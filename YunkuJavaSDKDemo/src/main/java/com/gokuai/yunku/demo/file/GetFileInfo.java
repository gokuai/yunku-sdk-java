package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.EntFileManager;

/**
 * Created by qp on 2017/3/2.
 *
 * 获取文件(夹)信息
 */
public class GetFileInfo {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntFileManagerHelper.getInstance().getFileInfo("default/custom/upload/path/search3.txt", EntFileManager.NetType.DEFAULT, true);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}

