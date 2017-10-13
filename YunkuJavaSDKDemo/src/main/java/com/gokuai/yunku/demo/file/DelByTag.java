package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;

public class DelByTag {
    public static void main(String[] args) {
        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntFileManagerHelper.getInstance().delByTag("tag","qp");

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
