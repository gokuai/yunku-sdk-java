package com.gokuai.yunku.demo.compat.v2;

import com.gokuai.yunku.demo.compat.v2.helper.EntManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.yunkuent.sdk.DebugConfig;
import com.yunkuent.sdk.compat.v2.HostConfig;

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
