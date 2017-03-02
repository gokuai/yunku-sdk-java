package com.yunkuent.sdk.data;

import org.json.JSONObject;

/**
 * 返回结果和返回状态号
 */
public class ReturnResult {
    int statusCode;
    String result;

    public ReturnResult(){

    }

    public ReturnResult(String result, int code) {
        statusCode = code;
        this.result = result;
    }

    /**
     * 返回请求内容
     *
     * @return
     */
    public String getResult() {
        return result;
    }

    /**
     * 返回请求号
     *
     * @return
     */
    public int getStatusCode() {
        return statusCode;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    public static ReturnResult create(String jsonString) {
        JSONObject json = null;
        String result = "";
        int statusCode = 0;
        try {
            json = new JSONObject(jsonString);
            result = json.getString("result");
            statusCode = json.getInt("statusCode");
        } catch (Exception e) {
            e.printStackTrace();
            json = null;
        }
        if (json == null) {
            return new ReturnResult("result json is null", 0);
        }
        return new ReturnResult(result, statusCode);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("result:" + result + "\n");
        sb.append("statusCode:" + statusCode + "\n");
        return sb.toString();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
