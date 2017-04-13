package com.gokuai.yunku.demo.compat.v2;

import com.gokuai.yunku.demo.compat.v2.helper.EntManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.yunkuent.sdk.DebugConfig;
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
        HostConfig.setProxy(proxy);

        String returnString = EntManagerHelper.getInstance().getMembers(0, 99);

        DeserializeHelper.getInstance().deserializeReturn(returnString);

    }
}
