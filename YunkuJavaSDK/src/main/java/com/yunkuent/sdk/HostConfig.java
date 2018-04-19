package com.yunkuent.sdk;

import com.gokuai.base.NetConnection;
import com.gokuai.base.utils.Util;

import java.net.Proxy;

/**
 * Created by qp on 2017/4/7.
 * <p>
 * use ConfigHelper.class instead
 */

public class HostConfig {

    protected static String OAUTH_HOST = "http://yk3-api.gokuai.com";
    protected static String API_ENT_HOST = "http://yk3-api-ent.gokuai.com";

    @Deprecated
    public static void changeConfig(String oauthHost, String apiEntHost) {
        if (!Util.isEmpty(oauthHost)) {
            OAUTH_HOST = oauthHost;
        }
        if (!Util.isEmpty(apiEntHost)) {
            API_ENT_HOST = apiEntHost;
        }
    }

    @Deprecated
    public static void setProxy(Proxy proxy) {
        if (proxy != null) {
            NetConnection.setProxy(proxy);
        }
    }
}
