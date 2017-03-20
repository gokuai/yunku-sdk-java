package com.yunkuent.sdk;

import com.yunkuent.sdk.utils.Util;
import org.apache.http.util.TextUtils;

import java.util.HashMap;

/**
 * Created by qp on 2017/3/9.
 */
public class ThirdPartyManager extends HttpEngine {

    private static final String URL_API_CREATE_ENT = OAUTH_HOST + "/1/thirdparty/create_ent";
    private static final String URL_API_ENT_INFO = OAUTH_HOST + "/1/thirdparty/ent_info";
    private static final String URL_API_ORDER = OAUTH_HOST + "/1/thirdparty/order";
    private static final String URL_GET_TOKEN = OAUTH_HOST + "/1/thirdparty/get_token";
    private static final String URL_GET_SSO_URL = OAUTH_HOST + "/1/thirdparty/get_sso_url";

    public static final String SUBSCRIBE = "orderSubscribe";
    public static final String UPGRADE = "orderUpgrade";
    public static final String RENEW = "orderRenew";
    public static final String UNSUBSCRIBE = "orderUnsubscribe";

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
        String url = URL_API_CREATE_ENT;
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", mClientId);
        params.put("out_id", OUT_ID);
        params.put("ent_name", entName);
        params.put("contact_name", contactName);
        params.put("contact_mobile", contactMobile);
        params.put("contact_email", contactEmail);
        params.put("contact_address", contactAddress);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
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
        HashMap<String, String> params = new HashMap<>();
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
        HashMap<String, String> params = new HashMap<>();
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
        return order(UPGRADE, -1, 0, month);
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
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", mClientId);
        params.put("out_id", OUT_ID);
        if (!TextUtils.isEmpty(type)) {
            params.put("type", type);
            switch (type) {
                case SUBSCRIBE:
                    params.put("member_count", memberCount + "");
                    params.put("space", space + "");
                    params.put("month", month + "");
                    break;
                case UPGRADE:
                    params.put("member_count", memberCount + "");
                    params.put("space", space + "");
                    break;
                case RENEW:
                    params.put("month", month + "");
                    break;
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
        HashMap<String, String> params = new HashMap<>();
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
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", mClientId);
        params.put("out_id", OUT_ID);
        params.put("ticket", ticket);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }
}
