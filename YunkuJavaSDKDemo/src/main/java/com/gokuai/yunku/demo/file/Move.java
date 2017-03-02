package com.gokuai.yunku.demo.file;

import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.DebugConfig;

/**
 * Created by qp on 2017/3/2.
 *
 * 移动文件
 */
public class Move {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="D://LogPath";//默认在D盘根目录

        String returnString = EntFileManagerHelper.getInstance().move("test/test.txt","test.txt","Brandon");

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}