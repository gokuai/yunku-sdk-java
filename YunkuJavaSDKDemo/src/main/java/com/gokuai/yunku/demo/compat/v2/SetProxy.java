package com.gokuai.yunku.demo.compat.v2;

import com.gokuai.base.DebugConfig;
import com.gokuai.yunku.demo.compat.v2.helper.EntManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.yunkuent.sdk.ConfigHelper;
import com.yunkuent.sdk.compat.v2.HostConfig;

import java.net.InetSocketAddress;
import java.net.Proxy;


/**
 * Created by qp on 2017/4/13.
 */
public class SetProxy {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("",80));
        new ConfigHelper().proxy(proxy).config();

        String returnString = EntManagerHelper.getInstance().getMembers(0, 99);

        DeserializeHelper.getInstance().deserializeReturn(returnString);

    }
}
