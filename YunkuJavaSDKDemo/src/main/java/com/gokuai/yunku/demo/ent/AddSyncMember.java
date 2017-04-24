package com.gokuai.yunku.demo.ent;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 添加或修改同步成员
 */
public class AddSyncMember {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntManagerHelper.getInstance().addSyncMember("MemberTest1", "Member1", "Member1", "1234", "111", "111");

        String returnString1 = EntManagerHelper.getInstance().addSyncMember("MemberTest2", "Member2", "Member2", "", "", "");

        String returnString2 = EntManagerHelper.getInstance().addSyncMember("MemberTest3", "Member3", "Member3", "", "", "");

        DeserializeHelper.getInstance().deserializeReturn(returnString);

        DeserializeHelper.getInstance().deserializeReturn(returnString1);

        DeserializeHelper.getInstance().deserializeReturn(returnString2);
    }
}
