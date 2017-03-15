package com.yunkuent.sdk;

import com.yunkuent.sdk.data.ReturnResult;
import com.yunkuent.sdk.utils.Base64;
import com.yunkuent.sdk.utils.Util;
import org.apache.http.HttpStatus;

import java.util.HashMap;

/**
 * Created by Brandon on 2014/8/6.
 */
abstract class OauthEngine extends HttpEngine {

    protected boolean mIsEnt;

    public OauthEngine(String clientId, String clientSecret, boolean isEnt) {
        mClientId = clientId;
        mClientSecret = clientSecret;
        mIsEnt = isEnt;
        mTokenType = isEnt ? "ent" : "";
    }

    /**
     * 获取token
     *
     * @return
     */
    public String accessToken(String username, String password) {
        String url = URL_API_TOKEN;
        String method = "POST";
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
        LogPrint.print("accessToken:==>result:" + result);

        if (returnResult.getStatusCode() == HttpStatus.SC_OK) {
            LogPrint.print("accessToken:==>StatusCode:200");
            OauthData data = OauthData.create(returnResult.getResult());
            mToken = data.getToken();
        }
        return result;
    }

    public String getToken() {
        return mToken;
    }

}
