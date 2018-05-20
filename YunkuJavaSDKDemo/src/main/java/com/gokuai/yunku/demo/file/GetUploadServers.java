package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;

/**
 * Created by qp on 2017/3/2.
 *
 * WEB直接上传文件 (支持50MB以上文件的上传)
 */
public class GetUploadServers {

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntFileManagerHelper.getInstance().getUploadServers();

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
