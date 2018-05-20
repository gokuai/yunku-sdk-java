package com.gokuai.yunku.demo;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.gokuai.yunku.demo.helper.EntManagerHelper;
import com.yunkuent.sdk.ConfigHelper;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Created by qp on 2017/4/13.
 */
public class SetProxy {
    public static void main(String[] args) {

        DebugConfig.DEBUG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("",80));
        new ConfigHelper().proxy(proxy).config();

        ReturnResult result = EntManagerHelper.getInstance().getMembers(0, 99);

        DeserializeHelper.getInstance().deserializeReturn(result);

    }

}
