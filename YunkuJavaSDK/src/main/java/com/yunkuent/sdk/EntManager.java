package com.yunkuent.sdk;

import com.gokuai.base.RequestMethod;
import com.gokuai.base.utils.Util;

import java.util.HashMap;

/**
 * Created by Brandon on 2014/8/14.
 */
public class EntManager extends OauthEngine {

    private final String URL_API_GET_GROUPS = HostConfig.API_ENT_HOST + "/1/ent/get_groups";
    private final String URL_API_GET_MEMBERS = HostConfig.API_ENT_HOST + "/1/ent/get_members";
    private final String URL_API_GET_MEMBER = HostConfig.API_ENT_HOST + "/1/ent/get_member";
    private final String URL_API_GET_ROLES = HostConfig.API_ENT_HOST + "/1/ent/get_roles";
    //    private final String URL_API_SYNC_MEMBER = HostConfig.API_ENT_HOST + "/1/ent/sync_member";
    private final String URL_API_GET_MEMBER_FILE_LINK = HostConfig.API_ENT_HOST + "/1/ent/get_member_file_link";
//    private final String URL_API_GET_MEMBER_BY_OUT_ID = HostConfig.API_ENT_HOST + "/1/ent/get_member_by_out_id";

    private final String URL_API_ADD_SYNC_MEMBER = HostConfig.API_ENT_HOST + "/1/ent/add_sync_member";
    private final String URL_API_DEL_SYNC_MEMBER = HostConfig.API_ENT_HOST + "/1/ent/del_sync_member";
    private final String URL_API_ADD_SYNC_GROUP = HostConfig.API_ENT_HOST + "/1/ent/add_sync_group";
    private final String URL_API_DEL_SYNC_GROUP = HostConfig.API_ENT_HOST + "/1/ent/del_sync_group";
    private final String URL_API_ADD_SYNC_GROUP_MEMBER = HostConfig.API_ENT_HOST + "/1/ent/add_sync_group_member";
    private final String URL_API_DEL_SYNC_GROUP_MEMBER = HostConfig.API_ENT_HOST + "/1/ent/del_sync_group_member";
    private final String URL_API_DEL_SYNC_MEMBER_GROUP = HostConfig.API_ENT_HOST + "/1/ent/del_sync_member_group";
    private final String URL_API_GET_GROUP_MEMBERS = HostConfig.API_ENT_HOST + "/1/ent/get_group_members";
    private final String ADD_SYNC_ADMIN = HostConfig.API_ENT_HOST + "/1/ent/add_sync_admin";
    private final String MEMBER_LOGIN_REPORT = HostConfig.API_ENT_HOST + "/1/ent/member_login_report";

    public EntManager(String clientId, String clientSecret) {
        super(clientId, clientSecret, true);
    }

    private EntManager(String clientId, String clientSecret, boolean isEnt, String token) {
        super(clientId, clientSecret, isEnt, token);
    }

    /**
     * 获取角色
     *
     * @return
     */
    public String getRoles() {
        String url = URL_API_GET_ROLES;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 获取成员
     *
     * @param start
     * @param size
     * @return
     */
    public String getMembers(int start, int size) {
        String url = URL_API_GET_MEMBERS;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("start", start + "");
        params.put("size", size + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }


    private String getMember(int memberId, String outId, String account, boolean showGroups) {
        String url = URL_API_GET_MEMBER;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        if (memberId > 0) {
            params.put("member_id", memberId + "");
        }
        params.put("out_id", outId);
        params.put("account", account);
        params.put("sign", generateSign(params));
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
    public String getMemberById(int memberId, boolean showGroups) {
        return getMember(memberId, null, null, showGroups);
    }

    /**
     * 根据外部id获取企业成员信息
     *
     * @param outId
     * @param showGroups
     * @return
     */
    public String getMemberByOutId(String outId, boolean showGroups) {
        return getMember(0, outId, null, showGroups);

    }

    /**
     * 根据帐号获取企业成员信息
     *
     * @param account
     * @param showGroups
     * @return
     */
    public String getMemberByAccount(String account, boolean showGroups) {
        return getMember(0, null, account, showGroups);
    }


    /**
     * 获取分组
     *
     * @return
     */
    public String getGroups() {
        String url = URL_API_GET_GROUPS;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 根据成员id获取成员个人库外链
     *
     * @param memberId
     * @return
     */

    public String getMemberFileLink(int memberId, boolean fileOnly) {
        String url = URL_API_GET_MEMBER_FILE_LINK;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("member_id", memberId + "");
        if (fileOnly) {
            params.put("file", "1");
        }
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

//    /**
//     * 根据外部成员id获取成员信息
//     *
//     * @return
//     */
//    public String getMemberByOutid(String outIds[]) {
//        if (outIds == null) {
//            throw new NullPointerException("outIds is null");
//        }
//        return getMemberByIds(null, outIds);
//
//    }
//
//    /**
//     * 根据外部成员登录帐号获取成员信息
//     *
//     * @return
//     */
//    public String getMemberByUserId(String[] userIds) {
//        if (userIds == null) {
//            throw new NullPointerException("userIds is null");
//        }
//        return getMemberByIds(userIds, null);
//    }

//    private String getMemberByIds(String[] userIds, String[] outIds) {
//        String method = "GET";
//        String url = URL_API_GET_MEMBER_BY_OUT_ID;
//        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("client_id", mClientId));
//        params.add(new BasicNameValuePair("dateline", Util.getUnixDateline() + ""));
//        if (outIds != null) {
//            params.add(new BasicNameValuePair("out_ids", Util.strArrayToString(outIds, ",") + ""));
//        } else {
//            params.add(new BasicNameValuePair("user_ids", Util.strArrayToString(userIds, ",") + ""));
//        }
//        params.add(new BasicNameValuePair("sign", generateSign(paramSorted(params))));
//        return NetConnection.sendRequest(url, method, params, null);
//    }

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
    public String addSyncMember(String outId, String memberName, String account, String memberEmail, String memberPhone, String password, String state) {
        String url = URL_API_ADD_SYNC_MEMBER;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("out_id", outId);
        params.put("member_name", memberName);
        params.put("account", account);
        params.put("member_email", memberEmail);
        params.put("member_phone", memberPhone);
        params.put("password", password);
        params.put("state", state);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 设置成员状态
     *
     * @param oudId
     * @return
     */
    public String setSyncMemberState(String oudId, boolean state) {
        String url = URL_API_ADD_SYNC_MEMBER;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("out_id", oudId);
        params.put("state", state ? "1" : "0");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除同步成员
     *
     * @param members
     * @return
     */
    public String delSyncMember(String[] members) {
        String url = URL_API_DEL_SYNC_MEMBER;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("members", Util.strArrayToString(members, ","));
        params.put("sign", generateSign(params));
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
    public String addSyncGroup(String outId, String name, String parentOutId) {
        String url = URL_API_ADD_SYNC_GROUP;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("out_id", outId);
        params.put("name", name);
        params.put("parent_out_id", parentOutId);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除同步分组
     *
     * @param groups
     * @return
     */
    public String delSyncGroup(String[] groups) {
        String url = URL_API_DEL_SYNC_GROUP;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("groups", Util.strArrayToString(groups, ","));
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 添加同步分组的成员
     *
     * @param groupOutId
     * @param members
     * @return
     */
    public String addSyncGroupMember(String groupOutId, String[] members) {
        String url = URL_API_ADD_SYNC_GROUP_MEMBER;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("group_out_id", groupOutId);
        params.put("members", Util.strArrayToString(members, ","));
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除同步分组的成员
     *
     * @param groupOutId
     * @param members
     * @return
     */
    public String delSyncGroupMember(String groupOutId, String[] members) {
        String url = URL_API_DEL_SYNC_GROUP_MEMBER;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("group_out_id", groupOutId);
        params.put("members", Util.strArrayToString(members, ","));
        params.put("sign", generateSign(params));
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
    public String getGroupMembers(int groupId, int start, int size, boolean showChild) {
        String url = URL_API_GET_GROUP_MEMBERS;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("group_id", groupId + "");
        params.put("start", start + "");
        params.put("size", size + "");
        params.put("show_child", (showChild ? 1 : 0) + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 删除成员的所有同步分组
     *
     * @param members
     * @return
     */
    public String delSyncMemberGroup(String[] members) {
        String url = URL_API_DEL_SYNC_MEMBER_GROUP;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("members", Util.strArrayToString(members, ","));
        params.put("sign", generateSign(params));
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
    public String addSyncAdmin(String outId, String memberEmail, boolean isSuperAdmin) {
        String url = ADD_SYNC_ADMIN;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("out_id", outId);
        params.put("member_email", memberEmail);
        params.put("type", (isSuperAdmin ? 1 : 0) + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 成员登录情况统计
     *
     * @param startDate
     * @param enDdate
     * @return
     */
    public String memberLoginReport(String startDate, String enDdate) {
        String url = MEMBER_LOGIN_REPORT;
        HashMap<String, String> params = new HashMap<String, String>();
        addAuthParams(params);
        params.put("start_date", startDate);
        params.put("end_date", enDdate);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 复制一个EntManager对象
     *
     * @return
     */
    public EntManager clone() {
        return new EntManager(mClientId, mClientSecret, mIsEnt, mToken);
    }

}
