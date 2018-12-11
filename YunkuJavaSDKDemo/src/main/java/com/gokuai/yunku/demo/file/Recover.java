package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;

/**
 * Created by qp on 2017/5/18.
 */
public class Recover {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntFileManagerHelper.getInstance().recover(new String[]{"qq.jpg"}, "tom");

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
