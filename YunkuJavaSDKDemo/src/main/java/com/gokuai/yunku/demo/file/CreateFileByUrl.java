package com.gokuai.yunku.demo.file;

import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/2.
 *
 * 通过链接上传文件
 */
public class CreateFileByUrl {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="D://LogPath";//默认在D盘根目录

        String returnString = EntFileManagerHelper.getInstance().createFileByUrl("qq.jpg", 0,
                "Brandon", true, "http://reso2.yiihuu.com/976162-z.jpg");
        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
