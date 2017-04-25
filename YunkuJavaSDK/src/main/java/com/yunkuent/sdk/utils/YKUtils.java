package com.yunkuent.sdk.utils;

import com.gokuai.base.utils.Util;
import com.yunkuent.sdk.data.FileInfo;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Created by Brandon on 2017/4/25.
 */
public class YKUtils extends Util {

    /**
     * 获取文件的filehash
     *
     * @param in
     * @param forReadAgain
     * @return
     */
    public static FileInfo getFileSha1(InputStream in, boolean forReadAgain) {
        String fileHash = "";
        long fileSize = 0;
        MessageDigest messagedigest;
        try {
            messagedigest = MessageDigest.getInstance("SHA-1");

            try {
                byte[] buffer = new byte[1024 * 1024 * 10];
                int len = 0;

                while ((len = in.read(buffer)) > 0) {
                    messagedigest.update(buffer, 0, len);
                    fileSize += len;
                }
                fileHash = toHexString(messagedigest.digest());
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (in != null) {
                    if (forReadAgain) {
                        in.close();
                    } else {
                        in.reset();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new FileInfo(fileSize, fileHash);
    }
}
