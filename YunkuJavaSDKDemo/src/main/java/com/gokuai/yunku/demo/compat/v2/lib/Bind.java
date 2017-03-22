package com.gokuai.yunku.demo.compat.v2.lib;

import com.gokuai.yunku.demo.compat.v2.helper.EntLibraryManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/2.
 *
 * 获取库授权
 */
public class Bind {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntLibraryManagerHelper.getInstance().bind(1262679,"YunkuJavaSDKDemo",null);

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
