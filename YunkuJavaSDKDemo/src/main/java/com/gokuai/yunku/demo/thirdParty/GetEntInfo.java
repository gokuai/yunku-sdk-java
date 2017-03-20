package com.gokuai.yunku.demo.thirdParty;

import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.ThirdPartyManagerHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/16.
 */
public class GetEntInfo {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = ThirdPartyManagerHelper.getInstance().getEntInfo();

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
