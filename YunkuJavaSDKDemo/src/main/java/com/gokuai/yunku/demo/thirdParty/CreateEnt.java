package com.gokuai.yunku.demo.thirdParty;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.ThirdPartyManagerHelper;

/**
 * Created by qp on 2017/3/16.
 */
public class CreateEnt {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = ThirdPartyManagerHelper.getInstance().createEnt("yunku3","yunku3","","","");
        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }

}
