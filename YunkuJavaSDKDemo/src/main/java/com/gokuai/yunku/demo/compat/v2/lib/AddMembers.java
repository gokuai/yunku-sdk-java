package com.gokuai.yunku.demo.compat.v2.lib;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.compat.v2.helper.EntLibraryManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 添加库成员
 */
public class AddMembers {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntLibraryManagerHelper.getInstance().addMembers(1262679,13862,new int[]{885371});

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
