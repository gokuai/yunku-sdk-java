package com.gokuai.yunku.demo.compat.v2.lib;

import com.gokuai.yunku.demo.compat.v2.helper.EntLibraryManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/2.
 *
 * 创建库
 * 1T="1099511627776" 1G＝“1073741824”；
 */
public class Create {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntLibraryManagerHelper.getInstance().create("compatV3", "1073741824", "destroy", "test lib", "");

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
