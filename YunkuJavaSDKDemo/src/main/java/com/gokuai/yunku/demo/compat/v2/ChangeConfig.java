package com.gokuai.yunku.demo.compat.v2;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.compat.v2.helper.EntManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.yunkuent.sdk.compat.v2.ConfigHelper;

/**
 * Created by qp on 2017/4/8.
 */
public class ChangeConfig {
    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        new ConfigHelper()
                .apiHost("http://apihost")
                .oauthHost("http://oauthHost")
                .language("Zh-CN")
                .userAgent("[Your User Agent]")
                .config();

        ReturnResult result = EntManagerHelper.getInstance().getMembers(0, 99);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
