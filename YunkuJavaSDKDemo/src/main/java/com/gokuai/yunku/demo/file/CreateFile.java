package com.gokuai.yunku.demo.file;

import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/2.
 *
 * 上传文件 文件不得超过50MB
 */
public class CreateFile {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="D://LogPath";//默认在D盘根目录

        String returnString = EntFileManagerHelper.getInstance().createFile("WoWScrnShot_031415_175713.jpeg","Brandon","/Users/Brandon/Desktop/gugepinyinshurufa_427.apk");

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
