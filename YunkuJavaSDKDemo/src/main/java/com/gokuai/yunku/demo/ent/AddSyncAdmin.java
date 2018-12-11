package com.gokuai.yunku.demo.ent;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;

/**
 * Created by qp on 2017/5/17.
 * <p>
 * 添加管理员
 */
public class AddSyncAdmin {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntManagerHelper.getInstance().addSyncAdmin("$:LWCP_v1:$ypc3i0Op0Tn0Ge2GvyShWA==", "", false);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}