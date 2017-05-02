package com.gokuai.base;

import com.gokuai.base.utils.Util;

import java.util.*;

/**
 * Created by Brandon on 14/12/16.
 */
abstract class SignAbility {


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
        int size = params.size();
        String string_to_sign = "";

        if (size > 0) {
            for (int i = 0; i < size - 1; i++) {
                String key = keys.get(i);
                if (ignoreKeys != null && ignoreKeys.contains(key)) {
                    continue;
                }

                String value = params.get(key);
                if (value == null) {
                    continue;
                }

                string_to_sign += value + "\n";
            }
            string_to_sign += params.get(keys.get(size - 1));
        }
        return Util.getHmacSha1(string_to_sign, secret);
    }


    private Comparator<String> mComparator = new Comparator<String>() {
        public int compare(String p1, String p2) {
            return p1.compareTo(p2);
        }
    };
}