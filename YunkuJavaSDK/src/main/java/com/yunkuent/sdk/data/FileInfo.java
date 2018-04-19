package com.yunkuent.sdk.data;

/**
 * Created by Brandon on 2017/2/27.
 */
public class FileInfo {
    public long fileSize;
    public String fileHash;
    public String fullpath;
    public String filename;
    public String hash;
    public String uploadServer;

    public FileInfo() {

    }

    public FileInfo(long fileSize, String fileHash) {
        this.fileSize = fileSize;
        this.fileHash = fileHash;
    }
}
