package com.gokuai.yunku.demo.model;

import org.json.JSONObject;

/**
 * 解析基础类
 */
public class BaseData {
    protected final static String KEY_ERRORCODE = "error_code";
    protected final static String KEY_ERRORMSG = "error_msg";

    protected int error_code;
    protected String error_msg;
    private String key;

    public int getErrorCode() {
        return error_code;
    }

    public void setErrorCode(int errno_code) {
        this.error_code = errno_code;
    }

    public String getErrorMsg() {
        return error_msg;
    }

    public void setErrorMsg(String errno_msg) {
        this.error_msg = errno_msg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static BaseData create(String jsonString) {
        BaseData data = new BaseData();
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
        } catch (Exception e) {
            json = null;
        }
        if (json == null) {
            return null;
        }
        data.setErrorCode(json.optInt(KEY_ERRORCODE));
        data.setErrorMsg(json.optString(KEY_ERRORMSG));
        return data;
    }

    public static BaseData create(String jsonString, String key) {
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
        } catch (Exception e) {
            json = null;
        }
        if (json == null) {
            return null;
        }

        BaseData data = new BaseData();
        data.setKey(json.optString(key));
        data.setErrorCode(json.optInt(KEY_ERRORCODE));
        data.setErrorMsg(json.optString(KEY_ERRORMSG));
        return data;
    }


}
