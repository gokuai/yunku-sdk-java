package com.gokuai.yunku.demo.library;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntLibraryManagerHelper;
import com.yunkuent.sdk.EntLibManager;

/**
 * Created by qp on 2017/3/2.
 *
 * 获取库信息
 */
public class Log {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntLibraryManagerHelper.getInstance().getLogByOrgId(1451560, null, EntLibManager.OrderBy.ASC, null, null, 0, 100);
        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
