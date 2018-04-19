package com.gokuai.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Brandon on 2017/3/13.
 */
public interface IAuthRequest {

    ReturnResult sendRequestWithAuth(String url, RequestMethod method,
                               HashMap<String, String> params, HashMap<String, String> headParams, ArrayList<String> ignoreKeys,String postType);
}
