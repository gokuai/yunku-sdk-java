package com.gokuai.yunku.demo.ent;

import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/20.
 *
 * 删除成员的所属部门
 */
public class DelSyncMemberGroup {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntManagerHelper.getInstance().delSyncMemberGroup(new String[]{"MemberTest2"});

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
