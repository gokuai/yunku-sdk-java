package com.gokuai.yunku.demo;


import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.ConfigHelper;

import java.io.IOException;

/**
 * Created by qp on 2017/4/8.
 */
public class ChangeConfig {

    public static void main(String[] args) throws IOException {

        new ConfigHelper()
                .apiHost("http://yk3.gokuai.com/m-open")
                .language("zh-CN")
                .retry(3)
                .config();

        ReturnResult result = EntFileManagerHelper.getInstance().previewUrl("upload/1521779111710/1521779111710.xls", true, "tom");
        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
