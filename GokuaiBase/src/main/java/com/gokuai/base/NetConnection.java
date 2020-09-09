package com.gokuai.base;

import com.gokuai.base.utils.Base64;
import com.gokuai.base.utils.Util;
import com.google.gson.Gson;
import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * http请求
 */
public final class NetConnection {

    private static final String LOG_TAG = "NetConnection";
    private static final String USER_AGENT = "YunkuJavaSDK;" + System.getProperties().getProperty("http.agent");

    private static Proxy mProxy = null;
    private static String mUserAgent = null;
    private static String mAcceptLanguage = "zh-CN";
    private static long mTimeout = 1800;
    private static long mConnectTimeout = 10;
    private static int mRetry = 0;
    private static boolean mTrustSsl = false;
    private static OkHttpClient mHttpClient = null;

    public static void setUserAgent(String userAgent) {
        mUserAgent = userAgent;
    }

    public static String getUserAgent() {
        return Util.isEmpty(mUserAgent) ? USER_AGENT : USER_AGENT + ";" + mUserAgent;
    }

    public static void trustSsl(boolean trust) {
        mTrustSsl = trust;
    }

    public static void setAcceptLanguage(String acceptLanguage) {
        mAcceptLanguage = acceptLanguage;
    }

    public static void setTimeout(long timeout) {
        mTimeout = timeout;
    }

    public static void setConnectTimeout(long timeout) {
        mConnectTimeout = timeout;
    }

    public static void setProxy(Proxy proxy) {
        mProxy = proxy;
    }

    public static void setRetry(int retry) {
        mRetry = retry;
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
     * @param headerMap
     * @return
     */
    public static ReturnResult sendRequest(String url, RequestMethod method,
                                           HashMap<String, String> params, HashMap<String, String> headerMap, String postType) {
        LogPrint.debug(LOG_TAG, "sendRequest(): url is: " + url + " params" + params + ", headers, " + headerMap);

        String paramsString = "";
        MediaType contentType = null;

        if (postType.equals(NetConfig.POST_DEFAULT_FORM_TYPE)) {
            paramsString = Util.mapToQueryString(params);
            contentType = MediaType.parse(URL_ENCODE);

        } else if (postType.equals(NetConfig.POST_JSON_TYPE)) {
            paramsString = new Gson().toJson(params);
            contentType = MediaType.parse(JSON_STRING);
        }

        Headers.Builder headerBuilder = new Headers.Builder();
        headerBuilder.set("User-Agent", getUserAgent());
        headerBuilder.set("Accept-Language", mAcceptLanguage);
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                String value = headerMap.get(key);
                if (value == null) {
                    continue;
                }
                headerBuilder.set(key, value);
            }
        }
        Headers headers = headerBuilder.build();

        LogPrint.debug(LOG_TAG, method + " " + url + " " + paramsString + " " + headers.toString());

        Request request;
        if (method.equals(RequestMethod.GET)) {

            if (!Util.isEmpty(paramsString)) {
                url += (url.indexOf("?") > 0 ? "&" : "?") + paramsString;
            }
            request = new Request.Builder().get().url(url).headers(headers).build();

        } else if (method.equals(RequestMethod.POST) || method.equals(RequestMethod.PUT)) {

            RequestBody postBody = RequestBody.create(contentType, paramsString);
            request = new Request.Builder().method(method.toString(), postBody).url(url).headers(headers).build();

        } else {
            return null;
        }


        int statusCode = 0;
        String body = "";
        int retry = mRetry;
        while (true) {
            try {
                OkHttpClient client = getOkHttpClient();
                Response resp = client.newCall(request).execute();

                if (resp.header("X-GOKUAI-DEBUG") != null) {
                    try {
                        LogPrint.debug(LOG_TAG, "X-GOKUAI-DEBUG: " + new String(Base64.decode(resp.header("X-GOKUAI-DEBUG").getBytes())));
                    } catch (IOException e) {
                    }
                }

                statusCode = resp.code();
                body = resp.body().string();

                if (!Util.isEmpty(body)) {
                    String response = body;
                    if (response.length() > 1000) {
                        response = body.substring(0, 1000);
                    }
                    LogPrint.debug(LOG_TAG, "response: " + response);
                }

                break;

            } catch (IOException e) {

                LogPrint.error(LOG_TAG, e.getMessage());
                if (retry-- > 0) {
                    //网络异常自动重试
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    return new ReturnResult(e);
                }
            }

        }

        return new ReturnResult(statusCode, body);
    }

    private static final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };
    private static final SSLContext trustAllSslContext;

    static {
        try {
            trustAllSslContext = SSLContext.getInstance("SSL");
            trustAllSslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    private static final SSLSocketFactory trustAllSslSocketFactory = trustAllSslContext.getSocketFactory();

    public static synchronized OkHttpClient getOkHttpClient() {
        if (mHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            if (mTrustSsl) {
                builder.sslSocketFactory(trustAllSslSocketFactory, (X509TrustManager)trustAllCerts[0]);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            }
            if (mProxy != null) {
                builder.proxy(mProxy);
            }
            builder.connectTimeout(mConnectTimeout, TimeUnit.SECONDS);
            builder.readTimeout(mTimeout, TimeUnit.SECONDS);
            mHttpClient = builder.build();
        }
        return mHttpClient;
    }
}
