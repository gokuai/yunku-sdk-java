package com.gokuai.base;

import com.google.gson.Gson;

import java.io.IOException;

/**
 * 返回结果和返回状态号
 */
public class ReturnResult {
    private int code;
    private String body;
    private Exception exception;

    public ReturnResult() {
        this.code = 0;
        this.body = "";
    }

    public ReturnResult(Exception e) {
        this.exception = e;
        this.code = 0;
        this.body = "";
    }

    public ReturnResult(int code, String body) {
        this.code = code;
        this.body = body;
    }

    /**
     * 接口调用是否成功
     * @return
     */
    public boolean isOK() {
        return exception == null && (code == 200 || code == 206);
    }

    /**
     * 返回请求内容
     *
     * @return
     */
    public String getBody() {
        return body;
    }

    /**
     * 返回请求状态
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    public Exception getException() {
        return this.exception;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
