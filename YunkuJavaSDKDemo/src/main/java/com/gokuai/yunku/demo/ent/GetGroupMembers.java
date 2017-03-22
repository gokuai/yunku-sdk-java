package com.gokuai.yunku.demo.ent;

import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/2.
 *
 * 分组成员列表
 */
public class GetGroupMembers {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntManagerHelper.getInstance().getGroupMembers(71715, 0, 3, true);

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
