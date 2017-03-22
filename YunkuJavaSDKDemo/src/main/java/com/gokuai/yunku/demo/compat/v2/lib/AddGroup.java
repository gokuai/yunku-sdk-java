package com.gokuai.yunku.demo.compat.v2.lib;

import com.gokuai.yunku.demo.compat.v2.helper.EntLibraryManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/2.
 *
 * 库上添加分组
 */
public class AddGroup {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntLibraryManagerHelper.getInstance().addGroup(1262679,154837,13862);

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
