package com.gokuai.yunku.demo.compat.v2.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.Config;
import com.gokuai.yunku.demo.compat.v2.helper.EntFileManagerHelper;
import com.yunkuent.sdk.compat.v2.ConfigHelper;
import com.yunkuent.sdk.data.FileInfo;
import com.yunkuent.sdk.data.YunkuException;
import com.yunkuent.sdk.upload.UploadCallback;

/**
 * Created by qp on 2017/3/2.
 * <p>
 * 文件分块上传
 */
public class UploadByBlockAsync {

    public static void main(String[] args) {

        //-------- 如果想改编上传基础配置，可以进行以几种配置------
        new ConfigHelper()
                .uploadOpName("[Default Name]")
                .uploadRootPath("default/custom/upload/path")
                .config();

        //---------------------------------------------------

        DebugConfig.DEBUG = true;

        EntFileManagerHelper.getInstance().uploadByBlockAsync("testBlockSize.jpg", "", 0, Config.TEST_FILE_PATH, true, 10485760, new UploadCallback() {

            @Override
            public void onSuccess(String fullpath, FileInfo file) {
                System.out.println(fullpath + " upload success");
            }

            @Override
            public void onFail(String fullpath, YunkuException e) {
                System.out.println("upload fail");
                e.printStackTrace();

                ReturnResult result = e.getReturnResult();
                if (result != null) {
                    if (result.getException() != null) {
                        //出现网络或IO错误
                        result.getException().printStackTrace();
                    } else {
                        //如果API接口返回异常, 获取最后一次API请求的结果
                        System.out.println("http response code: " + result.getCode() + ", body: " + result.getBody());
                    }
                }
            }

            @Override
            public void onProgress(String fullpath, float percent) {
                System.out.println(fullpath + ": "  + percent * 100 + "%");
            }
        });
    }
}
