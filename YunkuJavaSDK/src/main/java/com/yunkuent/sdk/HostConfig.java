package com.yunkuent.sdk;

import org.apache.http.util.TextUtils;

/**
 * Created by qp on 2017/4/7.
 */
public class HostConfig {

    //     protected static  String OAUTH_HOST = "http://yk3-api.goukuai.cn";
    protected static String OAUTH_HOST = "http://yk3-api.gokuai.com";
    //    protected static  String API_ENT_HOST = "http://yk3-api-ent.goukuai.cn";
    protected static  String API_ENT_HOST = "http://yk3-api-ent.gokuai.com";

    public static void changeConfig(String oauthHost, String apiHost){
        if (!TextUtils.isEmpty(oauthHost)){
            OAUTH_HOST = oauthHost;
        }
        if (!TextUtils.isEmpty(apiHost)){
            API_ENT_HOST = apiHost;
        }
    }

    public static void setProxy(String hostName, int port) {

        NetConnection.SETPROXY = true;

        if (!TextUtils.isEmpty(hostName)) {
            NetConnection.HOST_NAME = hostName;
        }
        if (port > 0) {
            NetConnection.PORT = port;
        }
    }
}
