package com.gokuai.yunku.demo.sso;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.SSOManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 获取角色
 */
public class GetSsoGkkey {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";
        String account = "gk001";

        String returnString = SSOManagerHelper.getInstance().getSsoGkkey(account);
        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
