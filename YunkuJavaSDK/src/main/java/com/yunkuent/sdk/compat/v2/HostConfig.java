package com.yunkuent.sdk.compat.v2;

import com.gokuai.base.NetConnection;
import org.apache.http.util.TextUtils;

import java.net.Proxy;

/**
 * Created by qp on 2017/4/7.
 */
public class HostConfig {

    //    protected static  String OAUTH_HOST_V2 = "http://zka.goukuai.cn";
    protected static String OAUTH_HOST_V2 = "http://a.goukuai.cn";
    //    protected static  String API_ENT_HOST_V2 = "http://zka-lib.goukuai.cn";
    //    protected static  String API_ENT_HOST_V2 = "http://test4a-lib.goukuai.cn";
    protected static String API_ENT_HOST_V2 = "http://a-lib.goukuai.cn";

    public static void changeConfig(String oauthHost, String apiHost) {
        if (!TextUtils.isEmpty(oauthHost)) {
            OAUTH_HOST_V2 = oauthHost;
        }
        if (!TextUtils.isEmpty(apiHost)) {
            API_ENT_HOST_V2 = apiHost;
        }
    }

    public static void setProxy(Proxy proxy) {
        if (!(proxy == null)) {
            NetConnection.proxy = proxy;
        }
    }
}
