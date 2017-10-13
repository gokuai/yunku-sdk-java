package com.gokuai.yunku.demo;


import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;
import com.yunkuent.sdk.ConfigHelper;
import com.yunkuent.sdk.HostConfig;

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

        String returnString = EntManagerHelper.getInstance().getMembers(0, 99);

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
