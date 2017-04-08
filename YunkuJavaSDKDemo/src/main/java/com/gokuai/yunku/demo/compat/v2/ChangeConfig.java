package com.gokuai.yunku.demo.compat.v2;

import com.gokuai.yunku.demo.compat.v2.helper.EntManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;
import com.yunkuent.sdk.compat.v2.HostConfig;

/**
 * Created by qp on 2017/4/8.
 */
public class ChangeConfig {
    public static void main(String[] args) {

        HostConfig.changeConfig("s","s");

        String returnString = EntManagerHelper.getInstance().getMembers(0, 99);

        DeserializeHelper.getInstance().deserializeReturn(returnString);
    }
}
