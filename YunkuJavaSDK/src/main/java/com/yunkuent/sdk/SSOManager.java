package com.yunkuent.sdk;

import com.gokuai.base.HttpEngine;
import com.gokuai.base.RequestMethod;
import com.gokuai.base.utils.Base64;
import com.gokuai.base.utils.URLEncoder;
import com.gokuai.base.utils.Util;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by wink on 2018/3/12.
 */
public class SSOManager extends HttpEngine {

    private final String URL_ENTGRANT_URL = HostConfig.OAUTH_HOST + "/account/autologin/entgrant";

    public SSOManager(String clientId, String clientSecret) {
        super(clientId, clientSecret);
    }

    private Integer getRandomNum(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 获取单点登录URL
     *
     * @param account 需要登录的帐号
     * @param returnUrl 登录成功后跳转url, 传null表示跳转到/web/index
     * @return
     */
    public String getSsoUrl(String account, String returnUrl) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("account", account);
        params.put("n", this.getRandomNum(100000, 999999).toString());
        params.put("t", Util.getUnixDateline() + "");
        params.put("sign", generateSign(params));

        JSONObject jsonObj = new JSONObject(params);

        String ticket = Base64.encodeBytes(jsonObj.toString().getBytes());
        String url = URL_ENTGRANT_URL + "?client_id=" + mClientId + "&ticket=" + URLEncoder.encodeUTF8(ticket);
        if (returnUrl != null && returnUrl.length() > 0) {
            url += "&returnurl=" + URLEncoder.encodeUTF8(returnUrl);
        }
        return url;
    }

    /**
     * 获取gkkey
     *
     * @param account 需要登录的帐号
     * @return {"gkkey":""}
     */
    public String getSsoGkkey(String account) {
        String url = this.getSsoUrl(account, null) + "&format=json";
        return new RequestHelper().setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }
}
