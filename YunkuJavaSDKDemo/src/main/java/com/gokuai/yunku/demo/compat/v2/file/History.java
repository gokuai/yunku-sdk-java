package com.gokuai.yunku.demo.compat.v2.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.compat.v2.helper.EntFileManagerHelper;

/**
 * Created by qp on 2017/5/17.
 * <p>
 * 获取文件历史
 */
public class History {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntFileManagerHelper.getInstance().history("test", 0, 100);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
