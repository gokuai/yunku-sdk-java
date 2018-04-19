package com.gokuai.yunku.demo.thirdParty;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.ThirdPartyManagerHelper;

/**
 * Created by qp on 2017/3/16.
 */
public class OrderUnsubscribe {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = ThirdPartyManagerHelper.getInstance().orderUnsubscribe();

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
