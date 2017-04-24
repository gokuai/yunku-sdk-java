package com.gokuai.base.data;

import org.json.JSONObject;

/**
 * oauth返回数据
 * 
 * httpcode-200: access_token: 要获取的“access_token” expires_in:
 * “access_token”的有效期，一秒为单位 refresh_token: 用于刷新“access_token” httpcode-: error:
 * 
 */
public class OauthData {

	private final static String ACCESS_TOKEN = "access_token";
	private final static String EXPIRES_IN = "expires_in";
	private final static String REFRESH_TOKEN = "refresh_token";
	private final static String ERROR = "error";

	private int code = 0;
	private String access_token;
	private int expires_in;
	private String refresh_token;
    private String error;

	public static OauthData create(String jsonString) {
		JSONObject json = null;

		try {
			json = new JSONObject(jsonString);
		} catch (Exception e) {
			json = null;
		}

		if (json == null) {
			return null;
		}

        OauthData data = new OauthData();
        data.setToken(json.optString(ACCESS_TOKEN));
        data.setExpires_in(json.optInt(EXPIRES_IN));
		data.setRefresh_token(json.optString(REFRESH_TOKEN));
        data.setError(json.optString(ERROR));
        return data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getToken() {
		return access_token;
	}

	public void setToken(String token) {
		this.access_token = token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

	@Override
	public String toString() {
		return "OauthData [code=" + code + ", error=" + error + ", token="
				+ access_token + "]";
	}

    public void setError(String error) {
        this.error = error;
    }
}
