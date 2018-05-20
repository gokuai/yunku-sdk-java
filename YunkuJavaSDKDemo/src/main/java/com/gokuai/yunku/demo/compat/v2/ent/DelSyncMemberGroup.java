package com.gokuai.yunku.demo.compat.v2.ent;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.compat.v2.helper.EntManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;

/**
 * Created by qp on 2017/3/21.
 */
public class DelSyncMemberGroup {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntManagerHelper.getInstance().delSyncMemberGroup(new String[]{"MemberTest2"});

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
