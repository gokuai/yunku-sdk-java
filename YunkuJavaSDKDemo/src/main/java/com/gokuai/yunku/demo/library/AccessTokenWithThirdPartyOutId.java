package com.gokuai.yunku.demo.library;

import com.gokuai.yunku.demo.Config;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntLibraryManagerHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/16.
 *
 * 使用合作方 OutID 进行认证
 */
public class AccessTokenWithThirdPartyOutId {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntLibraryManagerHelper.getInstance().accessTokenWithThirdPartyOutId(Config.OUT_ID);
        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
