package com.yunkuent.sdk;

import com.gokuai.base.ReturnResult;
import com.gokuai.base.utils.Util;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by qp on 2017/3/8.
 */
public class YunkuEntTest {

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    @Test
    public void getRoles() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.getRoles();
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void getMembers() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.getMembers(0, 99);
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void getMemberById() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.getMemberById(74478, false);
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void getMemberByOutId() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.getMemberByOutId("$:LWCP_v1:$ypc3i0Op0Tn0Ge2GvyShWA==", false);
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void getMemberByAccount() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.getMemberByAccount("6905656124312207", false);
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void getGroups() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.getGroups();
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void getGroupMembers() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.getGroupMembers(71715, 0, 3, true);
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void addSyncMember() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.addSyncMember("MemberTest1", "Member1", "Member1", "1234", "111", "111", null);
        ReturnResult r2 = ent.addSyncMember("MemberTest2", "Member2", "Member2", "", "", "", null);
        Assert.assertEquals(200, r.getCode());
        Assert.assertEquals(200, r2.getCode());
    }

    @Ignore
    @Test
    public void setSyncMemberState() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.setSyncMemberState("", true);
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void delSyncMember() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.delSyncMember(new String[]{"MemberTest1"});
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void addSyncGroup() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.addSyncGroup("ParentGroup", "ParentGroup", "");
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void delSyncGroup() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.delSyncGroup(new String[]{"ParentGroup"});
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void addSyncGroupMember() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.addSyncGroupMember("GroupTest", new String[]{"MemberTest1"});
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void delSyncGroupMember() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.delSyncGroupMember("GroupTest", new String[]{"MemberTest1"});
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void delSyncMemberGroup() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.delSyncMemberGroup(new String[]{"MemberTest2"});
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore
    @Test
    public void addSyncAdmin() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.addSyncAdmin("$:LWCP_v1:$ypc3i0Op0Tn0Ge2GvyShWA==", "", false);
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void getGroupByOutId() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.getGroupByOutID(CLIENT_ID, new String[]{"MemberTest1"});
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void getEntLog() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        ReturnResult r = ent.getLogByClientId(CLIENT_ID, null, null, null, null, 0, 0);
        Assert.assertEquals(200, r.getCode());
    }
}