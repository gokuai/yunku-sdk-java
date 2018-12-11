package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;

/**
 * Created by qp on 2017/5/17.
 * <p>
 * 添加标签
 */
public class AddTag {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntFileManagerHelper.getInstance().addTag("test.txt", new String[]{"tag1", "tag2"}, "tom");

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
