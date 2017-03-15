package com.yunkuent.sdk;

import com.yunkuent.sdk.data.ReturnResult;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by qp on 2017/3/8.
 */
public class YunkuEntTest {

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    @Test
    public void getRoles() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.getRoles();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getMembers() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.getMembers(0, 99);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getMemberById() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.getMemberById(74478);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getMemberByOutId() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.getMemberByOutId("$:LWCP_v1:$ypc3i0Op0Tn0Ge2GvyShWA==");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getMemberByAccount() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.getMemberByAccount("6905656124312207");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getGroups() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.getGroups();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getGroupMembers() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.getGroupMembers(1086, 0, 3, true);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getMemberFileLink() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.getMemberFileLink(74478,true);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void addSyncMember() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.addSyncMember("MemberTest1", "Member1", "Member1", "1234", "111", "111");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Ignore
    public void setSyncMemberState() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.setSyncMemberState("",true);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void delSyncMember() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.delSyncMember(new String[]{"MemberTest1"});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void addSyncGroup() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.addSyncGroup("ParentGroup", "ParentGroup", "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void delSyncGroup() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.delSyncGroup(new String[] { "ParentGroup"});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void addSyncGroupMember() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.addSyncGroupMember("GroupTest",new String[]{"MemberTest1"});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void delSyncGroupMember() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.delSyncGroupMember("GroupTest", new String[] {"MemberTest1"});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Ignore
    public void delSyncMemberGroup() throws Exception {
        EntManager ent = new EntManager(CLIENT_ID, CLIENT_SECRET);
        String s = ent.delSyncMemberGroup(new String[]{""});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

}