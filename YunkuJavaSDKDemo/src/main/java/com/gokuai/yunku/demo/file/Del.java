package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 删除文件
 */
public class Del {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntFileManagerHelper.getInstance().del(new String[]{"test3", "test1"}, true, "tom");

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
