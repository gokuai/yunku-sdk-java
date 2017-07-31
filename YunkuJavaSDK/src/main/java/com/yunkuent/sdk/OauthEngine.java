package com.yunkuent.sdk;

import com.gokuai.base.*;
import com.gokuai.base.utils.Base64;
import com.gokuai.base.utils.Util;
import com.yunkuent.sdk.data.OauthData;
import org.apache.http.HttpStatus;
import org.apache.http.util.TextUtils;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Brandon on 2014/8/6.
 */
abstract class OauthEngine extends HttpEngine implements IAuthRequest {

    private final static String LOG_TAG = HttpEngine.class.getSimpleName();

    protected final String URL_API_TOKEN = HostConfig.OAUTH_HOST + "/oauth2/token2";

    protected String mTokenType;
    protected String refreshToken;
    protected String mToken;

    protected boolean mIsEnt;

    public OauthEngine(String clientId, String clientSecret, boolean isEnt) {
        super(clientId, clientSecret);
        mIsEnt = isEnt;
        mTokenType = isEnt ? "ent" : "";
    }

    public OauthEngine(String clientId, String clientSecret, boolean isEnt, String token) {
        this(clientId, clientSecret, isEnt);
        mToken = token;
    }

    /**
     * 使用帐号用户名获取token
     *
     * @return
     */
    public String accessToken(String username, String password) {
        String url = URL_API_TOKEN;
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        String passwordEncoded;
        if (username.indexOf("/") > 0 || username.indexOf("\\") > 0) {
            passwordEncoded = Base64.encodeBytes(password.getBytes());
        } else {
            passwordEncoded = Util.convert2MD532(password);
        }
        params.put("password", passwordEncoded);
        params.put("client_id", mClientId);
        params.put("grant_type", mIsEnt ? "ent_password" : "password");
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("sign", generateSign(params));

        String result = NetConnection.sendRequest(url, RequestMethod.POST, params, null);
        ReturnResult returnResult = ReturnResult.create(result);
        LogPrint.info(LOG_TAG, "accessToken:==>result:" + result);

        if (returnResult.getStatusCode() == HttpStatus.SC_OK) {
            LogPrint.info(LOG_TAG, "accessToken:==>StatusCode:200");
            OauthData data = OauthData.create(returnResult.getResult());
            mToken = data.getToken();
        }
        return result;
    }

    /**
     * 使用第三方API OUTID 登录
     *
     * @param outId
     * @return
     */
    public String accessTokenWithThirdPartyOutId(String outId) {
        return new ThirdPartyManager(mClientId, mClientSecret, outId).getEntToken();
    }

    /**
     * 添加认证参数
     *
     * @param params
     */
    protected void addAuthParams(HashMap<String, String> params) {
        if (mToken == null) {
            params.put("client_id", mClientId);
            params.put("dateline", Util.getUnixDateline() + "");
        } else {
            params.put("token", mToken);
            params.put("token_type", mTokenType);
        }

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

        String returnString = new RequestHelper().setUrl(URL_API_TOKEN).setMethod(RequestMethod.POST).setParams(params).executeSync();
        ReturnResult returnResult = ReturnResult.create(returnString);
        OauthData data = OauthData.create(returnResult.getResult());
        if (data != null) {
            data.setCode(returnResult.getStatusCode());
            if (data.getCode() == HttpURLConnection.HTTP_OK) {
                mToken = data.getToken();
                refreshToken = data.getRefresh_token();
                return true;
            }

            LogPrint.info(LOG_TAG, "token:" + mToken + "_refreshToken:" + refreshToken);
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
    public String sendRequestWithAuth(String url, RequestMethod method,
                                      HashMap<String, String> params, HashMap<String, String> headParams, ArrayList<String> ignoreKeys, String postType) {
        String returnString = NetConnection.sendRequest(url, method, params, headParams, postType);
        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult.getStatusCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            refreshToken();
            reSignParams(params, ignoreKeys);
            returnString = NetConnection.sendRequest(url, method, params, headParams);
        }
        return returnString;
    }

    /**
     * 重新进行签名
     *
     * @param params
     * @param ignoreKeys
     */
    private void reSignParams(HashMap<String, String> params, ArrayList<String> ignoreKeys) {
        reSignParams(params, mClientSecret, ignoreKeys);
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
        params.put("token", mToken);
        params.put("sign", generateSign(params, secret, ignoreKeys));
    }


}