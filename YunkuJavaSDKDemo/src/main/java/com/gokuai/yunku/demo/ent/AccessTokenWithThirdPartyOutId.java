package com.gokuai.yunku.demo.ent;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.Config;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;

/**
 * Created by qp on 2017/3/16.
 *
 * 使用合作方 OutID 进行认证
 */
public class AccessTokenWithThirdPartyOutId {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntManagerHelper.getInstance().accessTokenWithThirdPartyOutId(Config.OUT_ID);
        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
