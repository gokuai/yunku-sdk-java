package com.gokuai.base;

import com.gokuai.base.data.OauthData;
import com.gokuai.base.data.ReturnResult;
import com.gokuai.base.utils.Util;
import org.apache.http.util.TextUtils;

import java.net.HttpURLConnection;
import java.util.*;

/**
 * Created by qp on 2017/4/21.
 */
public abstract class HttpEngine extends SignAbility {

    private final static String LOG_TAG = "HttpEngine";

    public final static int API_ID_GET_FILE_INFO = 50;
    public final static int API_ID_GET_URL_BY_HASH = 49;

    protected String URL_OAUTH;

    protected String token;
    protected String refreshToken;

    protected String mClientSecret;
    protected String mClientId;


    public HttpEngine(String clientId, String clientSecret) {
        mClientId = clientId;
        mClientSecret = clientSecret;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }


    public boolean isTokenAvailable() {
        return !TextUtils.isEmpty(token);
    }

    public static final int ERRORID_NETDISCONNECT = 1;

    /**
     * 从服务器获得数据后的回调
     *
     * @author Administrator
     */
    public interface DataListener {
        void onReceivedData(int apiId, Object object, int errorId);
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
     * 重新根据参数进行签名
     *
     * @param params
     * @param secret
     * @param ignoreKeys
     */
    protected void reSignParams(HashMap<String, String> params, String secret,
                                ArrayList<String> ignoreKeys) {
        params.remove("token");
        params.remove("sign");
        params.put("token", getToken());
        params.put("sign", generateSign(params, secret, ignoreKeys));
    }

    private void reSignParams(HashMap<String, String> params, ArrayList<String> ignoreKeys) {
        reSignParams(params, mClientSecret, ignoreKeys);
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

        public RequestHelper setHeadParams(HashMap<String, String> headParams) {
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
                    return sendRequestWithAuth(url, method, params, headParams, ignoreKeys);
                }

            }
            return NetConnection.sendRequest(url, method, params, headParams);
        }

        /**
         * 异步执行
         *
         * @return
         */
        Thread executeAsync(final DataListener listener, final int apiId, final RequestHelperCallBack callBack) {

            checkNecessaryParams(url, method);

            if (listener != null) {
                if (!Util.isNetworkAvailableEx()) {
                    listener.onReceivedData(apiId, null, ERRORID_NETDISCONNECT);
                    return null;
                }
            }

            Thread thread = new Thread() {
                @Override
                public void run() {
                    String returnString;

                    if (checkAuth) {
                        returnString = sendRequestWithAuth(url, method, params, headParams, ignoreKeys);
                    } else {
                        returnString = NetConnection.sendRequest(url, method, params, headParams);
                    }

                    if (callBack != null) {
                        if (listener != null) {
                            Object object = callBack.getReturnData(returnString);
                            listener.onReceivedData(apiId, object, -1);
                        }
                    }
                }
            };
            thread.start();
            return thread;
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

    private String sendRequestWithAuth(String url, RequestMethod method, HashMap<String, String> params, HashMap<String, String> headParams, ArrayList<String> ignoreKeys) {
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
     * 重新获得 token
     */
    private boolean refreshToken() {
        if (TextUtils.isEmpty(refreshToken)) {
            return false;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);
        params.put("client_id", mClientId);
        params.put("sign", generateSign(params));

        String returnString = new RequestHelper().setUrl(URL_OAUTH).setMethod(RequestMethod.POST).setParams(params).executeSync();
        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult != null) {
            OauthData data = OauthData.create(returnResult.getResult());
            if (data != null) {
                data.setCode(returnResult.getStatusCode());
                if (data.getCode() == HttpURLConnection.HTTP_OK) {
                    token = data.getToken();
                    refreshToken = data.getRefresh_token();
                    return true;
                }

                LogPrint.info(LOG_TAG, "token:" + token + "_refreshToken:" + refreshToken);
            }

        }
        return false;
    }

    interface RequestHelperCallBack {
        Object getReturnData(String returnString);
    }

}
