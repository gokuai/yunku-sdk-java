package com.gokuai.yunku.demo.compat.v2.ent;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.compat.v2.helper.EntManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 根据外部系统唯一id查询企业成员信息
 */
public class GetMemberByOutId {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntManagerHelper.getInstance().getMemberByOutId("ac1d8e1f-6d67-4143-8494-4c864c5f3d31");

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
