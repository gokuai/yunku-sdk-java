package com.gokuai.yunku.demo.ent;

import com.gokuai.yunku.demo.Config;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/16.
 */
public class AccessTokenWithThirdPartyOutId {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="D://LogPath";//默认在D盘根目录

        String returnString = EntManagerHelper.getInstance().accessTokenWithThirdPartyOutId(Config.OUT_ID);
        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
