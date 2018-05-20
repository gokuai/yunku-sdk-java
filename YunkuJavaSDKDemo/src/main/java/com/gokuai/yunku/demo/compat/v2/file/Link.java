package com.gokuai.yunku.demo.compat.v2.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.compat.v2.helper.EntFileManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.yunkuent.sdk.compat.v2.EntFileManager;

/**
 * Created by qp on 2017/3/2.
 *
 * 文件连接
 */
public class Link {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntFileManagerHelper.getInstance().link("qq.jpg", 0, EntFileManager.AuthType.DEFAULT, null);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
