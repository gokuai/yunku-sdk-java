package com.yunkuent.sdk;

import com.yunkuent.sdk.utils.Util;
import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by qp on 2017/2/13.
 */
public abstract class HttpEngine extends SignAbility {

    protected static  String OVERWRITE_OAUTH_HOST_V2 = "";

    protected static  String OVERWRITE_API_ENT_HOST_V2 = "";

    //     protected static  String OAUTH_HOST = "http://yk3-api.goukuai.cn";
    protected static  String OAUTH_HOST = "http://yk3-api.gokuai.com";
    //    protected static  String API_ENT_HOST = "http://yk3-api-ent.goukuai.cn";
    protected static  String API_ENT_HOST = "http://yk3-api-ent.gokuai.com";

    //    protected static  String OAUTH_HOST_V2 = "http://zka.goukuai.cn";
    protected static String OAUTH_HOST_V2 = "http://a.goukuai.cn";
    //    protected static  String API_ENT_HOST_V2 = "http://zka-lib.goukuai.cn";
    //    protected static  String API_ENT_HOST_V2 = "http://test4a-lib.goukuai.cn";
    protected static String API_ENT_HOST_V2 = "http://a-lib.goukuai.cn";

    {
        if (!TextUtils.isEmpty(OVERWRITE_OAUTH_HOST_V2)) {

            OAUTH_HOST_V2 = OVERWRITE_OAUTH_HOST_V2;

        }else if (!TextUtils.isEmpty(OVERWRITE_API_ENT_HOST_V2)){

            API_ENT_HOST_V2 = OVERWRITE_API_ENT_HOST_V2;

        }

    }

    public  static void changeConfig(String oauth, String api){

        OVERWRITE_OAUTH_HOST_V2 = oauth;

        OVERWRITE_API_ENT_HOST_V2 = api;
    }

    protected String mClientSecret;
    protected String mClientId;


    public HttpEngine(String clientId, String clientSecret) {
        mClientId = clientId;
        mClientSecret = clientSecret;
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
    protected String generateSign(HashMap<String, String> params, ArrayList<String> ignoreKeys) {
        return generateSign(params, mClientSecret, ignoreKeys);
    }


    /**
     * 请求协助类
     */
    public class RequestHelper {
        RequestMethod method;
        HashMap<String, String> params;
        HashMap<String, String> headParams;
        String url;
        boolean checkAuth;

        ArrayList<String> ignoreKeys;

        public RequestHelper setMethod(RequestMethod method) {
            this.method = method;
            return this;
        }

        public RequestHelper setParams(HashMap<String, String> params) {
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

        public RequestHelper setUrl(String url) {
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
        public String executeSync() {
            checkNecessaryParams(url, method);

            if (!Util.isNetworkAvailableEx()) {
                return "";
            }

            if (checkAuth) {
                if (this instanceof IAuthRequest) {
                    return ((IAuthRequest) this).sendRequestWithAuth(url, method, params, headParams, ignoreKeys);
                }

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
