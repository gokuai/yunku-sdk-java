package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;

/**
 * 关闭文件外链
 */
public class LinkClose {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntFileManagerHelper.getInstance().linkCloseByFullpath("qq.jpg", "tom", 0);

//        ReturnResult result = EntFileManagerHelper.getInstance().linkCloseByCode("abcdefgh", "tom", 0);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
