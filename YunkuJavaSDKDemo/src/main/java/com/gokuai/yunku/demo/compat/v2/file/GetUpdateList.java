package com.gokuai.yunku.demo.compat.v2.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.compat.v2.helper.EntFileManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * 获取更新列表
 */

public class GetUpdateList {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

            ReturnResult result = EntFileManagerHelper.getInstance().getUpdateList(false, 0);

            DeserializeHelper.getInstance().deserializeReturn(result);
        }
    }
