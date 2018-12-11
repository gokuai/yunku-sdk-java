package com.gokuai.yunku.demo.library;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntLibraryManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 获取某一个企业分组列表
 */
public class GetGroups {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntLibraryManagerHelper.getInstance().getGroups(0);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
