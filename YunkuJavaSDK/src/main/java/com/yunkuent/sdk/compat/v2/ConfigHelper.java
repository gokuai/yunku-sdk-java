package com.yunkuent.sdk.compat.v2;

import com.gokuai.base.utils.Util;

import java.util.UUID;

public class ConfigHelper extends com.yunkuent.sdk.ConfigHelper {

    private String mOauthHostV2;
    private String mApiHostV2;

    private String mUploadRootPath;
    private String mUploadOpname;
    private String mUploadTags;


    @Override
    public ConfigHelper apiHost(String apiHost) {
        this.mApiHostV2 = apiHost;
        return this;

    }

    @Override
    public ConfigHelper oauthHost(String oauthHost) {
        this.mOauthHostV2 = oauthHost;
        return this;
    }

    @Override
    public ConfigHelper uploadRootPath(String uploadRootPath) {
        this.mUploadRootPath = uploadRootPath;
        return this;
    }

    @Override
    public ConfigHelper uploadOpname(String uploadOpname) {
        this.mUploadOpname = uploadOpname;
        return this;
    }

    @Override
    public ConfigHelper uploadTags(String tags) {
        this.mUploadTags = tags;
        return this;
    }

    @Override
    public void config() {
        super.config();

        if (!Util.isEmpty(mApiHostV2)) {
            HostConfig.API_ENT_HOST_V2 = mApiHostV2;
        }

        if (!Util.isEmpty(mOauthHostV2)) {
            HostConfig.OAUTH_HOST_V2 = mOauthHostV2;
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

    }


}
