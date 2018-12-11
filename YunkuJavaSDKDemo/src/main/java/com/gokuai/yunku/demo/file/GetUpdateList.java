package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 获取更新列表
 */

public class GetUpdateList {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

            ReturnResult result = EntFileManagerHelper.getInstance().getUpdateList(false, 0, null);

            DeserializeHelper.getInstance().deserializeReturn(result);
        }
    }
