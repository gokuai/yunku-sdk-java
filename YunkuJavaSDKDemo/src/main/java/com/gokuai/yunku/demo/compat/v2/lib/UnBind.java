package com.gokuai.yunku.demo.compat.v2.lib;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.compat.v2.helper.EntLibraryManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 取消库授权
 */
public class UnBind {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntLibraryManagerHelper.getInstance().unBind("AxT0EUEjtYc8za41xXl1dKFJ40");

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
