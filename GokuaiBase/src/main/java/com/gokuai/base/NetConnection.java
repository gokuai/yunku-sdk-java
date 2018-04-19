package com.gokuai.base;

import com.gokuai.base.utils.Base64;
import com.gokuai.base.utils.Util;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * http请求
 */
public final class NetConnection {

    private static final String LOG_TAG = "NetConnection";
    private static final String USER_AGENT = "YunkuJavaSDK" + ";" + System.getProperties().getProperty("http.agent");
    private static final String ACCEPT_LANGUAGE = Locale.getDefault().toString().contains("zh") ? "zh-CN" : "en-US";

    private static Proxy mProxy = null;
    private static String mUserAgent = "Yunku Java SDK";
    private static String mAcceptLanguage = "Zh-CN";
    private static long mTimeout = 1800;
    private static long mConnectTimeout = 10;


    public static void setUserAgent(String userAgent) {
        NetConnection.mUserAgent = userAgent;
    }

    public static String getUserAgent() {
        return Util.isEmpty(mUserAgent) ? USER_AGENT : USER_AGENT + ";" + mUserAgent;
    }

    public static void setAcceptLanguage(String acceptLanguage) {
        NetConnection.mAcceptLanguage = acceptLanguage;
    }

    public static void setTimeout(long timeout) {
        NetConnection.mTimeout = timeout;
    }

    public static void setConnectTimeout(long timeout) {
        NetConnection.mConnectTimeout = timeout;
    }


    public static void setProxy(Proxy proxy) {
        NetConnection.mProxy = proxy;
    }


    /**
     * 发送请求
     *
     * @param url
     * @param method
     * @param params
     * @param headParams
     * @return
     */
    public static ReturnResult sendRequest(String url, RequestMethod method,
                                     HashMap<String, String> params, HashMap<String, String> headParams) {

        return sendRequest(url, method, params, headParams, NetConfig.POST_DEFAULT_FORM_TYPE);
    }

    private static final String URL_ENCODE = "application/x-www-form-urlencoded; charset=utf-8";

    private static final String JSON_STRING = "application/json; charset=utf-8";


    /**
     * 获取http请求返回的数据,组成bundle
     *
     * @param url
     * @param method
     * @param params
     * @param headParams
     * @return
     */
    public static ReturnResult sendRequest(String url, RequestMethod method,
                                     HashMap<String, String> params, HashMap<String, String> headParams, String postType) {
        LogPrint.info(LOG_TAG, "sendRequest(): url is: " + url + " params" + params + ", headParams, " + headParams);


        OkHttpClient client = getOkHttpClient();

        String paramsString = "";

        MediaType contentType = null;

        int statusCode = 0;
        String body = "";

        if (postType.equals(NetConfig.POST_DEFAULT_FORM_TYPE)) {
            paramsString = Util.getParamsStringFromHashMapParams(params);
            contentType = MediaType.parse(URL_ENCODE);

        } else if (postType.equals(NetConfig.POST_JSON_TYPE)) {
            paramsString = new Gson().toJson(params);
            contentType = MediaType.parse(JSON_STRING);
        }

        if (method.equals(RequestMethod.GET) && !Util.isEmpty(paramsString)) {
            url += "?" + paramsString;
            LogPrint.info(LOG_TAG, method + ":" + url);
        }


        Headers.Builder headerBuilder = new Headers.Builder();
        if (headParams != null) {

            for (String key : headParams.keySet()) {
                headerBuilder.add(key, headParams.get(key));
            }
        }

        String userAgent = getUserAgent();
        String language = Util.isEmpty(mAcceptLanguage) ? ACCEPT_LANGUAGE : mAcceptLanguage;

        headerBuilder.add("User-Agent", userAgent);

        headerBuilder.add("Accept-Language", language);

        Request.Builder requestBuilder = new Request.Builder();
        Request request = null;
        if (method.equals(RequestMethod.GET)) {
            request = requestBuilder.url(url).headers(headerBuilder.build()).get().build();

        } else if (method.equals(RequestMethod.POST)) {
            RequestBody postBody = RequestBody.create(contentType, paramsString);
            request = requestBuilder.url(url).post(postBody).headers(headerBuilder.build()).build();

        } else if (method.equals(RequestMethod.DELETE)) {
            RequestBody deleteBody = RequestBody.create(contentType, paramsString);
            request = requestBuilder.url(url).delete(deleteBody).headers(headerBuilder.build()).build();

        } else if (method.equals(RequestMethod.PUT)) {
            RequestBody putBody = RequestBody.create(contentType, paramsString);
            request = requestBuilder.url(url).put(putBody).headers(headerBuilder.build()).build();
        }

        if (request != null) {
            Response resp = null;
            try {
                resp = client.newCall(request).execute();

                if (resp.header("X-GOKUAI-DEBUG") != null) {
                    try {
                        LogPrint.error(LOG_TAG, "X-GOKUAI-DEBUG:" + new String(Base64.decode(resp.header("X-GOKUAI-DEBUG").getBytes())));
                    } catch (IOException e) {
                    }
                }

                statusCode = resp.code();
                body = resp.body().string();

                if (!Util.isEmpty(body)) {
                    if (body.length() > 1000) {
                        body = body.substring(0, 1000);
                    }
                    LogPrint.info(LOG_TAG, "response:" + body);
                }
            } catch (IOException e) {
                return new ReturnResult(e);
            }
        }
        return new ReturnResult(statusCode, body);
    }

    public static OkHttpClient getOkHttpClient() {

        ArrayList<Protocol> list = new ArrayList<Protocol>();
        list.add(Protocol.HTTP_1_1);
        list.add(Protocol.HTTP_2);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if (mProxy != null) {
            builder.proxy(mProxy);
        }

        builder.connectTimeout(mConnectTimeout, TimeUnit.SECONDS);
        builder.readTimeout(mTimeout, TimeUnit.SECONDS);
        return builder.build();
    }


}
