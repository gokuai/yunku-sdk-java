package com.gokuai.base;

import com.gokuai.base.utils.Base64;
import com.gokuai.base.utils.Util;
import com.google.gson.Gson;
import okhttp3.*;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.SocketTimeoutException;
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
    private static final int TIMEOUT = 30000;
    private static final int CONNECT_TIMEOUT = 30000;

    private static Proxy mProxy = null;
    private static String mUserAgent = null;
    private static String mAcceptLanguage = null;
    private static long mTimeOut = 0;
    private static long mConnectTimeOut = 0;


    public static void setUserAgent(String userAgent) {
        NetConnection.mUserAgent = userAgent;
    }

    public static String getUserAgent() {
        return Util.isEmpty(mUserAgent) ? USER_AGENT : USER_AGENT + ";" + mUserAgent;
    }


    public static void setAcceptLanguage(String acceptLanguage) {
        NetConnection.mAcceptLanguage = acceptLanguage;
    }

    public static void setTimeOut(long timeOut) {
        NetConnection.mTimeOut = timeOut;
    }


    public static void setConnectTimeOut(long connectTimeOut) {
        NetConnection.mConnectTimeOut = connectTimeOut;
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
    public static String sendRequest(String url, RequestMethod method,
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
    public static String sendRequest(String url, RequestMethod method,
                                     HashMap<String, String> params, HashMap<String, String> headParams, String postType) {
        LogPrint.info(LOG_TAG, "sendRequest(): url is: " + url + " params" + params + ", headParams, " + headParams);


        OkHttpClient client = getOkHttpClient();

        String paramsString = "";

        MediaType contentType = null;

        ReturnResult returnResult = new ReturnResult();

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
            Response response = null;
            try {
                response = client.newCall(request).execute();

                if (response.header("X-GOKUAI-DEBUG") != null) {
                    LogPrint.error(LOG_TAG, "X-GOKUAI-DEBUG:" + new String(Base64.decode(response.header("X-GOKUAI-DEBUG").getBytes())));
                }

                returnResult.setStatusCode(response.code());

                String responseString = "";
                responseString = response.body().string();
                returnResult.setResult(responseString);

                if (!Util.isEmpty(responseString)) {
                    if (responseString.length() > 1000) {
                        responseString = responseString.substring(0, 1000);
                    }
                    LogPrint.info(LOG_TAG, "response:" + responseString);
                }

            } catch (Exception e) {
                if (e.getCause() != null && e.getCause().equals(SocketTimeoutException.class)) {
                    returnResult.setStatusCode(HttpURLConnection.HTTP_CLIENT_TIMEOUT);
                }
                LogPrint.error(LOG_TAG, e.getMessage());
            }
        }
        return new Gson().toJson(returnResult);
    }

    public static OkHttpClient getOkHttpClient() {

        ArrayList<Protocol> list = new ArrayList<Protocol>();
        list.add(Protocol.HTTP_1_1);
        list.add(Protocol.HTTP_2);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if (mProxy != null) {
            builder.proxy(mProxy);
        }

        long readTimeOut = mTimeOut > 0 ? mTimeOut : TIMEOUT;
        long connectTimeOut = mConnectTimeOut > 0 ? mConnectTimeOut : CONNECT_TIMEOUT;

        builder.connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS);
        builder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
        return builder.build();
    }


}
