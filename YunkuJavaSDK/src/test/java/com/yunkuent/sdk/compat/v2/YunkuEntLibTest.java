package com.yunkuent.sdk.compat.v2;

import com.yunkuent.sdk.MemberType;
import com.yunkuent.sdk.data.ReturnResult;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by qp on 2017/3/8.
 */
public class YunkuEntLibTest {

    public static final String ADMIN = "";
    public static final String PASSWORD = "";
    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    @Ignore("create is ignored")
    @Test
    public void create() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.create("aaa", "1073741824", "destroy", "test lib", "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void set() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.set(1262696, "ttt", "1073741824", "", "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

//    @Ignore("getLibList is ignored")
    @Test
    public void getLibList() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.getLibList();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getLibListById() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.getLibList(885371);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getMember() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.getMember(1262679, MemberType.MEMBER_ID,new String[]{"239931"});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getInfo() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.getInfo(1262679);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Ignore("bind is ignored")
    @Test
    public void bind() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.bind(1262696,"YunkuJavaSDKDemo",null);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Ignore("unBind is ignored")
    @Test
    public void unBind() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.unBind("AxT0EUEjtYc8za41xXl1dKFJ40");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getMembers() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.getMembers(0, 10, 1262679);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void addMembers() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.addMembers(1262679,13862,new int[]{885371});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void setMemberRole() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.setMemberRole(1262679,13862,new int[]{885371});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void delMember() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.delMember(1262679,new int[]{885371});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void addGroup() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.addGroup(1262679,154837,13862);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getGroups() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.getGroups(1262679);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void setGroupRole() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.setGroupRole(1262679,154837,13862);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void delGroup() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.delGroup(1262679,154837);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Ignore("destroy is ignored")
    @Test
    public void destroy() throws Exception {
        EntLibManager entLib = new EntLibManager(CLIENT_ID, CLIENT_SECRET);
        entLib.accessToken(ADMIN, PASSWORD);
        String s = entLib.destroy("AxT0EUEjtYc8za41xXl1dKFJ40");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

}