package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.EntFileManager;

/**
 * Created by qp on 2017/3/2.
 *
 * 文件连接
 */
public class Link {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntFileManagerHelper.getInstance().link("qq.jpg", 0, EntFileManager.AuthType.DEFAULT, null, "tom");

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
