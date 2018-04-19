package com.gokuai.yunku.demo.ent;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;

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
