package com.yunkuent.sdk;

/**
 * 转化Oauth错误信息
 */
public class OauthErrorMsg {
    private final static String ERRORRESPONES_INVALID_REQUEST = "invalid_request";
    private final static String ERRORRESPONES_INVALID_CLIENT = "invalid_client";
    private final static String ERRORRESPONES_INVALID_GRANT = "invalid_grant";
    private final static String ERRORRESPONES_UNAUTHORIED_CLIENT = "unauthorized_client";
    private final static String ERRORRESPONES_ACCESS_DENIED = "access_denied";

    public static String convertMsg(String errorMsg)
    {
        if (errorMsg.equals(ERRORRESPONES_INVALID_REQUEST))
        {
            return "请求参数错误";
        }
        else if (errorMsg.equals(ERRORRESPONES_INVALID_CLIENT))
        {
            return "当前客户端版本已不能使用";
        }
        else if (errorMsg.equals(ERRORRESPONES_INVALID_GRANT))
        {
            return "邮箱或密码错误";
        }
        else if (errorMsg.equals(ERRORRESPONES_UNAUTHORIED_CLIENT))
        {
            return "该设备已限制使用";
        }
        else if (errorMsg.equals(ERRORRESPONES_ACCESS_DENIED))
        {
            return "您的客户端已被限制登录";
        }
        return "请求错误，请重试";
    }
}
