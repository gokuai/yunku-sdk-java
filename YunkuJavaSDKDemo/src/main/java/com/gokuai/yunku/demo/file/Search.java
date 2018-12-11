package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.ScopeType;

/**
 * Created by qp on 2017/3/2.
 * <p>
 * 文件搜索
 */
public class Search {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        ReturnResult result = EntFileManagerHelper.getInstance().search("search", "", 0, 100, ScopeType.FILENAME, ScopeType.CONTENT, ScopeType.TAG);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
