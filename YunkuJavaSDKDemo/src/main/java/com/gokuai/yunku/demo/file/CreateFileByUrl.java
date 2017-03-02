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

        String returnString = EntFileManagerHelper.getInstance().createFileByUrl("ppp", 0,
                "Brandon", true, "http://img02.tooopen.com/images/20150614/tooopen_sy_130377131846.jpg");
        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
