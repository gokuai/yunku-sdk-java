package com.yunkuent.sdk;

import com.gokuai.base.NetConnection;
import com.gokuai.base.utils.Util;

import java.net.Proxy;

public class ConfigHelper {

    private String mOauthHost;
    private String mApiHost;
    private String mUploadRootPath;
    private String mUploadOpName;
    private Proxy mProxy;
    private String mUserAgent;
    private String mLanguage;
    private long mConnectTimeout;
    private long mTimeout;
    private int mBlockSize;
    private int mRetry;

    /**
     * 设置 api host
     *
     * @param apiHost
     * @return
     */
    public ConfigHelper apiHost(String apiHost) {
        this.mApiHost = apiHost;
        return this;

    }

    /**
     * @param oauthHost
     * @return
     */
    public ConfigHelper oauthHost(String oauthHost) {
        this.mOauthHost = oauthHost;
        return this;
    }

    /**
     * @param uploadRootPath
     * @return
     */
    public ConfigHelper uploadRootPath(String uploadRootPath) {
        this.mUploadRootPath = uploadRootPath;
        return this;
    }

    /**
     * 设置上传默认操作人
     *
     * @param uploadOpName
     * @return
     */
    public ConfigHelper uploadOpName(String uploadOpName) {
        this.mUploadOpName = uploadOpName;
        return this;
    }

    /**
     * @param proxy
     * @return
     */
    public ConfigHelper proxy(Proxy proxy) {
        this.mProxy = proxy;
        return this;
    }

    /**
     * @param userAgent
     * @return
     */
    public ConfigHelper userAgent(String userAgent) {
        this.mUserAgent = userAgent;
        return this;
    }

    /**
     * @param language
     * @return
     */
    public ConfigHelper language(String language) {
        this.mLanguage = language;
        return this;
    }

    /**
     * 分块上传单块大小, 单位字节
     *
     * @return
     */
    public ConfigHelper blockSize(int blockSize) {
        this.mBlockSize = blockSize;
        return this;
    }

    /**
     * 网络连接超时
     *
     * @param timeoutSeconds
     * @return
     */
    public ConfigHelper connectTimeout(long timeoutSeconds) {
        this.mConnectTimeout = timeoutSeconds;
        return this;
    }

    /**
     * 网络执行超时
     *
     * @param timeoutSeconds
     * @return
     */
    public ConfigHelper timeout(long timeoutSeconds) {
        this.mTimeout = timeoutSeconds;
        return this;
    }

    /**
     * 接口访问失败后重试几次
     *
     * @param retry
     * @return
     */
    public ConfigHelper retry(int retry) {
        if (retry > 0) {
            this.mRetry = retry;
        }
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

        if (mConnectTimeout > 0) {
            NetConnection.setConnectTimeout(mConnectTimeout);
        }

        if (mTimeout > 0) {
            NetConnection.setTimeout(mTimeout);
        }

        if (!Util.isEmpty(mLanguage)) {
            NetConnection.setAcceptLanguage(mLanguage);
        }

        if (!Util.isEmpty(mUploadOpName)) {
            EntFileManager.DEFAULT_OPNAME = mUploadOpName;
        }

        if (!Util.isEmpty(mUploadRootPath)) {
            EntFileManager.UPLOAD_ROOT_PATH = mUploadRootPath + (mUploadRootPath.endsWith("/") ? "" : "/");
        }

        if (this.mBlockSize > 0) {
            EntFileManager.DEFAULT_BLOCKSIZE = this.mBlockSize;
        }

        if (this.mRetry > 0) {
            NetConnection.setRetry(mRetry);
            UploadManager.setRetry(mRetry);
        }
    }
}
