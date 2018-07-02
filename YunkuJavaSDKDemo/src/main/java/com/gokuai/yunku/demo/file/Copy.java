package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;

/**
 * Created by qp on 2017/5/18.
 * <p>
 * 复制文件
 */
public class Copy {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntFileManagerHelper.getInstance().copy("qq.jpg", "test/qq.jpg", "tom");

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
