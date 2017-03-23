package com.gokuai.yunku.demo.compat.v2.ent;

import com.gokuai.yunku.demo.compat.v2.helper.EntManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/2.
 *
 * 获取分组
 */
public class GetGroups {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntManagerHelper.getInstance().getGroups();

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
