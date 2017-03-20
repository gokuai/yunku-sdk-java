package com.yunkuent.sdk;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Brandon on 2017/3/13.
 */
public interface IAuthRequest {

    String sendRequestWithAuth(String url, RequestMethod method,
                               HashMap<String, String> params, HashMap<String, String> headParams, ArrayList<String> ignoreKeys);
}
