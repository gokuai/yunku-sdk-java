package com.yunkuent.sdk;

import com.yunkuent.sdk.data.ReturnResult;
import com.yunkuent.sdk.utils.Util;
import org.apache.http.util.TextUtils;


import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by qp on 2017/2/13.
 */
public abstract class HttpEngine extends SignAbility{

    protected String mClientSecret;
    protected String mClientId;

    protected static final String URL_API_TOKEN = OAUTH_HOST + "/oauth2/token2";

    private final static String LOG_TAG = HttpEngine.class.getSimpleName();


    protected String mTokenType;
    protected String refreshToken;
    protected String mToken;

    public String getToken(){
        return mToken;
    }

    /**
     * API签名,SSO签名
     *
     * @param params
     * @return
     */
    public String generateSign(HashMap<String, String> params) {
        return generateSign(params, mClientSecret);
    }

    /**
     * @param params
     * @param ignoreKeys 忽略签名
     * @return
     */
    private String generateSign(HashMap<String, String> params, ArrayList<String> ignoreKeys) {
        return generateSign(params, mClientSecret, ignoreKeys);
    }

    private void reSignParams(HashMap<String, String> params, ArrayList<String> ignoreKeys) {
        reSignParams(params, mClientSecret, ignoreKeys);
    }

    /**
     * 重新获得token
     */
    public boolean refreshToken() {
        if (TextUtils.isEmpty(refreshToken)) {
            return false;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);
        params.put("client_id", mClientId);
        params.put("sign", generateSign(params));

        String returnString = new RequestHelper().setUrl(URL_API_TOKEN).setMethod(RequestMethod.POST).setParams(params).executeSync();
        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult != null) {
            OauthData data = OauthData.create(returnResult.getResult());
            if (data != null) {
                data.setCode(returnResult.getStatusCode());
                if (data.getCode() == HttpURLConnection.HTTP_OK) {
                    mToken = data.getToken();
                    refreshToken = data.getRefresh_token();
                    return true;
                }

                LogPrint.print(LOG_TAG + "token:" + mToken + "_refreshToken:" + refreshToken);
            }

        }
        return false;
    }

    /**
     * 如果身份验证有问题,会自动刷token
     *
     * @param url
     * @param method
     * @param params
     * @param headParams
     * @param ignoreKeys
     * @return
     */
    private String sendRequestWithAuth(String url, RequestMethod method,
                                       HashMap<String, String> params, HashMap<String, String> headParams, ArrayList<String> ignoreKeys) {
        String returnString = NetConnection.sendRequest(url, method, params, headParams);
        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult != null) {
            if (returnResult.getStatusCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                refreshToken();
                reSignParams(params, ignoreKeys);
                returnString = NetConnection.sendRequest(url, method, params, headParams);
            }
        }
        return returnString;
    }

    /**
     * 请求协助类
     */
    protected class RequestHelper {
        RequestMethod method;
        HashMap<String, String> params;
        HashMap<String, String> headParams;
        String url;
        boolean checkAuth;

        ArrayList<String> ignoreKeys;

        RequestHelper setMethod(RequestMethod method) {
            this.method = method;
            return this;
        }

        RequestHelper setParams(HashMap<String, String> params) {
            this.params = params;
            return this;
        }

        RequestHelper setHeadParams(HashMap<String, String> headParams) {
            this.headParams = headParams;
            return this;
        }

        RequestHelper setCheckAuth(boolean checkAuth) {
            this.checkAuth = checkAuth;
            return this;
        }

        RequestHelper setUrl(String url) {
            this.url = url;
            return this;
        }

        public RequestHelper setIgnoreKeys(ArrayList<String> ignoreKeys) {
            this.ignoreKeys = ignoreKeys;
            return this;
        }

        /**
         * 同步执行
         *
         * @return
         */
        String executeSync() {
            checkNecessaryParams(url, method);

            if (!Util.isNetworkAvailableEx()) {
                return "";
            }

            if (checkAuth) {
                return sendRequestWithAuth(url, method, params, headParams, ignoreKeys);
            }
            return NetConnection.sendRequest(url, method, params, headParams);
        }

        private void checkNecessaryParams(String url, RequestMethod method) {
            if (TextUtils.isEmpty(url)) {
                throw new IllegalArgumentException("url must not be null");
            }

            if (method == null) {
                throw new IllegalArgumentException("method must not be null");
            }
        }
    }
}
