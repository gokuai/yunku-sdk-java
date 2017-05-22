package com.gokuai.yunku.demo.compat.v2.lib;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.compat.v2.helper.EntLibraryManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.yunkuent.sdk.MemberType;

/**
 * Created by qp on 2017/3/2.
 *
 * 查询库成员信息
 */
public class GetMember {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntLibraryManagerHelper.getInstance().getMember(1262679, MemberType.MEMBER_ID,new String[]{"885371"});

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
