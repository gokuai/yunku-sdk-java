package com.gokuai.yunku.demo.compat.v2.file;

import com.gokuai.yunku.demo.compat.v2.helper.EntFileManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.yunkuent.sdk.DebugConfig;
import com.yunkuent.sdk.compat.v2.EntFileManager;

/**
 * Created by qp on 2017/3/2.
 *
 * 获取文件(夹)信息
 */
public class GetFileInfo {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntFileManagerHelper.getInstance().getFileInfo("aa.jpg", EntFileManager.NetType.DEFAULT);

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}

