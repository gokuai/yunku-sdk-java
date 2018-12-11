package com.gokuai.yunku.demo.library;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntLibraryManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 修改库信息
 * 1T="1099511627776" 1G＝“1073741824”；
 */
public class Set {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntLibraryManagerHelper.getInstance().set(1258748, "ttt", "1073741824", "");

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
