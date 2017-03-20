package com.gokuai.yunku.demo.thirdParty;

import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.ThirdPartyManagerHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/16.
 */
public class GetSsoUrl {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="D://LogPath";//默认在D盘根目录

        String returnString = ThirdPartyManagerHelper.getInstance().getSsoUrl("");

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
