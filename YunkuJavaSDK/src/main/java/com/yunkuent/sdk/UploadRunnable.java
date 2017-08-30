package com.yunkuent.sdk;

import com.gokuai.base.*;
import com.gokuai.base.utils.URLEncoder;
import com.gokuai.base.utils.Util;
import com.yunkuent.sdk.data.FileInfo;
import com.yunkuent.sdk.data.FileOperationData;
import com.yunkuent.sdk.upload.UploadCallBack;
import com.yunkuent.sdk.utils.YKUtils;
import okhttp3.*;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.zip.CRC32;

public class UploadRunnable extends HttpEngine implements Runnable {
    private static final String LOG_TAG = "UploadRunnable ";

    private static final String URL_UPLOAD_INIT = "/upload_init";
    private static final String URL_UPLOAD_PART = "/upload_part";
    private static final String URL_UPLOAD_ABORT = "/upload_abort";
    private static final String URL_UPLOAD_FINISH = "/upload_finish";

    private int mRangSize = 524288;// 上传分块大小-512K

    private String mServer = "";// 上传服务器地址
    private String mSession = "";// 上传session
    private String mApiUrl = "";

    private String mLocalFullPath;
    private String mFullPath;
    private String mOrgClientId;
    private long mDateline;
    private String mOpName;
    private int mOpId;
    private static long threadSeqNumber;
    private boolean overWrite;

    private OkHttpClient mUploadHttpClient;

    private UploadCallBack mCallBack;
    private long mRId;
    private InputStream mInputStream;

    public UploadRunnable(String apiUrl, String localFullPath, String fullPath,
                          String opName, int opId, String orgClientId, long dateline, UploadCallBack callBack, String clientSecret, boolean overWrite, int rangSize) {

        super(orgClientId, clientSecret);
        this.mApiUrl = apiUrl;
        this.mLocalFullPath = localFullPath;
        this.mFullPath = fullPath;
        this.mOrgClientId = orgClientId;
        this.mDateline = dateline;
        this.mCallBack = callBack;
        this.mClientSecret = clientSecret;
        this.mOpId = opId;
        this.mOpName = opName;
        this.overWrite = overWrite;
        this.mRId = nextThreadID();
        this.mRangSize = rangSize;
    }


    protected UploadRunnable(String apiUrl, InputStream inputStream, String fullPath,
                             String opName, int opId, String orgClientId, long dateline, UploadCallBack callBack, String clientSecret, boolean overWrite, int rangSize) {

        super(orgClientId, clientSecret);
        this.mApiUrl = apiUrl;
        this.mInputStream = inputStream;
        this.mFullPath = fullPath;
        this.mOrgClientId = orgClientId;
        this.mDateline = dateline;
        this.mCallBack = callBack;
        this.mClientSecret = clientSecret;
        this.mOpId = opId;
        this.mOpName = opName;
        this.overWrite = overWrite;
        this.mRId = nextThreadID();
        this.mRangSize = rangSize;
    }

    private static synchronized long nextThreadID() {
        return ++threadSeqNumber;
    }

    @Override
    public void run() {

        ReturnResult result;
        String fullpath = mFullPath;
        InputStream in = null;
        BufferedInputStream bis = null;
        try {

            String filehash = "";
            long filesize = 0;
            if (!Util.isEmpty(mLocalFullPath)) {
                File file = new File(mLocalFullPath);
                if (!file.exists()) {
                    LogPrint.error(LOG_TAG, "'" + mLocalFullPath + "'  file not exist!");
                    return;
                }

                filehash = Util.getFileSha1(mLocalFullPath);
                filesize = file.length();
            }

            if (mInputStream != null) {
                mInputStream = Util.cloneInputStream(mInputStream);
                FileInfo fileInfo = YKUtils.getFileSha1(mInputStream, false);
                filehash = fileInfo.fileHash;
                filesize = fileInfo.fileSize;
            }

            String filename = Util.getNameFromPath(fullpath).replace("/", "");

            ReturnResult returnResult = ReturnResult.create(addFile(filesize, filehash, fullpath));
            FileOperationData data = FileOperationData.create(returnResult.getResult(), returnResult.getStatusCode());

            if (data != null) {
                if (data.getCode() == HttpURLConnection.HTTP_OK) {
                    if (data.getState() != FileOperationData.STATE_NOUPLOAD) {
                        // 服务器上没有，上传文件
                        mServer = data.getServer();

                        if (Util.isEmpty(mServer)) {
                            throw new Exception(" The server is empty ");
                        } else {
                            LogPrint.info(LOG_TAG, "The server is " + mServer);
                        }

                        // upload_init
                        int errorCode = upload_init(data.getHash(), filename, fullpath, filehash, filesize);

                        if (errorCode == HttpURLConnection.HTTP_OK) {
                            // upload_part
                            if (mInputStream != null) {
                                in = mInputStream;
                            } else {
                                in = new FileInputStream(mLocalFullPath);
                            }

                            if (in == null) {
                                throw new Exception(" error file InputStream ");
                            }

                            bis = new BufferedInputStream(in);

                            int code = 0;
                            long range_index = 0;
                            long range_end = 0;
                            long datalength = -1;
                            long crc32 = 0;
                            String range = "";
                            byte[] buffer = new byte[mRangSize];
                            CRC32 crc = new CRC32();
                            bis.mark(mRangSize);
                            while ((datalength = bis.read(buffer)) != -1 && !isStop) {

                                range_end = mRangSize * (range_index + 1) - 1;
                                if (range_end >= filesize) {
                                    range_end = filesize - 1;
                                    int length_end = (int) (filesize - mRangSize * range_index);
                                    byte[] buffer_end = new byte[length_end];
                                    System.arraycopy(buffer, 0, buffer_end, 0, length_end);
                                    crc.update(buffer_end);
                                    crc32 = crc.getValue();
                                } else {
                                    crc.update(buffer);
                                    crc32 = crc.getValue();
                                }
                                crc.reset();
                                long currentLength = mRangSize * range_index;
                                range = currentLength + "-" + range_end;

                                mCallBack.onProgress(mRId, (float) currentLength / (float) filesize);
                                result = upload_part(range, buffer, (int) datalength, crc32);
                                code = result.getStatusCode();
                                if (code == HttpURLConnection.HTTP_OK) {
                                    // 200
                                    bis.mark(mRangSize);
                                    range_index++;
                                    System.gc();
                                } else if (code == HttpURLConnection.HTTP_ACCEPTED) {
                                    // 202-上传的文件已完成, 可以直接调finish接口
                                    break;
                                } else if (code >= HttpURLConnection.HTTP_INTERNAL_ERROR) {
                                    // >=500-服务器错误
                                    upload_server(filesize, filehash, fullpath);
                                    continue;
                                } else if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                                    // 401-session验证不通过
                                    upload_init(data.getHash(), filename, fullpath,
                                            filehash, filesize);
                                    continue;
                                } else if (code == HttpURLConnection.HTTP_CONFLICT) {
                                    // 409-上传块序号错误, http内容中给出服务器期望的块序号
                                    JSONObject json = new JSONObject(result.getResult());
                                    long part_range_start = Long.parseLong(json.optString("expect"));
                                    range_index = part_range_start / mRangSize;
                                    bis.reset();
                                    bis.skip(part_range_start);
                                    continue;
                                } else {

                                    throw new Exception();
                                }
                            }
                        }

                        // upload_check
                        do {
                            //大文件数据没有存储完毕，需要等一会再检查一遍
                            errorCode = upload_check();

                            if (errorCode == HttpURLConnection.HTTP_OK) {
                                break;
                            }

                            Thread.sleep(3000);

                        } while (errorCode == HttpURLConnection.HTTP_ACCEPTED);
                    }

                    // upload_sussec
                    if (mCallBack != null) {
                        mCallBack.onProgress(mRId, 1);
                        mCallBack.onSuccess(mRId, returnResult.getResult());
                    }
                } else {
                    // 上传失败
                    throw new Exception(LOG_TAG + data.getErrnoMsg().trim());
                }
            } else {
                // 上传失败
                throw new Exception();
            }

        } catch (Exception ex) {
            LogPrint.warn(LOG_TAG, ex.getMessage());

            if (!Util.isEmpty(mServer)) {
                upload_abort();
            }
            if (mCallBack != null) {
                mCallBack.onFail(mRId, ex.getMessage());
            }
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                LogPrint.warn(LOG_TAG, "runnable with io exception:msg" + e.getMessage());
            }

            System.gc();

        }


    }

    /**
     * @return
     * @throws Exception
     */

    private int upload_check() throws Exception {
        String returnString = upload_finish();
        ReturnResult result = ReturnResult.create(returnString);
        if (result.getStatusCode() == HttpURLConnection.HTTP_OK
                || result.getStatusCode() == HttpURLConnection.HTTP_ACCEPTED) {
            return result.getStatusCode();
        } else {
            throw new Exception();
        }

    }

    /**
     * 上传服务器
     *
     * @param filesize
     * @param filehash
     * @param fullPath
     */
    private void upload_server(long filesize, String filehash, String fullPath) {
        ReturnResult returnResult = ReturnResult.create(addFile(filesize, filehash, fullPath));
        FileOperationData data = FileOperationData.create(returnResult.getResult(), returnResult.getStatusCode());
        if (data != null) {
            mServer = data.getServer();
        }
    }

    /**
     * 初始化上传
     *
     * @param hash
     * @param filename
     * @param filehash
     * @param filesize
     * @throws Exception
     */
    private int upload_init(String hash, String filename, String fullpath,
                            String filehash, long filesize) throws Exception {
        String url = mServer + URL_UPLOAD_INIT + "?org_client_id=" + mOrgClientId;
        final HashMap<String, String> headParams = new HashMap<>();
        headParams.put("x-gk-upload-pathhash", hash);
        headParams.put("x-gk-upload-filename", URLEncoder.encodeUTF8(filename));
        headParams.put("x-gk-upload-filehash", filehash);
        headParams.put("x-gk-upload-filesize", String.valueOf(filesize));
        String returnString = new RequestHelper().setHeadParams(headParams).setUrl(url).setMethod(RequestMethod.POST).executeSync();
        ReturnResult result = ReturnResult.create(returnString);
        if (result.getStatusCode() == HttpURLConnection.HTTP_OK) {
            JSONObject json = new JSONObject(result.getResult());
            mSession = json.optString("session");
            return result.getStatusCode();
        } else if (result.getStatusCode() == HttpURLConnection.HTTP_ACCEPTED) {
            return result.getStatusCode();
        } else if (result.getStatusCode() >= HttpURLConnection.HTTP_INTERNAL_ERROR) {
            upload_server(filesize, filehash, fullpath);
            return upload_init(hash, filename, fullpath, filehash, filesize);
        } else {
            throw new Exception();
        }
    }

    /**
     * 分块上传
     *
     * @param range
     * @param crc32
     * @return
     */
    private ReturnResult upload_part(String range, byte[] content, int dataLength, long crc32) {
        ReturnResult returnResult = new ReturnResult();
        String url = mServer + URL_UPLOAD_PART;
        try {

            Headers.Builder headerBuilder = new Headers.Builder();
            headerBuilder.add("Connection", "Keep-Alive");
            headerBuilder.add("x-gk-upload-session", mSession);
            headerBuilder.add("x-gk-upload-range", range);
            headerBuilder.add("x-gk-upload-crc", String.valueOf(crc32));

            Request.Builder requestBuilder = new Request.Builder();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream")
                    , content, 0, dataLength);
            Request request = requestBuilder
                    .url(url)
                    .put(requestBody)
                    .headers(headerBuilder.build())
                    .build();

            Response response = getUploadHttpClient().newCall(request).execute();

            returnResult.setResult(response.body().string());
            returnResult.setStatusCode(response.code());
            response.body().close();
        } catch (Exception e) {
            LogPrint.warn(LOG_TAG, "upload exception:" + e.getMessage());
        }
        return returnResult;
    }

    private OkHttpClient getUploadHttpClient() {
        if (mUploadHttpClient == null) {
            mUploadHttpClient = NetConnection.getOkHttpClient();
        }
        return mUploadHttpClient;
    }


    /**
     * 上传完成
     *
     * @return
     */
    private String upload_finish() {
        String url = mServer + URL_UPLOAD_FINISH;
        final HashMap<String, String> headParams = new HashMap<>();
        headParams.put("x-gk-upload-session", mSession);
        return new RequestHelper().setHeadParams(headParams).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }


    /**
     * 上传取消
     */
    public void upload_abort() {
        String url = mServer + URL_UPLOAD_ABORT;
        final HashMap<String, String> headParams = new HashMap<>();
        headParams.put("x-gk-upload-session", mSession);
        new RequestHelper().setHeadParams(headParams).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    public String addFile(long filesize, String filehash, String fullpath) {
        String url = mApiUrl;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("dateline", mDateline + "");
        params.put("fullpath", fullpath + "");
        if (mOpId > 0) {
            params.put("op_id", mOpId + "");
        } else if (mOpName != null) {
            params.put("op_name", mOpName);
        }
        params.put("overwrite", (overWrite ? 1 : 0) + "");
        params.put("sign", generateSign(params));

        params.put("filesize", filesize + "");
        params.put("filehash", filehash + "");

        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    private boolean isStop;

    public void stop() {
        isStop = true;
    }


}
