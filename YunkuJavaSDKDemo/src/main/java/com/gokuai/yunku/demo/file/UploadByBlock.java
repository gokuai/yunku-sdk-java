package com.gokuai.yunku.demo.file;

import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.DebugConfig;
import com.yunkuent.sdk.upload.UploadCallBack;

/**
 * Created by qp on 2017/3/2.
 *
 * 文件分块上传
 */
public class UploadByBlock {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="D://LogPath";//默认在D盘根目录

        EntFileManagerHelper.getInstance().uploadByBlock("test", "Brandon", 0, "/Users/Brandon/Desktop/android-ndk-r13b-darwin-x86_64.zip", true, new UploadCallBack() {

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
