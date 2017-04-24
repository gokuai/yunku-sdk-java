package com.gokuai.yunku.demo.ent;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 根据外部系统唯一id查询企业成员信息
 */
public class GetMemberByOutId {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntManagerHelper.getInstance().getMemberByOutId("$:LWCP_v1:$ypc3i0Op0Tn0Ge2GvyShWA==");

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
