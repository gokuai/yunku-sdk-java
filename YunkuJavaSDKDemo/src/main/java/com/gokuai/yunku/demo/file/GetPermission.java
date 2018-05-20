package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;

/**
 * Created by qp on 2017/5/17.
 * <p>
 * 获取文件夹权限
 */
public class GetPermission {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntFileManagerHelper.getInstance().getPermission("test", 4);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}

