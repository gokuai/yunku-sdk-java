package com.yunkuent.sdk.compat.v2;

import com.gokuai.base.NetConnection;
import com.gokuai.base.utils.Util;

import java.net.Proxy;

/**
 * Created by qp on 2017/4/7.
 * <p>
 * use ConfigHelper.class instead
 */
public class HostConfig {

    protected static String API_HOST_V2 = "http://a.goukuai.cn";
    protected static String API_ENT_HOST_V2 = "http://a-lib.goukuai.cn";

    @Deprecated
    public static void changeConfig(String apiHost, String apiEntHost) {
        if (!Util.isEmpty(apiHost)) {
            API_HOST_V2 = apiHost;
        }
        if (!Util.isEmpty(apiEntHost)) {
            API_ENT_HOST_V2 = apiEntHost;
        }
    }

    @Deprecated
    public static void setProxy(Proxy proxy) {
        if (!(proxy == null)) {
            NetConnection.setProxy(proxy);
        }
    }
}
