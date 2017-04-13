package com.gokuai.yunku.demo;

import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;
import com.yunkuent.sdk.DebugConfig;
import com.yunkuent.sdk.HostConfig;

/**
 * Created by qp on 2017/4/13.
 */
public class SetProxy {
    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        HostConfig.setProxy("qq",80);

        String returnString = EntManagerHelper.getInstance().getMembers(0, 99);

        DeserializeHelper.getInstance().deserializeReturn(returnString);

    }

}
