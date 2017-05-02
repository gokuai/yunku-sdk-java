package com.gokuai.yunku.demo.compat.v2.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.compat.v2.helper.EntFileManagerHelper;
import com.yunkuent.sdk.UploadRunnable;
import com.yunkuent.sdk.upload.UploadCallBack;

/**
 * Created by qp on 2017/3/2.
 *
 * 文件分块上传
 */
public class UploadByBlock {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        UploadRunnable u = EntFileManagerHelper.getInstance().uploadByBlock("test.jpg", "Brandon", 0, DebugConfig.TEST_FILE_PATH, true, new UploadCallBack() {

            @Override
            public void onSuccess(long threadId, String fileHash) {
                System.out.println("success:" + threadId);

            }

            public void onFail(long threadId, String errorMsg) {
                System.out.println("fail:" + threadId + " errorMsg:" + errorMsg);

            }

            public void onProgress(long threadId, float percent) {
                System.out.println("onProgress:" + threadId + " onProgress:" + percent * 100);

            }
        });
    }
}
