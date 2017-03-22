package com.gokuai.yunku.demo.compat.v2.file;

import com.gokuai.yunku.demo.compat.v2.helper.EntFileManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/2.
 *
 * 通过链接上传文件
 */
public class CreateFileByUrl {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntFileManagerHelper.getInstance().createFileByUrl("qq.jpg", 0,
                "Brandon", true, "http://reso2.yiihuu.com/976162-z.jpg");
        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
