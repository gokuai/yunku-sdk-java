package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
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

        ReturnResult result = EntFileManagerHelper.getInstance().getDownloadUrlByHash("a712811330970ce5c13673ba73708cb85175fcd3",false, EntFileManager.NetType.DEFAULT);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
