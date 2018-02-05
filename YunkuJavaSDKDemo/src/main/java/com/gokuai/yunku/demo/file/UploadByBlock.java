package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.ConfigHelper;
import com.yunkuent.sdk.UploadRunnable;
import com.yunkuent.sdk.upload.UploadCallBack;

/**
 * Created by qp on 2017/3/2.
 *
 * 文件分块上传
 */
public class UploadByBlock {

    public static void main(String[] args) {

        //-------- 如果想改编上传基础配置，可以进行以几种配置------
        new ConfigHelper()
                .uploadOpname("[Default Name]")
                .uploadRootPath("default/custom/upload/path")
                .uploadTags("document").config();

        //---------------------------------------------------


        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        UploadRunnable u = EntFileManagerHelper.getInstance().uploadByBlock("sear.txt", "Brandon", 0, "/Users/Brandon/Desktop/search.txt", true, 10485760, new UploadCallBack() {

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
