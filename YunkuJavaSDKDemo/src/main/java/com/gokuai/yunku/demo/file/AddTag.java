package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;

/**
 * Created by qp on 2017/5/17.
 * <p>
 * 添加标签
 */
public class AddTag {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        String returnString = EntFileManagerHelper.getInstance().addTag("default/custom/upload/path/search.txt", new String[]{"test", "test1"});

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
