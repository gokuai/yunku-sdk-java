package com.gokuai.base.utils;

import java.io.UnsupportedEncodingException;

public class URLEncoder {
    private URLEncoder() {
    }

    public static String encodeUTF8(final String s) {
        return encode(s, "UTF-8");
    }

    public static String encode(final String s, final String encoding) {
        try {
            return java.net.URLEncoder.encode(s, encoding)
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

}