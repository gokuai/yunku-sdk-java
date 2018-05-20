package com.gokuai.base;

import java.util.HashMap;

/**
 * Created by Brandon on 2017/3/13.
 */
public interface IAuthRequest {

    ReturnResult sendRequestWithAuth(String url, RequestMethod method,
                               HashMap<String, String> params, HashMap<String, String> headParams, String postType);
}
