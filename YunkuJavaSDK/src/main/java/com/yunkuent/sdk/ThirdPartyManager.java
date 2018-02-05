package com.yunkuent.sdk;

import com.gokuai.base.HttpEngine;
import com.gokuai.base.RequestMethod;
import com.gokuai.base.utils.Util;

import java.util.HashMap;

/**
 * Created by qp on 2017/3/9.
 */
public class ThirdPartyManager extends HttpEngine {

    private final String URL_API_CREATE_ENT = HostConfig.OAUTH_HOST + "/1/thirdparty/create_ent";
    private final String URL_API_ENT_INFO = HostConfig.OAUTH_HOST + "/1/thirdparty/ent_info";
    private final String URL_API_ORDER = HostConfig.OAUTH_HOST + "/1/thirdparty/order";
    private final String URL_GET_TOKEN = HostConfig.OAUTH_HOST + "/1/thirdparty/get_token";
    private final String URL_GET_SSO_URL = HostConfig.OAUTH_HOST + "/1/thirdparty/get_sso_url";

    public final String SUBSCRIBE = "orderSubscribe";
    public final String UPGRADE = "orderUpgrade";
    public final String RENEW = "orderRenew";
    public final String UNSUBSCRIBE = "orderUnsubscribe";

    public static String OUT_ID = "";

    public ThirdPartyManager(String clientId, String clientSecret, String outId) {
        super(clientId, clientSecret);
        OUT_ID = outId;
    }

    /**
     * 开通企业
     *
     * @param entName
     * @param contactName
     * @param contactMobile
     * @param contactEmail
     * @param contactAddress
     * @return
     */
    public String createEnt(String entName, String contactName, String contactMobile, String contactEmail, String contactAddress) {
        return createEnt(null,entName,contactName,contactMobile,contactEmail,contactAddress);
    }

    /**
     * 扩展参数
     *
     * @param map
     * @param entName
     * @param contactName
     * @param contactMobile
     * @param contactEmail
     * @param contactAddress
     * @return
     */
    public String createEnt(HashMap<String, String> map, String entName, String contactName, String contactMobile,
                            String contactEmail, String contactAddress) {
        String url = URL_API_CREATE_ENT;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("client_id", mClientId);
        params.put("out_id", OUT_ID);
        params.put("ent_name", entName);
        params.put("contact_name", contactName);
        params.put("contact_mobile", contactMobile);
        params.put("contact_email", contactEmail);
        params.put("contact_address", contactAddress);
        params.put("dateline", Util.getUnixDateline() + "");
        if (map != null) {
            params.putAll(map);
        }
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 获取企业信息
     *
     * @return
     */
    public String getEntInfo() {
        String url = URL_API_ENT_INFO;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("client_id", mClientId);
        params.put("out_id", OUT_ID);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 升级
     *
     * @param memberCount
     * @param space
     * @return
     */
    public String orderUpgrade(int memberCount, int space) {

        return order(UPGRADE, memberCount, space, 0);
    }

    /**
     * 续费
     *
     * @param month
     * @return
     */
    public String orderRenew(int month) {
        return order(RENEW, -1, 0, month);
    }

    /**
     * 退订
     *
     * @return
     */
    public String orderUnsubscribe() {
        return order(UNSUBSCRIBE, -1, 0, 0);
    }

    /**
     * 购买
     *
     * @param memberCount
     * @param space
     * @param month
     * @return
     */
    public String orderSubscribe(int memberCount, int space, int month) {
        return order(SUBSCRIBE, memberCount, space, month);
    }


    /**
     * 订单处理
     *
     * @param type
     * @param memberCount
     * @param space
     * @param month
     * @return
     */
    private String order(String type, int memberCount, int space, int month) {
        String url = URL_API_ORDER;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("client_id", mClientId);
        params.put("out_id", OUT_ID);
        if (!Util.isEmpty(type)) {
            params.put("type", type);
            if (SUBSCRIBE.equals(type)) {
                params.put("member_count", memberCount + "");
                params.put("space", space + "");
                params.put("month", month + "");

            } else if (UPGRADE.equals(type)) {
                params.put("member_count", memberCount + "");
                params.put("space", space + "");

            } else if (RENEW.equals(type)) {
                params.put("month", month + "");

            }
        }
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 获取企业token
     *
     * @return
     */
    public String getEntToken() {
        String url = URL_GET_TOKEN;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("client_id", mClientId);
        params.put("out_id", OUT_ID);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 获取单点登录地址
     *
     * @param ticket
     * @return
     */
    public String getSsoUrl(String ticket) {
        String url = URL_GET_SSO_URL;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("client_id", mClientId);
        params.put("out_id", OUT_ID);
        params.put("ticket", ticket);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }
}
