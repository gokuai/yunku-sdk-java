package com.yunkuent.sdk.upload;

/**
 * Created by Brandon on 14/12/16.
 */
public interface UploadCallBack {
    void onSuccess(long threadId, String fileHash);

    void onFail(long threadId, String errorMsg);

    void onProgress(long threadId, float percent);
}
