package com.gokuai.yunku.demo.compat.v2.lib;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.compat.v2.helper.EntLibraryManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 从库中删除成员
 */
public class DelMember {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntLibraryManagerHelper.getInstance().delMember(1262679,new int[]{885371});

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
