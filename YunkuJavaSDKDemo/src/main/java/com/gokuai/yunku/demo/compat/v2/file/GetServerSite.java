package com.gokuai.yunku.demo.compat.v2.file;

import com.gokuai.yunku.demo.compat.v2.helper.EntFileManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/21.
 */
public class GetServerSite {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntFileManagerHelper.getInstance().getServerSite("YunkuJavaSDK");

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
