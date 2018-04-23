package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.data.FileInfo;
import com.yunkuent.sdk.data.YunkuException;

/**
 * Created by qp on 2017/3/2.
 *
 * 文件分块上传
 */
public class UploadByBlock {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        try {
            //overwrite=false, 如果有重名 file.fullpath 的文件会自动追加(2), (3)...
            FileInfo file = EntFileManagerHelper.getInstance().uploadByBlock("RiverOtters-Yellowstone_GettyImages_RM-564829679_1080_HD_ZH-CN.mp4", "", 0, "/Pictures/RiverOtters-Yellowstone_GettyImages_RM-564829679_1080_HD_ZH-CN.mp4", true);
            System.out.println(file.fullpath + " upload success");
        } catch (YunkuException e) {
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
    }
}
