package com.yunkuent.sdk.upload;

import com.yunkuent.sdk.data.FileInfo;
import com.yunkuent.sdk.data.YunkuException;

/**
 * Created by Brandon on 14/12/16.
 */
public interface UploadCallback {
    void onSuccess(String fullpath, FileInfo result);

    void onFail(String fullpath, YunkuException e);

    void onProgress(String fullpath, float percent);
}
