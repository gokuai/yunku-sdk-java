package com.yunkuent.sdk;

import com.gokuai.base.ReturnResult;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;

/**
 * Created by qp on 2017/3/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class YunkuThirdPartyTest {

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";
    public static final String OUT_ID = "";

    @Ignore
    @Test
    public void createEnt() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        ReturnResult r = thirdParty.createEnt("yunku2", "yunku2", "", "", "");
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void createEntByMap() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("__setting_site_url", "aaa");
        ReturnResult r = thirdParty.createEnt(map, "yunku", "yunku", "", "", "");
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void getEntInfo() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        ReturnResult r = thirdParty.getEntInfo();
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void orderSubscribe() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        ReturnResult r = thirdParty.orderSubscribe(-1, 1, 12);
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void orderUpgrade() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        ReturnResult r = thirdParty.orderUpgrade(-1, 1);
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void orderRenew() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        ReturnResult r = thirdParty.orderRenew(12);
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void orderUnsubscribe() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        ReturnResult r = thirdParty.orderUnsubscribe();
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void getEntToken() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        ReturnResult r = thirdParty.getEntToken();
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void getSsoUrl() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        ReturnResult r = thirdParty.getSsoUrl("");
        Assert.assertEquals(200, r.getCode());
    }
}
