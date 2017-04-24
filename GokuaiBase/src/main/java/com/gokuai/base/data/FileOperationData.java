package com.gokuai.base.data;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

/**
 * 新建/重命名/删除 文件/文件夹 的返回数据
 */
public class FileOperationData {
    public final static int STATE_NOUPLOAD = 1;

    private final static String KEY_ERRORCODE = "error_code";
    private final static String KEY_ERRORMSG = "error_msg";
    private final static String KEY_STATE = "state";
    private final static String KEY_HASH = "hash";
    private final static String KEY_VERSION = "version";
    private final static String KEY_SERVER = "server";

    private int code;
    private int error_code;
    private String error_msg;
    private int state;
    private String hash;
    private int version;
    private String server;
    private String filehash;

    public static FileOperationData create(String jsonString, int code) {
        JSONObject json = null;

        try {
            json = new JSONObject(jsonString);
        } catch (Exception e) {
            json = null;
        }

        if (json == null) {
            return null;
        }

        FileOperationData data = new FileOperationData();
        data.setCode(code);
        if (code == HttpStatus.SC_OK) {
            data.setState(json.optInt(KEY_STATE));
            data.setHash(json.optString(KEY_HASH));
            data.setVersion(json.optInt(KEY_VERSION));
            data.setServer(json.optString(KEY_SERVER));
        } else {
            data.setErrnoCode(json.optInt(KEY_ERRORCODE));
            data.setErrnoMsg(json.optString(KEY_ERRORMSG));
        }
        return data;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getErrnoCode() {
        return error_code;
    }

    public void setErrnoCode(int errno_code) {
        this.error_code = errno_code;
    }

    public String getErrnoMsg() {
        return error_msg;
    }

    public void setErrnoMsg(String errno_msg) {
        this.error_msg = errno_msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getFilehash() {
        return filehash;
    }

    public void setFilehash(String filehash) {
        this.filehash = filehash;
    }
}
