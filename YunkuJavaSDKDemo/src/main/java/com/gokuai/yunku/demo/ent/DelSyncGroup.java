package com.gokuai.yunku.demo.ent;

import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/2.
 *
 * 删除同步分组
 */
public class DelSyncGroup {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="D://LogPath";//默认在D盘根目录

        String returnString = EntManagerHelper.getInstance().delSyncGroup(new String[] { "ParentGroup", "GroupTest" });

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
