package com.gokuai.yunku.demo.file;

import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.DebugConfig;
import com.yunkuent.sdk.ScopeType;

/**
 * Created by qp on 2017/3/2.
 *
 * 文件搜索
 */
public class Search {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="D://LogPath";//默认在D盘根目录

        String returnString = EntFileManagerHelper.getInstance().search("tes", "", 0, 100, ScopeType.FILENAME, ScopeType.TAG, ScopeType.CONTENT);

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
