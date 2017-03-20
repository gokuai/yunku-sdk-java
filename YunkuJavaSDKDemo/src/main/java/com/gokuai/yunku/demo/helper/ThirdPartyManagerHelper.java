package com.gokuai.yunku.demo.helper;

import com.gokuai.yunku.demo.Config;
import com.yunkuent.sdk.ThirdPartyManager;

/**
 * Created by qp on 2017/3/16.
 */
public class ThirdPartyManagerHelper {

    private static volatile ThirdPartyManager instance = null;


    public static ThirdPartyManager getInstance() {
        if (instance == null) {
            synchronized (ThirdPartyManager.class) {
                if (instance == null) {
                    instance = new ThirdPartyManager(Config.CLIENT_ID, Config.CLIENT_SECRET, Config.OUT_ID);
                }
            }
        }
        return instance;
    }
}
