package com.gokuai.yunku.demo.compat.v2.lib;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.compat.v2.helper.EntLibraryManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 获取某一个企业分组列表
 */
public class GetGroups {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntLibraryManagerHelper.getInstance().getGroups(1262679);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
