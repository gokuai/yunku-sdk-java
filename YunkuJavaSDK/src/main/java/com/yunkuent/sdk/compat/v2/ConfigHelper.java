package com.yunkuent.sdk.compat.v2;

import com.gokuai.base.NetConnection;
import com.gokuai.base.utils.Util;
import com.yunkuent.sdk.UploadManager;

import java.net.Proxy;


public class ConfigHelper {

    private String mOauthHostV2;
    private String mApiHostV2;

    private String mUploadRootPath;
    private String mUploadOpName;

    private Proxy mProxy;
    private String mUserAgent;
    private String mLanguage;
    private long mConnectTimeout;
    private long mTimeout;
    private int mBlockSize;
    private int mRetry;


    public ConfigHelper apiHost(String apiHost) {
        this.mApiHostV2 = apiHost;
        return this;

    }

    public ConfigHelper oauthHost(String oauthHost) {
        this.mOauthHostV2 = oauthHost;
        return this;
    }

    public ConfigHelper uploadRootPath(String uploadRootPath) {
        this.mUploadRootPath = uploadRootPath;
        return this;
    }

    public ConfigHelper uploadOpName(String opName) {
        this.mUploadOpName = opName;
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
     * @param timeoutSeconds
     * @return
     */
    public ConfigHelper connectTimeout(long timeoutSeconds) {
        this.mConnectTimeout = timeoutSeconds;
        return this;
    }

    /**
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

        if (!Util.isEmpty(mApiHostV2)) {
            HostConfig.API_ENT_HOST_V2 = mApiHostV2;
        }

        if (!Util.isEmpty(mOauthHostV2)) {
            HostConfig.API_HOST_V2 = mOauthHostV2;
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

        if (this.mRetry > 0) {
            NetConnection.setRetry(mRetry);
            UploadManager.setRetry(mRetry);
        }
    }


}
