package com.gokuai.yunku.demo.library;

import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntLibraryManagerHelper;
import com.yunkuent.sdk.DebugConfig;
import com.yunkuent.sdk.MemberType;

/**
 * Created by qp on 2017/3/2.
 *
 * 查询库成员信息
 */
public class GetMember {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="D://LogPath";//默认在D盘根目录

        String returnString = EntLibraryManagerHelper.getInstance().getMember(1258748, MemberType.ACCOUNT,new String[]{"BrandonTest"});

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
