package com.gokuai.yunku.demo.compat.v2.ent;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.compat.v2.helper.EntManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 分组成员列表
 */
public class GetGroupMembers {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntManagerHelper.getInstance().getGroupMembers(154837, 0, 3, true);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
