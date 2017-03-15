package com.yunkuent.sdk;

import com.yunkuent.sdk.data.ReturnResult;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by qp on 2017/3/8.
 */
public class YunkuEntLibTest {

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    @Ignore
    public void create() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.create("aaa", "1073741824", "destroy", "test lib", "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void set() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.set(1258748, "ttt", "1073741824", "", "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getLibList() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.getLibList();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getLibListById() throws Exception {

        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.getLibList(1258748);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getMember() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.getMember(4405, MemberType.ACCOUNT,new String[]{"qwdqwdq1"});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getInfo() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.getInfo(1258748);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Ignore
    public void bind() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.bind(0,"YunkuJavaSDKDemo",null);
        ReturnResult r = ReturnResult.create(s);
        System.out.println(r);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Ignore
    public void unBind() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.unBind("");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getMembers() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.getMembers(0, 10, 1258748);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void addMembers() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.addMembers(1258748,3330,new int[]{52064});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void setMemberRole() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.setMemberRole(1258748,3208,new int[]{216144});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void delMember() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.delMember(1258748,new int[]{52064});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void addGroup() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.addGroup(1258748,71715,3208);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getGroups() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.getGroups(1258748);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void setGroupRole() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.setGroupRole(1258748,4448,3208);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void delGroup() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.delGroup(1258748,71715);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Ignore
    public void destroy() throws Exception {
        EntLibManager entFile = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        String s = entFile.destroy("b2013df96cbc23b4b0dd72f075e5cbf7");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

}