package com.yunkuent.sdk;

import com.gokuai.base.ReturnResult;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by qp on 2017/3/8.
 */
public class YunkuEntLibTest {

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    @Ignore("create is ignored")
    @Test
    public void create() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.create("aaa", "1073741824", "destroy", "test lib");
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void set() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.set(1258748, "ttt", "1073741824", "");
        Assert.assertEquals(200,r.getCode());
    }

    @Ignore("getLibList is ignored")
    @Test
    public void getLibList() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.getLibList();
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void getLibListById() throws Exception {

        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.getLibList(1258748,0);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void getMember() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.getMember(1258748, MemberType.MEMBER_ID,new String[]{"4"});
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void getInfo() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.getInfo(1258748);
        Assert.assertEquals(200,r.getCode());
    }

    @Ignore("bind is ignored")
    @Test
    public void bind() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.bind(0,"YunkuJavaSDKDemo");
        System.out.println(r);
        Assert.assertEquals(200,r.getCode());
    }

    @Ignore("unBind is ignored")
    @Test
    public void unBind() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.unBind("");
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void getMembers() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.getMembers(0, 10, 1258748);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void addMembers() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.addMembers(1258748,3330,new int[]{52064});
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void setMemberRole() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.setMemberRole(1258748,3208,new int[]{216144});
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void delMember() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.delMember(1258748,new int[]{52064});
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void addGroup() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.addGroup(1258748,71715,3208);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void getGroups() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.getGroups(1258748);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void setGroupRole() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.setGroupRole(1258748,4448,3208);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void delGroup() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.delGroup(1258748,71715);
        Assert.assertEquals(200,r.getCode());
    }

    @Ignore("destroy is ignored")
    @Test
    public void destroy() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = entLib.destroy(1258748);
        Assert.assertEquals(200,r.getCode());
    }

}