package com.gokuai.base;

import com.gokuai.base.utils.Util;

import java.util.*;

/**
 * Created by Brandon on 14/12/16.
 */
public class SignAbility {
    static List<String> IGNORE_KEYS = Arrays.asList("filehash", "filesize", "sign");

    protected final String mClientId;
    protected final String mSecret;

    public SignAbility(String clientId, String secret) {
        this.mClientId = clientId;
        this.mSecret = secret;
    }

    public String getClientId() {
        return this.mClientId;
    }

    public String getSecret() {
        return this.mSecret;
    }

    protected String generateSign(HashMap<String, String> params) {
        //移除 null 参数
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue() == null) {
                it.remove();
            }
        }

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String strToSign = "";
        if (!params.isEmpty()) {
            for (String key:keys) {
                if (IGNORE_KEYS.contains(key)) {
                    continue;
                }
                String value = params.get(key);
                strToSign += "\n" + value;
            }
            strToSign = strToSign.substring(1);
        }
        return Util.getHmacSha1(strToSign, this.getSecret());
    }
}