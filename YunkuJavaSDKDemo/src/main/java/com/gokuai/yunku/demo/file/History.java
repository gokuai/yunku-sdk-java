package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;

/**
 * Created by qp on 2017/5/17.
 * <p>
 * 获取文件历史
 */
public class History {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntFileManagerHelper.getInstance().history("test", 0, 100);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
