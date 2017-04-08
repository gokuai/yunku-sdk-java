package com.yunkuent.sdk.compat.v2;

import com.yunkuent.sdk.*;
import com.yunkuent.sdk.data.ReturnResult;
import com.yunkuent.sdk.utils.Base64;
import com.yunkuent.sdk.utils.Util;
import org.apache.http.HttpStatus;

import java.util.HashMap;

/**
 * Created by Brandon on 2017/3/20.
 */
abstract class OauthEngine extends HttpEngine {

    private final static String LOG_TAG = HttpEngine.class.getSimpleName();

    protected static final String URL_API_TOKEN = HostConfig.OAUTH_HOST_V2 + "/oauth2/token2";

    protected String mToken;
    protected boolean mIsEnt;
    protected String mTokenType;

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
     * 添加认证参数
     *
     * @param params
     */
    protected void addAuthParams(HashMap<String, String> params) {
            params.put("token", mToken);
            params.put("token_type", mTokenType);
    }
}
