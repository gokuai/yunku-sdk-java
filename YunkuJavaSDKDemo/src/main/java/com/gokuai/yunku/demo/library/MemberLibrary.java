package com.gokuai.yunku.demo.library;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntLibraryManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 获取库信息
 */
public class MemberLibrary {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        //获取个人库信息
        ReturnResult result = EntLibraryManagerHelper.getInstance().getInfoByMembmer(0, null, "tom", null);
        DeserializeHelper.getInstance().deserializeReturn(result);

        //修改个人库空间
//        ReturnResult result = EntLibraryManagerHelper.getInstance().setByMembmer(0, null, "tom", null, 21474836480l);
//        DeserializeHelper.getInstance().deserializeReturn(result);

    }
}
