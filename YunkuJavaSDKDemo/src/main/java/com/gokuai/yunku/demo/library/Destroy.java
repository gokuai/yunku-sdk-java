package com.gokuai.yunku.demo.library;

import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntLibraryManagerHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/2.
 *
 * 删除库
 */
public class Destroy {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="D://LogPath";//默认在D盘根目录

        String returnString = EntLibraryManagerHelper.getInstance().destroy("b2013df96cbc23b4b0dd72f075e5cbf7");

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
