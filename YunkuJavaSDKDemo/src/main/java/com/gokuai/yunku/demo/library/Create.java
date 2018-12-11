package com.gokuai.yunku.demo.library;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntLibraryManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 创建云库
 * 1T="1099511627776" 1G＝“1073741824”；
 */
public class Create {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntLibraryManagerHelper.getInstance().create("test", "1073741824", "destroy", "test lib");

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
