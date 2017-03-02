package com.gokuai.yunku.demo.helper;

import com.gokuai.yunku.demo.Config;
import com.yunkuent.sdk.EntFileManager;

/**
 * Created by qp on 2017/3/2.
 */
public class EntFileManagerHelper {

    private static volatile EntFileManager instance = null;

    public static EntFileManager getInstance() {
        if (instance == null) {
            synchronized (EntFileManager.class) {
                if (instance == null) {
                    instance = new EntFileManager(Config.ORG_CLIENT_ID, Config.ORG_CLIENT_SECRET);
                }
            }
        }
        return instance;
    }
}
