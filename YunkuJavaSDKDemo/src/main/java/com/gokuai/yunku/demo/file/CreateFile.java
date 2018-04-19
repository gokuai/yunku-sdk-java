package com.gokuai.yunku.demo.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.Config;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntFileManagerHelper;
import com.yunkuent.sdk.ConfigHelper;

/**
 * Created by qp on 2017/3/2.
 * <p>
 * 上传文件 文件不得超过50MB
 */
public class CreateFile {

    public static void main(String[] args) {

        //-------- 如果想改编上传基础配置，可以进行以几种配置------
        new ConfigHelper()
                .uploadOpname("[Default Name]")
                .uploadRootPath("default/custom/upload/path")
                .uploadTags("[tag1]|[tag2]").config();

        //---------------------------------------------------

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        ReturnResult result = EntFileManagerHelper.getInstance().createFile("aa.jpg", "Brandon", Config.TEST_FILE_PATH);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
