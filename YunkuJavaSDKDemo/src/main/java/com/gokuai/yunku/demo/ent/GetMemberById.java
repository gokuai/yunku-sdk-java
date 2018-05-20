package com.gokuai.yunku.demo.ent;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 根据成员Id查询企业成员信息
 */
public class GetMemberById {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntManagerHelper.getInstance().getMemberById(74478, false);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
