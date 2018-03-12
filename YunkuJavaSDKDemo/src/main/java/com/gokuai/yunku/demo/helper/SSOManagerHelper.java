package com.gokuai.yunku.demo.helper;

import com.gokuai.yunku.demo.Config;
import com.yunkuent.sdk.ConfigHelper;
import com.yunkuent.sdk.EntFileManager;
import com.yunkuent.sdk.SSOManager;

/**
 * Created by wink on 2018/3/12.
 */
public class SSOManagerHelper {

    private static volatile SSOManager instance = null;

    static {
        new ConfigHelper()
                .oauthHost("http://yk3.goukuai.cn")
                .language("Zh-CN")
                .config();
    }

    public static SSOManager getInstance() {
        if (instance == null) {
            synchronized (EntFileManager.class) {
                if (instance == null) {
                    instance = new SSOManager(Config.ORG_CLIENT_ID, Config.ORG_CLIENT_SECRET);
                }
            }
        }
        return instance;
    }
}
