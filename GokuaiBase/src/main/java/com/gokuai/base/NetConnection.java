package com.gokuai.base;

import com.gokuai.base.utils.Base64;
import com.gokuai.base.utils.Util;
import com.google.gson.Gson;
import okhttp3.*;
import org.apache.http.util.TextUtils;

import java.io.IOException;
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
    public static final String USER_AGENT = "GK_ANDROID" + ";" + System.getProperties().getProperty("http.agent");
    public static final String ACCEPT_LANGUAGE = Locale.getDefault().toString().contains("zh") ? "zh-CN" : "en-US";
    public static final int TIMEOUT = 30000;
    public static final int CONNECT_TIMEOUT = 30000;

    public static Proxy mProxy = null;

    public static void setProxy(Proxy proxy) {
        mProxy = proxy;
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
        LogPrint.info(LOG_TAG, "sendRequest(): url is: " + url + " " + params);


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

        if (method.equals(RequestMethod.GET) && !TextUtils.isEmpty(paramsString)) {
            url += "?" + paramsString;
            LogPrint.info(LOG_TAG, method + ":" + url);
        }


        Headers.Builder headerBuilder = new Headers.Builder();
        if (headParams != null) {

            for (String key : headParams.keySet()) {
                headerBuilder.add(key, headParams.get(key));
            }
        }

        headerBuilder.add("User-Agent", USER_AGENT);

        headerBuilder.add("Accept-Language", ACCEPT_LANGUAGE);

        Request.Builder requestBuilder = new Request.Builder();
        Request request = null;
        switch (method) {
            case GET:
                request = requestBuilder.url(url).headers(headerBuilder.build()).get().build();
                break;
            case POST:
            case DELETE:
            case PUT:
                RequestBody body = RequestBody.create(contentType, paramsString);
                request = requestBuilder.url(url).post(body).headers(headerBuilder.build()).build();
                break;
        }

        if (request != null) {
            Response response = null;
            try {
                response = client.newCall(request).execute();

                if (response.header("X-GOKUAI-DEBUG") != null) {
                    LogPrint.error(LOG_TAG, "X-GOKUAI-DEBUG:" + new String(Base64.decode(response.header("X-GOKUAI-DEBUG").getBytes())));
                }
                returnResult.setStatusCode(response.code());
                returnResult.setResult(response.body().string());
            } catch (IOException | NullPointerException e) {
                if (e.getCause().equals(SocketTimeoutException.class)) {
                    returnResult.setStatusCode(HttpURLConnection.HTTP_CLIENT_TIMEOUT);
                }
                LogPrint.error(LOG_TAG, e.getMessage());

            }
        }
        return new Gson().toJson(returnResult);
    }

    public static OkHttpClient getOkHttpClient() {

        ArrayList<Protocol> list = new ArrayList<>();
        list.add(Protocol.HTTP_1_1);
        list.add(Protocol.HTTP_2);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if (mProxy != null) {
            builder.proxy(mProxy);
        }

        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        return builder.build();
    }
}
