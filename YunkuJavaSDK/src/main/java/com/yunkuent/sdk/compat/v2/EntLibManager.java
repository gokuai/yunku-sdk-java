package com.yunkuent.sdk.compat.v2;

import com.gokuai.base.HttpEngine;
import com.gokuai.base.RequestMethod;
import com.gokuai.base.utils.Util;
import com.yunkuent.sdk.MemberType;

import java.util.HashMap;

/**
 * Created by Brandon on 2017/3/20.
 */
public class EntLibManager extends OauthEngine {

    private final String URL_API_CREATE_LIB = HostConfig.API_ENT_HOST_V2 + "/1/org/create";
    private final String URL_API_GET_LIB_LIST = HostConfig.API_ENT_HOST_V2 + "/1/org/ls";
    private final String URL_API_BIND = HostConfig.API_ENT_HOST_V2 + "/1/org/bind";
    private final String URL_API_UNBIND = HostConfig.API_ENT_HOST_V2 + "/1/org/unbind";

    private final String URL_API_GET_MEMBERS = HostConfig.API_ENT_HOST_V2 + "/1/org/get_members";
    private final String URL_API_ADD_MEMBERS = HostConfig.API_ENT_HOST_V2 + "/1/org/add_member";
    private final String URL_API_SET_MEMBER_ROLE = HostConfig.API_ENT_HOST_V2 + "/1/org/set_member_role";
    private final String URL_API_DEL_MEMBER = HostConfig.API_ENT_HOST_V2 + "/1/org/del_member";
    private final String URL_API_GET_GROUPS = HostConfig.API_ENT_HOST_V2 + "/1/org/get_groups";
    private final String URL_API_ADD_GROUP = HostConfig.API_ENT_HOST_V2 + "/1/org/add_group";
    private final String URL_API_DEL_GROUP = HostConfig.API_ENT_HOST_V2 + "/1/org/del_group";
    private final String URL_API_SET_GROUP_ROLE = HostConfig.API_ENT_HOST_V2 + "/1/org/set_group_role";
    private final String URL_API_DESTROY = HostConfig.API_ENT_HOST_V2 + "/1/org/destroy";
    private final String URL_API_GET_MEMBER = HostConfig.API_ENT_HOST_V2 + "/1/org/get_member";
    private final String URL_API_SET = HostConfig.API_ENT_HOST_V2 + "/1/org/set";
    private final String URL_API_GET_INFO = HostConfig.API_ENT_HOST_V2 + "/1/org/info";

    public EntLibManager(String clientId, String clientSecret) {
        super(clientId, clientSecret, true);
    }

    public EntLibManager(String clientId, String clientSecret, boolean isEnt) {
        super(clientId, clientSecret, isEnt);
    }

    private EntLibManager(String clientId, String clientSecret, boolean isEnt, String token) {
        super(clientId, clientSecret, isEnt, token);
    }

    /**
     * 创建库
     *
     * @param orgName
     * @param orgCapacity
     * @param storagePointName
     * @return
     */
    public String create(String orgName, String orgCapacity, String storagePointName, String orgLogo) {
        String url = URL_API_CREATE_LIB;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("org_name", orgName);
        params.put("org_capacity", orgCapacity);
        params.put("storage_point_name", storagePointName);
        params.put("org_logo", orgLogo);
        params.put("sign", generateSign(params));
        return new HttpEngine.RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }


    /**
     * 获取企业库列表
     *
     * @return
     */
    public String getLibList() {
        return this.getLibList(0);
    }

    /**
     * 获取指定成员参与的库列表
     *
     * @param memberId
     * @return
     */
    public String getLibList(int memberId) {
        String url = URL_API_GET_LIB_LIST;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        if (memberId > 0) {
            params.put("member_id", memberId + "");
        }
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }


    /**
     * 获取库授权
     *
     * @param orgId   库ID
     * @param appName 调用SDK的应用的名称
     * @param linkUrl 可以不传
     * @return
     */
    public String bind(int orgId, String appName, String linkUrl) {
        String url = URL_API_BIND;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("org_id", String.valueOf(orgId));
        params.put("title", appName);
        params.put("url", linkUrl);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 取消库授权
     *
     * @param orgClientId
     * @return
     */
    public String unBind(String orgClientId) {
        String url = URL_API_UNBIND;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("org_client_id", orgClientId);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 获取库成员列表
     *
     * @param start
     * @param size
     * @param orgId
     * @return
     */
    public String getMembers(int start, int size, int orgId) {
        String url = URL_API_GET_MEMBERS;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("start", start + "");
        params.put("size", size + "");
        params.put("org_id", orgId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 添加库成员
     *
     * @param orgId
     * @param roleId
     * @param memberIds
     * @return
     */
    public String addMembers(int orgId, int roleId, int[] memberIds) {
        String url = URL_API_ADD_MEMBERS;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("role_id", roleId + "");
        params.put("org_id", orgId + "");
        params.put("member_ids", Util.intArrayToString(memberIds, ","));
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 修改库成员角色
     *
     * @param orgId
     * @param roleId
     * @param memberIds
     * @return
     */
    public String setMemberRole(int orgId, int roleId, int[] memberIds) {
        String url = URL_API_SET_MEMBER_ROLE;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("org_id", orgId + "");
        params.put("role_id", roleId + "");
        params.put("member_ids", Util.intArrayToString(memberIds, ","));
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除库成员
     *
     * @param orgId
     * @param memberIds
     * @return
     */
    public String delMember(int orgId, int[] memberIds) {
        String url = URL_API_DEL_MEMBER;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("org_id", orgId + "");
        params.put("member_ids", Util.intArrayToString(memberIds, ","));
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 获取库分组列表
     *
     * @param orgId
     * @return
     */
    public String getGroups(int orgId) {
        String url = URL_API_GET_GROUPS;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("org_id", orgId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 库上添加分组
     *
     * @param orgId
     * @param groupId
     * @param roleId
     * @return
     */
    public String addGroup(int orgId, int groupId, int roleId) {
        String url = URL_API_ADD_GROUP;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("org_id", orgId + "");
        params.put("group_id", groupId + "");
        params.put("role_id", roleId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除库上的分组
     *
     * @param orgId
     * @param groupId
     * @return
     */
    public String delGroup(int orgId, int groupId) {
        String url = URL_API_DEL_GROUP;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("org_id", orgId + "");
        params.put("group_id", groupId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 修改库上分组的角色
     *
     * @param orgId
     * @param groupId
     * @param roleId
     * @return
     */
    public String setGroupRole(int orgId, int groupId, int roleId) {
        String url = URL_API_SET_GROUP_ROLE;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("org_id", orgId + "");
        params.put("group_id", groupId + "");
        params.put("role_id", roleId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除库
     *
     * @param orgClientId
     * @return
     */
    public String destroy(String orgClientId) {
        String url = URL_API_DESTROY;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("org_client_id", orgClientId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 修改库信息
     *
     * @param orgId
     * @param orgName
     * @param orgCapacity
     * @param orgLogo
     * @return
     */
    public String set(int orgId, String orgName, String orgCapacity, String orgLogo) {
        String url = URL_API_SET;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("org_id", orgId + "");
        if (orgName != null && !orgName.isEmpty()) {
            params.put("org_name", orgName);
        }
        if (orgCapacity != null && !orgCapacity.isEmpty()) {
            params.put("org_capacity", orgCapacity + "");
        }
        if (orgLogo != null && !orgLogo.isEmpty()) {
            params.put("org_logo", orgLogo);
        }
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 查询库成员信息
     *
     * @param orgId
     * @param type
     * @param ids
     * @return
     */
    public String getMember(int orgId, MemberType type, String[] ids) {
        String url = URL_API_GET_MEMBER;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("org_id", orgId + "");
        params.put("type", type.toString().toLowerCase());
        params.put("ids", Util.strArrayToString(ids, ",") + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 获取库信息
     *
     * @param orgId
     * @return
     */
    public String getInfo(int orgId) {
        String url = URL_API_GET_INFO;
        HashMap<String, String> params = new HashMap<>();
        addAuthParams(params);
        params.put("org_id", orgId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }


    /**
     * 复制EntLibManager对象
     *
     * @return
     */
    public EntLibManager clone() {
        return new EntLibManager(mClientId, mClientSecret, mIsEnt, mToken);
    }

}