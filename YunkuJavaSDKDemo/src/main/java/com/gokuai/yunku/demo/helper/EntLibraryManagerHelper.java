package com.gokuai.yunku.demo.helper;

import com.gokuai.yunku.demo.Config;
import com.yunkuent.sdk.ConfigHelper;
import com.yunkuent.sdk.EntLibManager;

/**
 * Created by qp on 2017/3/2.
 */
public class EntLibraryManagerHelper {

    private static volatile EntLibManager instance = null;

    public static EntLibManager getInstance() {
        if (instance == null) {
            synchronized (EntLibManager.class) {
                if (instance == null) {
                    instance = new EntLibManager(Config.CLIENT_ID, Config.CLIENT_SECRET);
                }
            }
        }
        return instance;
    }
}
