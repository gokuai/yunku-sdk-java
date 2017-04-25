package com.yunkuent.sdk.compat.v2;

import com.gokuai.base.data.ReturnResult;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by qp on 2017/3/8.
 */
public class YunkuEntTest {

    public static final String ADMIN = "";
    public static final String PASSWORD = "";
    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    @Test
    public void getRoles() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.getRoles();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getMembers() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.getMembers(0, 99);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getMemberById() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.getMemberById(504482);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getMemberByOutId() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.getMemberByOutId("ac1d8e1f-6d67-4143-8494-4c864c5f3d31");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getMemberByAccount() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.getMemberByAccount("lll");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getGroups() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.getGroups();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getGroupMembers() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.getGroupMembers(154837, 0, 3, true);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getMemberFileLink() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.getMemberFileLink(504482,true);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

//    @Ignore("addSyncMember is ignored")
    @Test
    public void addSyncMember() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.addSyncMember("MemberTest1", "Member1", "Member1", "1234", "111", "111");
        String s2 = ent.addSyncMember("MemberTest2", "Member2", "Member2", "", "", "");
        ReturnResult r = ReturnResult.create(s);
        ReturnResult r2 = ReturnResult.create(s2);
        Assert.assertEquals(200,r.getStatusCode());
        Assert.assertEquals(200,r2.getStatusCode());
    }

//    @Ignore("setSyncMemberState is ignored")
    @Test
    public void setSyncMemberState() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.setSyncMemberState("ac1d8e1f-6d67-4143-8494-4c864c5f3d31",true);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

//    @Ignore("delSyncMember is ignored")
    @Test
    public void delSyncMember() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.delSyncMember(new String[]{"MemberTest1"});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

//    @Ignore("addSyncGroup is ignored")
    @Test
    public void addSyncGroup() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.addSyncGroup("ParentGroup", "ParentGroup", "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

//    @Ignore("delSyncGroup is ignored")
    @Test
    public void delSyncGroup() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.delSyncGroup(new String[] { "ParentGroup"});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

//    @Ignore("addSyncGroupMember is ignored")
    @Test
    public void addSyncGroupMember() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.addSyncGroupMember("GroupTest",new String[]{"MemberTest1"});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

//    @Ignore("delSyncGroupMember is ignored")
    @Test
    public void delSyncGroupMember() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.delSyncGroupMember("GroupTest", new String[] {"MemberTest1"});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

//    @Ignore("delSyncMemberGroup is ignored")
    @Test
    public void delSyncMemberGroup() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ent.accessToken(ADMIN, PASSWORD);
        String s = ent.delSyncMemberGroup(new String[]{"MemberTest2"});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

}