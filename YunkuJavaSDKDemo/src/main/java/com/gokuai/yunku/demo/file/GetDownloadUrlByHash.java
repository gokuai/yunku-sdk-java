package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.EntFileManager;

/**
 * Created by qp on 2017/3/2.
 *
 * 通过文件唯一标识获取下载地址
 */
public class GetDownloadUrlByHash {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntFileManagerHelper.getInstance().getDownloadUrlByHash("5ef2b3b8449cf3440b8a3b1874da5e4236b06dd8",false, EntFileManager.NetType.DEFAULT);

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
