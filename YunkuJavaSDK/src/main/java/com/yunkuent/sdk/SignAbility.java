package com.yunkuent.sdk;

import com.yunkuent.sdk.utils.Util;

import java.util.*;

/**
 * Created by Brandon on 14/12/16.
 */
abstract class SignAbility implements HostConfig {


    /**
     * 根据clientsecret 签名
     *
     * @param params
     * @return
     */
    public String generateSign(HashMap<String, String> params, String secret) {
        return generateSign(params, secret, new ArrayList<String>());
    }

    /**
     * 根据clientsecret 签名 ,排除不需要签名的value
     *
     * @param params
     * @param secret
     * @param ignoreKeys
     * @return
     */
    protected String generateSign(HashMap<String, String> params, String secret, ArrayList<String> ignoreKeys) {
        ArrayList<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys, mComparator);
        String string_sign = "";

        //移除对应为null的参数
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue() == null||
                    (ignoreKeys != null
                            && ignoreKeys.contains(pair.getKey().toString()))) {
                keys.remove(pair.getKey().toString());
                it.remove();
            }
        }

        int size = params.size();
        if (size > 0) {
            for (int i = 0; i < size - 1; i++) {
                String key = keys.get(i);
                String value = params.get(key);
                string_sign += value + "\n";
            }
            string_sign += params.get(keys.get(size - 1));
        }
        return Util.getHmacSha1(string_sign, secret);
    }


    private Comparator<String> mComparator = new Comparator<String>() {
        public int compare(String p1, String p2) {
            return p1.compareTo(p2);
        }
    };
}