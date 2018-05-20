package com.gokuai.yunku.demo.library;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntLibraryManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 从库中删除成员
 */
public class DelMember {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntLibraryManagerHelper.getInstance().delMember(1258748,new int[]{52064});

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
