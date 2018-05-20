package com.gokuai.yunku.demo.ent;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 添加或修改同步成员
 */
public class AddSyncMember {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntManagerHelper.getInstance().addSyncMember("MemberTest1", "Member1", "Member1", "1234", "111", "111", null);

        ReturnResult result1 = EntManagerHelper.getInstance().addSyncMember("MemberTest2", "Member2", "Member2", "", "", "", null);

        ReturnResult result2 = EntManagerHelper.getInstance().addSyncMember("MemberTest3", "Member3", "Member3", "", "", "", null);

        DeserializeHelper.getInstance().deserializeReturn(result);

        DeserializeHelper.getInstance().deserializeReturn(result1);

        DeserializeHelper.getInstance().deserializeReturn(result2);
    }
}
