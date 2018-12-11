package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.ConfigHelper;
import com.yunkuent.sdk.EntFileManager;

/**
 * Created by qp on 2017/3/2.
 *
 * 通过文件路径获取下载地址
 */
public class GetDownloadUrlByFullpath {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntFileManagerHelper.getInstance().getDownloadUrlByFullpath("test.docx", "tom");

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
