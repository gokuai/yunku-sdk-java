package com.gokuai.yunku.demo.library;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntLibraryManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 批量修改单库中成员角色
 */
public class SetMemberRole {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntLibraryManagerHelper.getInstance().setMemberRole(1258748,3208,new int[]{216144});

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
