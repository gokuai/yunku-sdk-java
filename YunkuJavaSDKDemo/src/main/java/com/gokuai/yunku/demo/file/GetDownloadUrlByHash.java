package com.gokuai.yunku.demo.file;

import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.DebugConfig;
import com.yunkuent.sdk.EntFileManager;

/**
 * Created by qp on 2017/3/2.
 *
 * 通过文件唯一标识获取下载地址
 */
public class GetDownloadUrlByHash {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="D://LogPath";//默认在D盘根目录

        String returnString = EntFileManagerHelper.getInstance().getDownloadUrlByHash("fe515ef69e56d6b60ecf3ed779c542f1920c3136",false, EntFileManager.NetType.DEFAULT);

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
