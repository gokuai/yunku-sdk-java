package com.yunkuent.sdk.compat.v2;

import com.yunkuent.sdk.HttpEngine;

/**
 * Created by Brandon on 2017/3/20.
 */
public class OauthEngine extends HttpEngine {
    public OauthEngine(String clientId, String clientSecret) {
        super(clientId, clientSecret);
    }
}
