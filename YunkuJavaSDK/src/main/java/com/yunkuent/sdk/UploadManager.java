package com.yunkuent.sdk;

import com.gokuai.base.*;
import com.gokuai.base.utils.URLEncoder;
import com.gokuai.base.utils.Util;
import com.yunkuent.sdk.data.FileInfo;
import com.yunkuent.sdk.data.YunkuException;
import com.yunkuent.sdk.upload.IEntFileManager;
import com.yunkuent.sdk.upload.UploadCallback;
import com.yunkuent.sdk.utils.YKUtils;
import okhttp3.*;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.zip.CRC32;

public class UploadManager {
    private static final String LOG_TAG = "UploadManager ";

    private static final String URL_UPLOAD_INIT = "/upload_init";
    private static final String URL_UPLOAD_REQ = "/upload_req";
    private static final String URL_UPLOAD_PART = "/upload_part";
    private static final String URL_UPLOAD_ABORT = "/upload_abort";
    private static final String URL_UPLOAD_FINISH = "/upload_finish";
    private static final String URL_UPLOAD_ALL = "/upload_all";
    private static int RETRY = 0;

    private final int mBlockSize;// 上传分块大小
    private String mSession = "";// 上传session

    private HttpEngine mEngine;

    private String mLocalFile;
    private String mFullpath;
    private String mOpName;
    private int mOpId;
    private boolean mOverwrite;

    private OkHttpClient mUploadHttpClient;

    private UploadCallback mCallback;
    private InputStream mStream;
    private FileInfo mFileinfo = new FileInfo();

    private boolean mIsStop;

    public static void setRetry(int retry) {
        RETRY = retry;
    }

    public UploadManager(int blockSize, HttpEngine engine) {
        this.mBlockSize = blockSize;
        this.mEngine = engine;
    }

    public void setOperator(int opId) {
        this.mOpId = opId;
    }

    public void setOperator(String opName) {
        this.mOpName = opName;
    }

    public FileInfo upload(String localFile, String fullpath, boolean overwrite) throws YunkuException {
        this.mLocalFile = localFile;
        this.mFullpath = fullpath;
        this.mOverwrite = overwrite;
        return this.doUpload();
    }

    public FileInfo upload(InputStream stream, String fullpath, boolean overwrite) throws YunkuException {
        this.mStream = stream;
        this.mFullpath = fullpath;
        this.mOverwrite = overwrite;
        return this.doUpload();
    }

    public void uploadAsync(String localFile, String fullpath, boolean overwrite, UploadCallback callback) {
        this.mLocalFile = localFile;
        this.mFullpath = fullpath;
        this.mOverwrite = overwrite;
        this.mCallback = callback;
        this.doUploadAsync();
    }

    public void uploadAsync(InputStream stream, String fullpath, boolean overwrite, UploadCallback callback) {
        this.mStream = stream;
        this.mFullpath = fullpath;
        this.mOverwrite = overwrite;
        this.mCallback = callback;
        this.doUploadAsync();
    }

    private FileInfo doUpload() throws YunkuException {
        try {
            this.startUpload();
            return this.mFileinfo;
        } catch (IOException e) {
            LogPrint.error(LOG_TAG, e.getMessage());
            throw new YunkuException(e);
        } finally {
            if (this.mStream != null) {
                try {
                    this.mStream.close();
                } catch (IOException e) {
                    //忽略错误
                }
            }
        }
    }

    private void doUploadAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startUpload();

                    if (mCallback != null) {
                        mCallback.onProgress(mFullpath, 1);
                        mCallback.onSuccess(mFullpath, mFileinfo);
                    }
                } catch (YunkuException e) {
                    LogPrint.error(LOG_TAG, e.getMessage());
                    if (mCallback != null) {
                        mCallback.onFail(mFullpath, e);
                    }
                } catch (IOException e) {
                    LogPrint.error(LOG_TAG, e.getMessage());
                    if (mCallback != null) {
                        mCallback.onFail(mFullpath, new YunkuException(e));
                    }
                } finally {
                    if (mStream != null) {
                        try {
                            mStream.close();
                        } catch (IOException e) {
                            //忽略错误
                        }
                    }
                }
            }
        }).start();
    }

    private void startUpload() throws YunkuException, IOException {
        ReturnResult result;
        long totalTime = System.currentTimeMillis();  // 总上传开始时间
        long createFileTime = System.currentTimeMillis();
        long hashTime= System.currentTimeMillis();
        long uploadTime = System.currentTimeMillis();
        long uploadServerTime = System.currentTimeMillis();

        hashTime = System.currentTimeMillis();
        if (mStream != null) {
            mStream = Util.cloneInputStream(mStream);
            FileInfo fileInfo = YKUtils.getFileSha1(mStream, false);
            this.mFileinfo.fileHash = fileInfo.fileHash;
            this.mFileinfo.fileSize = fileInfo.fileSize;
        } else if (!Util.isEmpty(mLocalFile)) {
            File file = new File(mLocalFile);
            if (!file.exists()) {
                throw new YunkuException(mLocalFile + " not found");
            }
            if (!file.canRead()) {
                throw new YunkuException(mLocalFile + " can not read");
            }

            this.mFileinfo.fileHash = Util.getFileSha1(mLocalFile);
            this.mFileinfo.fileSize = file.length();
            mStream = new FileInputStream(mLocalFile);
        }

        hashTime = System.currentTimeMillis() - hashTime;
        if (mStream == null) {
            throw new YunkuException("fail to open file stream");
        }

        uploadTime = System.currentTimeMillis();
        for (int trys = 0; trys < 3; trys++) {

            createFileTime = System.currentTimeMillis();
            result = ((IEntFileManager)this.mEngine).createFile(this.mFullpath, this.mFileinfo.fileHash, this.mFileinfo.fileSize, mOverwrite, mOpName, mOpId);
            boolean shouldUpload = this.decodeAddFileResult(result);
            if (!shouldUpload) {
                return;
            }

            if (Util.isEmpty(this.mFileinfo.uploadServer)) {
                throw new YunkuException("fail to get upload server", result);
            }

            createFileTime = System.currentTimeMillis() -createFileTime;
            LogPrint.info(LOG_TAG, "The server is " + this.mFileinfo.uploadServer);

            if (this.mBlockSize == 0) {
                this.uploadAll();
                return;
            }

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

            mStream.skip(offset);

            uploadTime = System.currentTimeMillis();
            while (offset < this.mFileinfo.fileSize - 1 && !this.mIsStop) {

                if (mCallback != null) {
                    mCallback.onProgress(this.mFullpath, (float) offset / (float) this.mFileinfo.fileSize);
                }

                buflen = offset + this.mBlockSize > mFileinfo.fileSize ? (int) (mFileinfo.fileSize - offset) : this.mBlockSize;
                byte[] buffer = new byte[buflen];

                long chunkStart = System.currentTimeMillis();

                mStream.read(buffer);
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
                    mStream.skip(offset);
                } else {
                    throw new YunkuException("fail to call upload_part", result);
                }
            }
            if (this.mIsStop) {
                throw new YunkuException("upload stopped");
            }
            if (!uploadPartErr) {
                break;
            }
        }

        uploadTime = System.currentTimeMillis() - uploadTime;

        // 上传完成阶段
        uploadServerTime = System.currentTimeMillis();

        this.uploadFinish();

        uploadServerTime = System.currentTimeMillis() - uploadServerTime;

        totalTime = System.currentTimeMillis() - totalTime;

        LogPrint.info(LOG_TAG, "[UploadLog] fullPath: " + mFullpath + " fileSize: " + this.mFileinfo.fileSize + " hashTime: " + hashTime + "ms," + " creatFileTime: " + createFileTime + " ms," + " uploadTime: " + uploadTime + "ms," + " uploadServerTime: " + uploadServerTime + "ms," + " totalTime: " + totalTime + "ms");

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

    private ReturnResult uploadAll() throws YunkuException {
        String url = this.mFileinfo.uploadServer + URL_UPLOAD_ALL + "?org_client_id=" + this.mEngine.getClientId();

        Headers.Builder headerBuilder = new Headers.Builder();
        headerBuilder.set("x-gk-upload-pathhash", this.mFileinfo.hash);
        headerBuilder.set("x-gk-upload-filename", URLEncoder.encodeUTF8(this.mFileinfo.filename));
        headerBuilder.set("x-gk-upload-filehash", this.mFileinfo.fileHash);
        headerBuilder.set("x-gk-upload-filesize", String.valueOf(this.mFileinfo.fileSize));

        byte[] uploadData;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = this.mStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            uploadData = buffer.toByteArray();
        } catch (IOException e) {
            throw new YunkuException("fail to load upload data", new ReturnResult(e));
        }

        Request.Builder requestBuilder = new Request.Builder();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), uploadData);
        Request request = requestBuilder
                .url(url)
                .put(requestBody)
                .headers(headerBuilder.build())
                .build();

        int retry = RETRY;
        while (true) {
            try {

                Response resp = NetConnection.getOkHttpClient().newCall(request).execute();
                return new ReturnResult(resp.code(), resp.body().string());

            } catch (IOException e) {

                if (retry-- > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    throw new YunkuException("fail to call upload_all", new ReturnResult(e));
                }

            }
        }
    }

    /**
     * 初始化上传
     *
     * @throws Exception
     */
    private ReturnResult uploadInit() throws YunkuException {
        String url = this.mFileinfo.uploadServer + URL_UPLOAD_INIT + "?org_client_id=" + this.mEngine.getClientId();
        HashMap<String, String> headParams = new HashMap<String, String>();
        headParams.put("x-gk-upload-pathhash", this.mFileinfo.hash);
        headParams.put("x-gk-upload-filename", URLEncoder.encodeUTF8(this.mFileinfo.filename));
        headParams.put("x-gk-upload-filehash", this.mFileinfo.fileHash);
        headParams.put("x-gk-upload-filesize", String.valueOf(this.mFileinfo.fileSize));
        ReturnResult result = this.mEngine.new RequestHelper().setHeaders(headParams).setUrl(url).setMethod(RequestMethod.POST).disableSign().executeSync();

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
        HashMap<String, String> headParams = new HashMap<String, String>();
        headParams.put("x-gk-upload-session", mSession);

        long checkSize = 0;
        ReturnResult result = this.mEngine.new RequestHelper().setHeaders(headParams).setUrl(url).setMethod(RequestMethod.GET).disableSign().executeSync();
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

        int retry = RETRY;
        while (true) {
            try {

                Response resp = NetConnection.getOkHttpClient().newCall(request).execute();
                return new ReturnResult(resp.code(), resp.body().string());

            } catch (IOException e) {

                if (retry-- > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    throw new YunkuException("fail to call upload_part", new ReturnResult(e));
                }

            }
        }
    }

    /**
     * 上传完成
     *
     * @return
     */
    private ReturnResult uploadFinish() throws YunkuException {
        String url = this.mFileinfo.uploadServer + URL_UPLOAD_FINISH;
        HashMap<String, String> headParams = new HashMap<String, String>();
        headParams.put("x-gk-upload-session", mSession);
        ReturnResult result = null;

        int retry = 10;
        while (retry-- > 0) {
            result = this.mEngine.new RequestHelper().setHeaders(headParams).setUrl(url).setMethod(RequestMethod.POST).disableSign().executeSync();
            if (result.getCode() == HttpURLConnection.HTTP_ACCEPTED) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                break;
            }
        }

        if (result.isOK()) {
            return result;
        } else {
            throw new YunkuException("fail to call upload_finish" , result);
        }
    }

    /**
     * 上传取消
     */
    private void uploadAbort() {
        if (Util.isEmpty(this.mFileinfo.uploadServer) || Util.isEmpty(mSession)) {
            return;
        }
        String url = this.mFileinfo.uploadServer + URL_UPLOAD_ABORT;
        HashMap<String, String> headParams = new HashMap<String, String>();
        headParams.put("x-gk-upload-session", mSession);
        this.mEngine.new RequestHelper().setHeaders(headParams).setUrl(url).setMethod(RequestMethod.POST).disableSign().executeSync();
    }

    /**
     * 终止异步上传
     */
    public void stop() {
        mIsStop = true;
    }
}
