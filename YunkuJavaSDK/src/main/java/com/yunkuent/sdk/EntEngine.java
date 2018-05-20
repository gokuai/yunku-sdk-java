package com.yunkuent.sdk;

import com.gokuai.base.HttpEngine;
import com.gokuai.base.utils.Util;

import java.util.HashMap;

public class EntEngine extends HttpEngine {

    protected static String CLIENT_ID_KEY = "client_id";

    public EntEngine(String clientId, String secret) {
        super(clientId, secret);
    }

    public class RequestHelper extends HttpEngine.RequestHelper {

        @Override
        protected void setCommonParams() {
            if (this.params == null) {
                this.params = new HashMap<String, String>();
            }
            this.params.put(EntEngine.CLIENT_ID_KEY, EntEngine.this.mClientId);
            this.params.put("dateline", Long.toString(Util.getUnixDateline()));
            super.setCommonParams();
        }
    }
}
