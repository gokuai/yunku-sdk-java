package com.yunkuent.sdk;

import com.gokuai.base.data.ReturnResult;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by qp on 2017/3/16.
 */
public class YunkuThirdPartyTest {

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";
    public static final String OUT_ID = "";

    @Test
    public void createEnt() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        String s = thirdParty.createEnt("yunku2","yunku2","","","");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void createEntByMap() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        HashMap<String, String> map = new HashMap<>();
        map.put("__setting_site_url","aaa");
        String s = thirdParty.createEnt(map,"yunku","yunku","","","");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getEntInfo() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        String s = thirdParty.getEntInfo();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void orderSubscribe() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        String s = thirdParty.orderSubscribe(-1,1,12);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void orderUpgrade() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        String s = thirdParty.orderUpgrade(-1,1);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void orderRenew() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        String s = thirdParty.orderRenew(12);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void orderUnsubscribe() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        String s = thirdParty.orderUnsubscribe();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getEntToken() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        String s = thirdParty.getEntToken();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getSsoUrl() throws Exception {
        ThirdPartyManager thirdParty = new ThirdPartyManager(CLIENT_ID, CLIENT_SECRET, OUT_ID);
        String s = thirdParty.getSsoUrl("");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }
}
