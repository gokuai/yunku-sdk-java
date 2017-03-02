package com.gokuai.yunku.demo.library;

import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntLibraryManagerHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/2.
 *
 * 创建云库
 * 1T="1099511627776" 1G＝“1073741824”；
 */
public class Create {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="D://LogPath";//默认在D盘根目录

        String returnString = EntLibraryManagerHelper.getInstance().create("qqq", "1073741824", "destroy", "test lib", "");

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
