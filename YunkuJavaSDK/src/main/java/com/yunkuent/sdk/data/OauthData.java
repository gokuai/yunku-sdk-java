package com.yunkuent.sdk.data;

import com.gokuai.base.utils.Util;
import org.json.JSONObject;

public class OauthData {

    private final static String ACCESS_TOKEN = "access_token";
    private final static String EXPIRES_IN = "expires_in";
    private final static String REFRESH_TOKEN = "refresh_token";
    private final static String ERROR = "error";
    private final static String ERROR_DESCRIPTION = "error_description";

    private String json;

    private String accessToken = "";
    private int expiresIn;
    private String refreshToken = "";

    private String error;
    private String errorDescription;

    public static OauthData create(String jsonString) {
        if (Util.isEmpty(jsonString)) {
            return null;
        }

        JSONObject json;
        try {
            json = new JSONObject(jsonString);
        } catch (Exception e) {
            json = null;
        }

        if (json == null) {
            return null;
        }

        OauthData data = new OauthData();
        data.json = jsonString;
        data.accessToken = json.optString(ACCESS_TOKEN);
        data.expiresIn = json.optInt(EXPIRES_IN);
        data.refreshToken = json.optString(REFRESH_TOKEN);

        data.error = json.optString(ERROR);
        data.errorDescription = json.optString(ERROR_DESCRIPTION);

        return data;
    }

    public String getToken() {
        return this.accessToken;
    }

    public int getExpiresIn() {
        return this.expiresIn;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public String getError() {
        return this.error;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }

    @Override
    public String toString() {
        return this.json;
    }
}
