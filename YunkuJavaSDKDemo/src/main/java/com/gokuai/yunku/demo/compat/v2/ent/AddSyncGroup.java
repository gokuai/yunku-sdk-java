package com.gokuai.yunku.demo.compat.v2.ent;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.compat.v2.helper.EntManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 添加同步分组
 */
public class AddSyncGroup {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";


        ReturnResult result = EntManagerHelper.getInstance().addSyncGroup("ParentGroup", "ParentGroup", "");
        ReturnResult result1 = EntManagerHelper.getInstance().addSyncGroup("GroupTest", "Group", "ParentGroup");

        DeserializeHelper.getInstance().deserializeReturn(result);
        DeserializeHelper.getInstance().deserializeReturn(result1);
    }
}
