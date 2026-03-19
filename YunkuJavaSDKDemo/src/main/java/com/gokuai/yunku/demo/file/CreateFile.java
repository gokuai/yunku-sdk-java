package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;

public class CreateFile {
    public static void main(String[] args) {
        DebugConfig.DEBUG = true;
        ReturnResult result = EntFileManagerHelper.getInstance().createFile("test/testIgnoreFilehash2.txt", "21af9a0386a60f130331aac363b98f42d175cbf9", 6608, true, "", 0);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}