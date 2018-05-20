package com.gokuai.yunku.demo.ent;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;

/**
 * Created by qp on 2017/3/20.
 *
 * 删除成员的所属部门
 */
public class DelSyncMemberGroup {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntManagerHelper.getInstance().delSyncMemberGroup(new String[]{"MemberTest2"});

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
