package com.gokuai.base.data;

/**
 * Created by Brandon on 2017/2/27.
 */
public class FileInfo {
    public long fileSize;
    public String fileHash;

    public FileInfo(long fileSize, String fileHash) {
        this.fileSize = fileSize;
        this.fileHash = fileHash;
    }
}
