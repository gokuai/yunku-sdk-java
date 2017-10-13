package com.yunkuent.sdk;

import com.gokuai.base.NetConnection;
import com.gokuai.base.utils.Util;

import java.net.Proxy;

/**
 * Created by qp on 2017/4/7.
 *
 * use {@link ConfigHelper.class} instead
 *
 */

public class HostConfig {

    //     protected static  String OAUTH_HOST = "http://yk3-api.goukuai.cn";
    protected static String OAUTH_HOST = "http://yk3-api.gokuai.com";
    //    protected static  String API_ENT_HOST = "http://yk3-api-ent.goukuai.cn";
    protected static String API_ENT_HOST = "http://yk3-api-ent.gokuai.com";

    @Deprecated
    public static void changeConfig(String oauthHost, String apiHost) {
        if (!Util.isEmpty(oauthHost)) {
            OAUTH_HOST = oauthHost;
        }
        if (!Util.isEmpty(apiHost)) {
            API_ENT_HOST = apiHost;
        }
    }

    @Deprecated
    public static void setProxy(Proxy proxy) {
        if (proxy != null) {
            NetConnection.setProxy(proxy);
        }
    }
}
