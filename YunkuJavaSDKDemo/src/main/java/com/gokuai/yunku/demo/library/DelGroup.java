package com.gokuai.yunku.demo.library;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntLibraryManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 库上删除分组
 */
public class DelGroup {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntLibraryManagerHelper.getInstance().delGroup(1258748,71715);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
