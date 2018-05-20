package com.yunkuent.sdk;

import com.gokuai.base.RequestMethod;
import com.gokuai.base.ReturnResult;
import com.gokuai.base.utils.Util;

import java.util.HashMap;

/**
 * Created by Brandon on 2014/8/14.
 */
public class EntManager extends EntEngine {

    private final String URL_API_GET_GROUPS = HostConfig.API_ENT_HOST + "/1/ent/get_groups";
    private final String URL_API_GET_MEMBERS = HostConfig.API_ENT_HOST + "/1/ent/get_members";
    private final String URL_API_GET_MEMBER = HostConfig.API_ENT_HOST + "/1/ent/get_member";
    private final String URL_API_GET_ROLES = HostConfig.API_ENT_HOST + "/1/ent/get_roles";
    private final String URL_API_GET_MEMBER_BY_OUT_ID = HostConfig.API_ENT_HOST + "/1/ent/get_member_by_out_id";

    private final String URL_API_ADD_SYNC_MEMBER = HostConfig.API_ENT_HOST + "/1/ent/add_sync_member";
    private final String URL_API_DEL_SYNC_MEMBER = HostConfig.API_ENT_HOST + "/1/ent/del_sync_member";
    private final String URL_API_ADD_SYNC_GROUP = HostConfig.API_ENT_HOST + "/1/ent/add_sync_group";
    private final String URL_API_DEL_SYNC_GROUP = HostConfig.API_ENT_HOST + "/1/ent/del_sync_group";
    private final String URL_API_ADD_SYNC_GROUP_MEMBER = HostConfig.API_ENT_HOST + "/1/ent/add_sync_group_member";
    private final String URL_API_DEL_SYNC_GROUP_MEMBER = HostConfig.API_ENT_HOST + "/1/ent/del_sync_group_member";
    private final String URL_API_DEL_SYNC_MEMBER_GROUP = HostConfig.API_ENT_HOST + "/1/ent/del_sync_member_group";
    private final String URL_API_GET_GROUP_MEMBERS = HostConfig.API_ENT_HOST + "/1/ent/get_group_members";
    private final String ADD_SYNC_ADMIN = HostConfig.API_ENT_HOST + "/1/ent/add_sync_admin";

    public EntManager(String clientId, String secret) {
        super(clientId, secret);
    }

    /**
     * 获取角色
     *
     * @return
     */
    public ReturnResult getRoles() {
        String url = URL_API_GET_ROLES;
        return new RequestHelper().setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 获取成员
     *
     * @param start
     * @param size
     * @return
     */
    public ReturnResult getMembers(int start, int size) {
        String url = URL_API_GET_MEMBERS;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("start", Integer.toString(start));
        params.put("size", Integer.toString(size));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    private ReturnResult getMember(int memberId, String outId, String account, boolean showGroups) {
        String url = URL_API_GET_MEMBER;
        HashMap<String, String> params = new HashMap<String, String>();
        if (memberId > 0) {
            params.put("member_id", Integer.toString(memberId));
        }
        if (!Util.isEmpty(outId)) {
            params.put("out_id", outId);
        }
        if (!Util.isEmpty(account)) {
            params.put("account", account);
        }
        if (showGroups) {
            params.put("show_groups", "1");
        }
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 根据成员id获取企业成员信息
     *
     * @param memberId
     * @param showGroups
     * @return
     */
    public ReturnResult getMemberById(int memberId, boolean showGroups) {
        return getMember(memberId, null, null, showGroups);
    }

    /**
     * 根据外部id获取企业成员信息
     *
     * @param outId
     * @param showGroups
     * @return
     */
    public ReturnResult getMemberByOutId(String outId, boolean showGroups) {
        return getMember(0, outId, null, showGroups);

    }

    /**
     * 根据帐号获取企业成员信息
     *
     * @param account
     * @param showGroups
     * @return
     */
    public ReturnResult getMemberByAccount(String account, boolean showGroups) {
        return getMember(0, null, account, showGroups);
    }


    /**
     * 获取分组
     *
     * @return
     */
    public ReturnResult getGroups() {
        String url = URL_API_GET_GROUPS;
        return new RequestHelper().setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 根据外部成员id获取成员信息
     *
     * @return
     */
    public ReturnResult getMemberByOutids(String[] outIds) {
        return getMemberByIds(null, outIds);
    }

    /**
     * 根据外部成员登录帐号获取成员信息
     *
     * @return
     */
    public ReturnResult getMemberByUserIds(String[] userIds) {
        return getMemberByIds(userIds, null);
    }

    private ReturnResult getMemberByIds(String[] outIds, String[] userIds) {
        String url = URL_API_GET_MEMBER_BY_OUT_ID;
        HashMap<String, String> params = new HashMap<String, String>();
        if (outIds != null && outIds.length > 0) {
            params.put("out_ids", Util.strArrayToString(outIds, ","));
        } else {
            params.put("out_ids", Util.strArrayToString(userIds, ","));
        }
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 添加或修改同步成员
     *
     * @param outId
     * @param memberName
     * @param account
     * @param memberEmail
     * @param memberPhone
     * @param password    如果需要由够快验证帐号密码,密码为必须参数
     * @return
     */
    public ReturnResult addSyncMember(String outId, String memberName, String account, String memberEmail, String memberPhone, String password, String state) {
        String url = URL_API_ADD_SYNC_MEMBER;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("out_id", outId);
        params.put("member_name", memberName);
        params.put("account", account);
        params.put("member_email", memberEmail);
        params.put("member_phone", memberPhone);
        params.put("password", password);
        params.put("state", state);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 设置成员状态
     *
     * @param oudId
     * @return
     */
    public ReturnResult setSyncMemberState(String oudId, boolean state) {
        String url = URL_API_ADD_SYNC_MEMBER;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("out_id", oudId);
        params.put("state", state ? "1" : "0");
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除同步成员
     *
     * @param members
     * @return
     */
    public ReturnResult delSyncMember(String[] members) {
        String url = URL_API_DEL_SYNC_MEMBER;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("members", Util.strArrayToString(members, ","));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 添加或修改同步分组
     *
     * @param outId
     * @param name
     * @param parentOutId
     * @return
     */
    public ReturnResult addSyncGroup(String outId, String name, String parentOutId) {
        String url = URL_API_ADD_SYNC_GROUP;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("out_id", outId);
        params.put("name", name);
        params.put("parent_out_id", parentOutId);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除同步分组
     *
     * @param groups
     * @return
     */
    public ReturnResult delSyncGroup(String[] groups) {
        String url = URL_API_DEL_SYNC_GROUP;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("groups", Util.strArrayToString(groups, ","));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 添加同步分组的成员
     *
     * @param groupOutId
     * @param members
     * @return
     */
    public ReturnResult addSyncGroupMember(String groupOutId, String[] members) {
        String url = URL_API_ADD_SYNC_GROUP_MEMBER;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("group_out_id", groupOutId);
        params.put("members", Util.strArrayToString(members, ","));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除同步分组的成员
     *
     * @param groupOutId
     * @param members
     * @return
     */
    public ReturnResult delSyncGroupMember(String groupOutId, String[] members) {
        String url = URL_API_DEL_SYNC_GROUP_MEMBER;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("group_out_id", groupOutId);
        params.put("members", Util.strArrayToString(members, ","));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 分组成员列表
     *
     * @param groupId
     * @param start
     * @param size
     * @param showChild
     * @return
     */
    public ReturnResult getGroupMembers(int groupId, int start, int size, boolean showChild) {
        String url = URL_API_GET_GROUP_MEMBERS;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("group_id", Integer.toString(groupId));
        params.put("start", Integer.toString(start));
        params.put("size", Integer.toString(size));
        params.put("show_child", (showChild ? "1" : "0"));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 删除成员的所有同步分组
     *
     * @param members
     * @return
     */
    public ReturnResult delSyncMemberGroup(String[] members) {
        String url = URL_API_DEL_SYNC_MEMBER_GROUP;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("members", Util.strArrayToString(members, ","));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 添加管理员
     *
     * @param outId
     * @param memberEmail
     * @param isSuperAdmin
     * @return
     */
    public ReturnResult addSyncAdmin(String outId, String memberEmail, boolean isSuperAdmin) {
        String url = ADD_SYNC_ADMIN;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("out_id", outId);
        params.put("member_email", memberEmail);
        params.put("type", (isSuperAdmin ? "1" : "0"));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 复制一个EntManager对象
     *
     * @return
     */
    public EntManager clone() {
        return new EntManager(mClientId, mSecret);
    }
}
