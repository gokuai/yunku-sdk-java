package com.gokuai.yunku.demo.library;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntLibraryManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 添加库成员
 */
public class AddMembers {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntLibraryManagerHelper.getInstance().addMembers(1258748,3330,new int[]{52064});

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
