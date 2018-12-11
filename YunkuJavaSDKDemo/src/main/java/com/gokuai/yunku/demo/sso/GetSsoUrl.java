package com.gokuai.yunku.demo.sso;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.helper.SSOManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 获取角色
 */
public class GetSsoUrl {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
        String account = "gk001";

        String result = SSOManagerHelper.getInstance().getSsoUrl(account, null);

        System.out.println(result);
    }
}
