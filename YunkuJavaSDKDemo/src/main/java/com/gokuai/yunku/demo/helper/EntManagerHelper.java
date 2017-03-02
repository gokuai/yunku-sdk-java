package com.gokuai.yunku.demo.helper;

import com.gokuai.yunku.demo.Config;
import com.yunkuent.sdk.EntManager;

/**
 * Created by qp on 2017/3/2.
 */
public class EntManagerHelper {

    private static volatile EntManager instance = null;


    public static EntManager getInstance() {
        if (instance == null) {
            synchronized (EntManager.class) {
                if (instance == null) {
                    instance = new EntManager(Config.CLIENT_ID, Config.CLIENT_SECRET);
                }
            }
        }
        return instance;
    }
}
