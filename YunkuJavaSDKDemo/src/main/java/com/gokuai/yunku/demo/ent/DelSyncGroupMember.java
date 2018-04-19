package com.gokuai.yunku.demo.ent;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 删除同步分组的成员
 */
public class DelSyncGroupMember {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntManagerHelper.getInstance().delSyncGroupMember("ParentGroup", new String[] { "MemberTest2", "MemberTest3" });

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
