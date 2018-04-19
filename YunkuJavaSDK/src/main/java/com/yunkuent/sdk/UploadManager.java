package com.yunkuent.sdk;

import com.gokuai.base.*;
import com.gokuai.base.utils.URLEncoder;
import com.gokuai.base.utils.Util;
import com.yunkuent.sdk.data.FileInfo;
import com.yunkuent.sdk.data.YunkuException;
import com.yunkuent.sdk.upload.UploadCallback;
import com.yunkuent.sdk.utils.YKUtils;
import okhttp3.*;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.zip.CRC32;

public class UploadManager<T> extends HttpEngine implements Runnable {
    private static final String LOG_TAG = "UploadManager ";

    private static final String URL_UPLOAD_INIT = "/upload_init";
    private static final String URL_UPLOAD_REQ = "/upload_req";
    private static final String URL_UPLOAD_PART = "/upload_part";
    private static final String URL_UPLOAD_ABORT = "/upload_abort";
    private static final String URL_UPLOAD_FINISH = "/upload_finish";

    private final int mBlockSize;// 上传分块大小
    private String mSession = "";// 上传session
    private String mApiUrl = "";

    private String mLocalFullPath;
    private String mFullPath;
    private long mDateline;
    private String mOpName;
    private int mOpId;
    private boolean mOverwrite;

    private OkHttpClient mUploadHttpClient;

    private UploadCallback mCallback;
    private InputStream mInputStream;
    private FileInfo mFileinfo = new FileInfo();

    private String mTags;
    private T mManager;
    private boolean mIsStop;

    public UploadManager(String apiUrl, String localFullPath, String fullPath,
                         String opName, int opId, String orgClientId, String clientSecret, boolean overwrite, int blockSize, long dateline) {

        super(orgClientId, clientSecret);
        this.mApiUrl = apiUrl;
        this.mLocalFullPath = localFullPath;
        this.mFullPath = fullPath;
        this.mOpId = opId;
        this.mOpName = opName;
        this.mOverwrite = overwrite;
        this.mBlockSize = blockSize;
        this.mDateline = dateline;
    }


    protected UploadManager(String apiUrl, InputStream inputStream, String fullPath,
                            String opName, int opId, String orgClientId, String clientSecret, boolean overwrite, int blockSize, long dateline) {

        super(orgClientId, clientSecret);
        this.mApiUrl = apiUrl;
        this.mInputStream = inputStream;
        this.mFullPath = fullPath;
        this.mOpId = opId;
        this.mOpName = opName;
        this.mOverwrite = overwrite;
        this.mBlockSize = blockSize;
        this.mDateline = dateline;
    }

    public void setAsyncCallback(UploadCallback callback) {
        this.mCallback = callback;
    }

    public void setAutoTags(String tags, T manager) {
        this.mTags = tags;
        this.mManager = manager;
    }

    public FileInfo upload() throws YunkuException {
        try {
            this.startUpload();
            this.tagUploadFile();
            return this.mFileinfo;
        } catch (IOException e) {
            LogPrint.error(LOG_TAG, e.getMessage());
            throw new YunkuException(e);
        }
    }

    @Override
    public void run() {
        try {
            this.startUpload();
            this.tagUploadFile();

            if (mCallback != null) {
                mCallback.onProgress(this.mFullPath, 1);
                mCallback.onSuccess(this.mFullPath, this.mFileinfo);
            }
        } catch (YunkuException e) {
            LogPrint.error(LOG_TAG, e.getMessage());
            if (mCallback != null) {
                mCallback.onFail(this.mFullPath, e);
            }
        } catch (IOException e) {
            LogPrint.error(LOG_TAG, e.getMessage());
            if (mCallback != null) {
                mCallback.onFail(this.mFullPath, new YunkuException(e));
            }
        }
    }

    public void startUpload() throws YunkuException, IOException {
        ReturnResult result;

        if (mInputStream != null) {
            mInputStream = Util.cloneInputStream(mInputStream);
            FileInfo fileInfo = YKUtils.getFileSha1(mInputStream, false);
            this.mFileinfo.fileHash = fileInfo.fileHash;
            this.mFileinfo.fileSize = fileInfo.fileSize;
        } else if (!Util.isEmpty(mLocalFullPath)) {
            File file = new File(mLocalFullPath);
            if (!file.exists()) {
                throw new YunkuException(mLocalFullPath + " not found");
            }
            if (!file.canRead()) {
                throw new YunkuException(mLocalFullPath + " can not read");
            }

            this.mFileinfo.fileHash = Util.getFileSha1(mLocalFullPath);
            this.mFileinfo.fileSize = file.length();
            mInputStream = new FileInputStream(mLocalFullPath);
        }

        if (mInputStream == null) {
            throw new YunkuException("fail to open file stream");
        }

        for (int trys = 0; trys < 3; trys++) {

            result = this.addFile();
            boolean shouldUpload = this.decodeAddFileResult(result);
            if (!shouldUpload) {
                return;
            }

            if (Util.isEmpty(this.mFileinfo.uploadServer)) {
                throw new YunkuException("fail to get upload server", result);
            }

            LogPrint.info(LOG_TAG, "The server is " + this.mFileinfo.uploadServer);

            result = this.uploadInit();
            if (result == null) {
                continue;
            }
            if (result.getCode() == HttpURLConnection.HTTP_ACCEPTED) {
                return;
            }

            long checkSize = this.uploadReq();
            if (checkSize < 0) {
                continue;
            }
            if (checkSize == this.mFileinfo.fileSize) {
                break;
            }

            int buflen;
            long offset = checkSize;
            long range_end;
            long crc32;
            String range;
            boolean uploadPartErr = false;
            int code;
            CRC32 crc = new CRC32();

            mInputStream.skip(offset);

            while (offset < this.mFileinfo.fileSize - 1 && !mIsStop) {

                if (mCallback != null) {
                    mCallback.onProgress(this.mFullPath, (float) offset / (float) this.mFileinfo.fileSize);
                }

                buflen = offset + this.mBlockSize > mFileinfo.fileSize ? (int) (mFileinfo.fileSize - offset) : this.mBlockSize;
                byte[] buffer = new byte[buflen];
                mInputStream.read(buffer);
                crc.update(buffer);
                crc32 = crc.getValue();
                crc.reset();

                range_end = offset + buflen - 1;
                range = offset + "-" + range_end;

                result = uploadPart(range, buffer, crc32);
                System.gc();

                code = result.getCode();

                if (code == HttpURLConnection.HTTP_OK) {
                    // 200
                    offset += buflen;
                } else if (code == HttpURLConnection.HTTP_ACCEPTED) {
                    // 202-上传的文件已完成, 可以直接调finish接口
                    //uploadPartErr = true;
                    break;
                } else if (code >= HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    // >=500-服务器错误
                    uploadPartErr = true;
                    break;
                } else if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    // 401-session验证不通过
                    uploadPartErr = true;
                    break;
                } else if (code == HttpURLConnection.HTTP_CONFLICT) {
                    // 409-上传块序号错误, http内容中给出服务器期望的块序号
                    JSONObject json = new JSONObject(result.getBody());
                    offset = Long.parseLong(json.getString("expect"));
                    mInputStream.skip(offset);
                } else {
                    throw new YunkuException("fail to upload part", result);
                }
            }
            if (!uploadPartErr) {
                break;
            }
        }
        this.uploadFinish();
    }

    private ReturnResult addFile() {
        String url = mApiUrl;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", this.mClientId);
        params.put("fullpath", this.mFullPath);
        if (mOpId > 0) {
            params.put("op_id", mOpId + "");
        } else if (mOpName != null && !mOpName.isEmpty()) {
            params.put("op_name", mOpName);
        }
        params.put("overwrite", (mOverwrite ? 1 : 0) + "");
        params.put("dateline", mDateline + "");
        params.put("sign", generateSign(params));

        params.put("filesize", this.mFileinfo.fileSize + "");
        params.put("filehash", this.mFileinfo.fileHash);

        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    private boolean decodeAddFileResult(ReturnResult result) throws YunkuException {
        if (!result.isOK() || Util.isEmpty(result.getBody())) {
            throw new YunkuException("fail to get upload server", result);
        }
        try {
            JSONObject json = new JSONObject(result.getBody());
            this.mFileinfo.fullpath = json.getString("fullpath");
            this.mFileinfo.filename = Util.getNameFromPath(this.mFileinfo.fullpath);
            this.mFileinfo.hash = json.getString("hash");
            this.mFileinfo.uploadServer = json.optString("server");

            int state = json.optInt("state");
            return state == 0;
        } catch (Exception e) {
            throw new YunkuException("fail to decode create_file result", result);
        }
    }

    /**
     * 初始化上传
     *
     * @throws Exception
     */
    private ReturnResult uploadInit() throws YunkuException {
        String url = this.mFileinfo.uploadServer + URL_UPLOAD_INIT + "?org_client_id=" + this.mClientId;
        final HashMap<String, String> headParams = new HashMap<String, String>();
        headParams.put("x-gk-upload-pathhash", this.mFileinfo.hash);
        headParams.put("x-gk-upload-filename", URLEncoder.encodeUTF8(this.mFileinfo.filename));
        headParams.put("x-gk-upload-filehash", this.mFileinfo.fileHash);
        headParams.put("x-gk-upload-filesize", String.valueOf(this.mFileinfo.fileSize));
        ReturnResult result = new RequestHelper().setHeadParams(headParams).setUrl(url).setMethod(RequestMethod.POST).executeSync();

        if (result.isOK()) {
            JSONObject json = new JSONObject(result.getBody());
            this.mSession = json.optString("session");
            if (Util.isEmpty(this.mSession)) {
                throw new YunkuException("fail to get session in uploadInit", result);
            }
        } else if (result.getCode() >= HttpURLConnection.HTTP_INTERNAL_ERROR) {
            return null;
        } else if (result.getCode() != HttpURLConnection.HTTP_ACCEPTED) {
            throw new YunkuException("fail to call upload_init", result);
        }
        return result;
    }

    private long uploadReq() throws YunkuException {
        String url = this.mFileinfo.uploadServer + URL_UPLOAD_REQ;
        final HashMap<String, String> headParams = new HashMap<String, String>();
        headParams.put("x-gk-upload-session", mSession);

        long checkSize = 0;
        ReturnResult result = new RequestHelper().setHeadParams(headParams).setUrl(url).setMethod(RequestMethod.GET).executeSync();
        if (result.isOK()) {
            try {
                checkSize = Long.parseLong(result.getBody());
            } catch (Exception e) {
                checkSize = 0;
            }
        } else if (result.getCode() >= HttpURLConnection.HTTP_INTERNAL_ERROR) {
            checkSize = -1;
        } else {
            throw new YunkuException("fail to call upload_req", result);
        }
        return checkSize;
    }

    /**
     * 分块上传
     *
     * @param range
     * @param crc32
     * @return
     */
    private ReturnResult uploadPart(String range, byte[] content, long crc32) throws YunkuException {

        try {
            String url = this.mFileinfo.uploadServer + URL_UPLOAD_PART;

            Headers.Builder headerBuilder = new Headers.Builder();
            headerBuilder.add("Connection", "Keep-Alive");
            headerBuilder.add("x-gk-upload-session", mSession);
            headerBuilder.add("x-gk-upload-range", range);
            headerBuilder.add("x-gk-upload-crc", String.valueOf(crc32));

            Request.Builder requestBuilder = new Request.Builder();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), content);
            Request request = requestBuilder
                    .url(url)
                    .put(requestBody)
                    .headers(headerBuilder.build())
                    .build();

            Response resp = this.getUploadHttpClient().newCall(request).execute();
            return new ReturnResult(resp.code(), resp.body().string());
        } catch (Exception e) {
            throw new YunkuException("fail to call upload_part", new ReturnResult(e));
        }
    }

    private OkHttpClient getUploadHttpClient() {
        if (this.mUploadHttpClient == null) {
            this.mUploadHttpClient = NetConnection.getOkHttpClient();
        }
        return this.mUploadHttpClient;
    }

    /**
     * 上传完成
     *
     * @return
     */
    private ReturnResult uploadFinish() throws YunkuException {
        String url = this.mFileinfo.uploadServer + URL_UPLOAD_FINISH;
        final HashMap<String, String> headParams = new HashMap<String, String>();
        headParams.put("x-gk-upload-session", mSession);
        ReturnResult result = new RequestHelper().setHeadParams(headParams).setUrl(url).setMethod(RequestMethod.POST).executeSync();
        if (!result.isOK()) {
            throw new YunkuException("fail to call upload_finish" , result);
        }
        return result;
    }

    /**
     * 上传取消
     */
    private void uploadAbort() {
        if (Util.isEmpty(this.mFileinfo.uploadServer) || Util.isEmpty(mSession)) {
            return;
        }
        String url = this.mFileinfo.uploadServer + URL_UPLOAD_ABORT;
        final HashMap<String, String> headParams = new HashMap<String, String>();
        headParams.put("x-gk-upload-session", mSession);
        new RequestHelper().setHeadParams(headParams).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 为上传文件打标签
     * @throws YunkuException
     */
    private void tagUploadFile() throws YunkuException {
        if (Util.isEmpty(this.mTags) || this.mManager == null) {
            return;
        }
        ReturnResult result = null;
        if (mManager instanceof EntFileManager) {
            result = ((EntFileManager) mManager).addUploadTags(this.mFileinfo.fullpath);

        } else if (mManager instanceof com.yunkuent.sdk.compat.v2.EntFileManager) {
            result = ((com.yunkuent.sdk.compat.v2.EntFileManager) mManager).addUploadTags(this.mFileinfo.fullpath);
        }

        if (result == null || !result.isOK()) {
            throw new YunkuException("fail to call add_tag", result);
        }
    }

    public void stop() {
        mIsStop = true;
    }


}
