package com.yunkuent.sdk;

import com.gokuai.base.NetConnection;
import com.gokuai.base.utils.Util;

import java.net.Proxy;

public class ConfigHelper {

    private String mOauthHost;
    private String mApiHost;
    private String mUploadRootPath;
    private String mUploadOpname;
    private String mUploadTags;
    private boolean mRandomGuidTag;
    private Proxy mProxy;
    private String mUserAgent;
    private String mLanguage;
    private long mReadTimeOut;
    private long mConnectTimeOut;

    /**
     * 设置 api host
     * @param apiHost
     * @return
     */
    public ConfigHelper apiHost(String apiHost) {
        this.mApiHost = apiHost;
        return this;

    }

    /**
     *
     * @param oauthHost
     * @return
     */
    public ConfigHelper oauthHost(String oauthHost) {
        this.mOauthHost = oauthHost;
        return this;
    }

    /**
     *
     * @param uploadRootPath
     * @return
     */
    public ConfigHelper uploadRootPath(String uploadRootPath) {
        this.mUploadRootPath = uploadRootPath;
        return this;
    }

    /**
     * 设置上传默认操作人
     * @param uploadOpname
     * @return
     */
    public ConfigHelper uploadOpname(String uploadOpname) {
        this.mUploadOpname = uploadOpname;
        return this;
    }

    /**
     * 设置上传默认 tag
     * @param tags
     * @return
     */
    public ConfigHelper uploadTags(String tags) {
        this.mUploadTags = tags;
        return this;
    }


    /**
     *
     * @param proxy
     * @return
     */
    public ConfigHelper proxy(Proxy proxy) {
        this.mProxy = proxy;
        return this;
    }

    /**
     *
     * @param userAgent
     * @return
     */
    public ConfigHelper userAgent(String userAgent) {
        this.mUserAgent = userAgent;
        return this;
    }


    /**
     *
     * @param language
     * @return
     */
    public ConfigHelper language(String language) {
        this.mLanguage = language;
        return this;
    }

    /**
     *
     * @param millionSeconds
     * @return
     */
    public ConfigHelper readTimeOut(long millionSeconds) {
        this.mReadTimeOut = millionSeconds;
        return this;
    }


    /**
     *
     * @param millionSeconds
     * @return
     */
    public ConfigHelper connectTimeOut(long millionSeconds) {
        this.mConnectTimeOut = millionSeconds;
        return this;
    }

    public ConfigHelper randomGuidTag(boolean randomGuidTag) {
        this.mRandomGuidTag = randomGuidTag;
        return this;
    }

    public void config() {
        if (!Util.isEmpty(mApiHost)) {
            HostConfig.API_ENT_HOST = mApiHost;
        }

        if (!Util.isEmpty(mOauthHost)) {
            HostConfig.OAUTH_HOST = mOauthHost;
        }

        if (mProxy != null) {
            NetConnection.setProxy(mProxy);
        }

        if (!Util.isEmpty(mUserAgent)) {
            NetConnection.setUserAgent(mUserAgent);
        }

        if (mConnectTimeOut > 0) {
            NetConnection.setConnectTimeOut(mConnectTimeOut);
        }

        if (mReadTimeOut > 0) {
            NetConnection.setTimeOut(mReadTimeOut);
        }

        if (!Util.isEmpty(mLanguage)) {
            NetConnection.setAcceptLanguage(mLanguage);
        }

        if (!Util.isEmpty(mUploadOpname)) {
            EntFileManager.DEFAULT_OPNAME = mUploadOpname;
        }

        if (!Util.isEmpty(mUploadRootPath)) {
            EntFileManager.UPLOAD_ROOT_PATH = mUploadRootPath + (mUploadRootPath.endsWith("/") ? "" : "/");
        }

        if (!Util.isEmpty(mUploadTags)) {
            EntFileManager.DEFAULT_UPLOAD_TAGS = mUploadTags;
        }

        EntFileManager.RANDOM_GUID_TAG = mRandomGuidTag;


    }



}
