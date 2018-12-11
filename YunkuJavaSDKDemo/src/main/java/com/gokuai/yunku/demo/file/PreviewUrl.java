package com.gokuai.yunku.demo.file;

import com.gokuai.base.ReturnResult;
import com.yunkuent.sdk.ConfigHelper;
import com.yunkuent.sdk.EntFileManager;

public class PreviewUrl {

    public static void main(String[] args) {

//        new ConfigHelper()
//                .apiHost("http://yk3.gokuai.com/m-open")
//                .config();

        String clientId = "";
        String secret = "";

        //文件上传时使用的fullpath参数
        String fullpath = "";
        //是否显示水印
        boolean showWatermark = true;
        //水印上显示的用户名
        String WatermarkMemberName = "";

        EntFileManager manager = new EntFileManager(clientId, secret);
        ReturnResult result = manager.previewUrl(fullpath, showWatermark, WatermarkMemberName, "tom");

        if (result.isOK()) {
            //成功的结果
            System.out.println("success");
            System.out.println(result.getBody());

            //TODO 需要使用json解析body内容, 里面包含预览地址

        } else {
            if (result.getException() != null) {
                //出现网络或IO错误
                result.getException().printStackTrace();
            } else {
                //参数或其他错误
                System.out.println("http response code: " + result.getCode() + ", body: " + result.getBody());
            }
        }
    }
}
