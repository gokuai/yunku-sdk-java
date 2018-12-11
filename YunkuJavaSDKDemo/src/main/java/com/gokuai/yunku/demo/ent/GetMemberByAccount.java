package com.gokuai.yunku.demo.ent;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 根据外部系统登录帐号查询企业成员信息
 */
public class GetMemberByAccount {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntManagerHelper.getInstance().getMemberByAccount("6905656124312207", false);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
