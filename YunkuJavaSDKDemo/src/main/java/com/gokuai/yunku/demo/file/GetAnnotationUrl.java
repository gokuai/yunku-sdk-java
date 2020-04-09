package com.gokuai.yunku.demo.file;

import com.gokuai.base.ReturnResult;
import com.yunkuent.sdk.EntFileManager;

public class GetAnnotationUrl {

    public static void main(String[] args) {

        String clientId = "";
        String secret = "";

        //文件上传时使用的fullpath参数
        String fullpath = "";
        //外部认证系统用户ID
        String outId = "";

        EntFileManager manager = new EntFileManager(clientId, secret);
        ReturnResult result = manager.getAnnotationUrlByFullpath(fullpath, outId);
        System.out.println(result.getBody());
    }
}
