package com.yunkuent.sdk;

import com.gokuai.base.RequestMethod;
import com.gokuai.base.ReturnResult;
import com.gokuai.base.utils.Util;

import java.util.HashMap;

/**
 * 企业库管理http请求类
 */
public class EntLibManager extends EntEngine {

    private final String URL_API_CREATE_LIB = HostConfig.API_ENT_HOST + "/1/org/create";
    private final String URL_API_SET = HostConfig.API_ENT_HOST + "/1/org/set";
    private final String URL_API_GET_INFO = HostConfig.API_ENT_HOST + "/1/org/info";
    private final String URL_API_INFO_BY_MEMBER = HostConfig.API_ENT_HOST + "/1/org/info_by_member";
    private final String URL_API_SET_BY_MEMBER = HostConfig.API_ENT_HOST + "/1/org/set_by_member";
    private final String URL_API_GET_LIB_LIST = HostConfig.API_ENT_HOST + "/1/org/ls";
    private final String URL_API_BIND = HostConfig.API_ENT_HOST + "/1/org/bind";
    private final String URL_API_UNBIND = HostConfig.API_ENT_HOST + "/1/org/unbind";

    private final String URL_API_GET_MEMBERS = HostConfig.API_ENT_HOST + "/1/org/get_members";
    private final String URL_API_GET_MEMBER = HostConfig.API_ENT_HOST + "/1/org/get_member";
    private final String URL_API_SET_OWNER = HostConfig.API_ENT_HOST + "/1/org/set_owner";
    private final String URL_API_ADD_MEMBERS = HostConfig.API_ENT_HOST + "/1/org/add_member";
    private final String URL_API_SET_MEMBER_ROLE = HostConfig.API_ENT_HOST + "/1/org/set_member_role";
    private final String URL_API_DEL_MEMBER = HostConfig.API_ENT_HOST + "/1/org/del_member";
    private final String URL_API_GET_GROUPS = HostConfig.API_ENT_HOST + "/1/org/get_groups";
    private final String URL_API_ADD_GROUP = HostConfig.API_ENT_HOST + "/1/org/add_group";
    private final String URL_API_DEL_GROUP = HostConfig.API_ENT_HOST + "/1/org/del_group";
    private final String URL_API_SET_GROUP_ROLE = HostConfig.API_ENT_HOST + "/1/org/set_group_role";
    private final String URL_API_DESTROY = HostConfig.API_ENT_HOST + "/1/org/destroy";
    private final String URL_API_LOG = HostConfig.API_ENT_HOST + "/1/org/log";

    private final String URL_API_GET_SEARCH = HostConfig.API_ENT_HOST + "/1/org/search";

    public EntLibManager(String clientId, String secret) {
        super(clientId, secret);
    }

    /**
     * 创建库
     *
     * @param orgName
     * @param orgCapacity
     * @param storagePointName
     * @return
     */
    public ReturnResult create(String orgName, String orgCapacity, String storagePointName, String orgLogo) {
        String url = URL_API_CREATE_LIB;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_name", orgName);
        params.put("org_capacity", orgCapacity);
        params.put("storage_point_name", storagePointName);
        params.put("org_logo", orgLogo);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }


    /**
     * 获取企业库列表
     *
     * @return
     */
    public ReturnResult getLibList() {
        return this.getLibList(0, 0);
    }

    /**
     * 获取指定成员参与的库列表
     *
     * @param memberId
     * @return
     */
    public ReturnResult getLibList(int memberId, int type) {
        String url = URL_API_GET_LIB_LIST;
        HashMap<String, String> params = new HashMap<String, String>();
        if (memberId > 0) {
            params.put("member_id", memberId + "");
        }
        params.put("type", type + "");
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }


    /**
     * 获取库授权
     *
     * @param orgId   库ID
     * @param appName 调用SDK的应用的名称
     * @return
     */
    public ReturnResult bind(int orgId, String appName) {
        String url = URL_API_BIND;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_id", String.valueOf(orgId));
        params.put("title", appName);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 取消库授权
     *
     * @param orgClientId
     * @return
     */
    public ReturnResult unBind(String orgClientId) {
        String url = URL_API_UNBIND;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", orgClientId);
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
    public ReturnResult getMembers(int start, int size, int orgId) {
        String url = URL_API_GET_MEMBERS;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_id", Integer.toString(orgId));
        params.put("start", Integer.toString(start));
        params.put("size", Integer.toString(size));
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
    public ReturnResult addMembers(int orgId, int roleId, int[] memberIds) {
        String url = URL_API_ADD_MEMBERS;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_id", Integer.toString(orgId));
        params.put("role_id", Integer.toString(roleId));
        params.put("member_ids", Util.intArrayToString(memberIds, ","));
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
    public ReturnResult setMemberRole(int orgId, int roleId, int[] memberIds) {
        String url = URL_API_SET_MEMBER_ROLE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_id", Integer.toString(orgId));
        params.put("role_id", Integer.toString(roleId));
        params.put("member_ids", Util.intArrayToString(memberIds, ","));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除库成员
     *
     * @param orgId
     * @param memberIds
     * @return
     */
    public ReturnResult delMember(int orgId, int[] memberIds) {
        String url = URL_API_DEL_MEMBER;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_id", Integer.toString(orgId));
        params.put("member_ids", Util.intArrayToString(memberIds, ","));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 获取库分组列表
     *
     * @param orgId
     * @return
     */
    public ReturnResult getGroups(int orgId) {
        String url = URL_API_GET_GROUPS;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_id", Integer.toString(orgId));
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
    public ReturnResult addGroup(int orgId, int groupId, int roleId) {
        String url = URL_API_ADD_GROUP;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_id", Integer.toString(orgId));
        params.put("group_id", Integer.toString(groupId));
        params.put("role_id", Integer.toString(roleId));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除库上的分组
     *
     * @param orgId
     * @param groupId
     * @return
     */
    public ReturnResult delGroup(int orgId, int groupId) {
        String url = URL_API_DEL_GROUP;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_id", Integer.toString(orgId));
        params.put("group_id", Integer.toString(groupId));
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
    public ReturnResult setGroupRole(int orgId, int groupId, int roleId) {
        String url = URL_API_SET_GROUP_ROLE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_id", Integer.toString(orgId));
        params.put("group_id", Integer.toString(groupId));
        params.put("role_id", Integer.toString(roleId));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除库
     *
     * @param orgId
     * @return
     */
    public ReturnResult destroy(int orgId) {
        String url = URL_API_DESTROY;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_id", Integer.toString(orgId));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除库
     *
     * @param orgClientId
     * @return
     */
    public ReturnResult destroyByOrgClientId(String orgClientId) {
        String url = URL_API_DESTROY;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", orgClientId);
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
    public ReturnResult set(int orgId, String orgName, String orgCapacity, String orgLogo) {
        String url = URL_API_SET;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_id", Integer.toString(orgId));
        if (!Util.isEmpty(orgName)) {
            params.put("org_name", orgName);
        }
        if (!Util.isEmpty(orgCapacity)) {
            params.put("org_capacity", orgCapacity);
        }
        if (!Util.isEmpty(orgLogo)) {
            params.put("org_logo", orgLogo);
        }
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
    public ReturnResult getMember(int orgId, MemberType type, String[] ids) {
        String url = URL_API_GET_MEMBER;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_id", Integer.toString(orgId));
        params.put("type", type.toString().toLowerCase());
        params.put("ids", Util.strArrayToString(ids, ","));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 获取库信息
     *
     * @param orgId
     * @return
     */
    public ReturnResult getInfo(int orgId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_id", Integer.toString(orgId));
        return new RequestHelper().setParams(params).setUrl(URL_API_GET_INFO).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 获取成员个人库信息, memberId, outId, account和email传其中一个即可
     *
     * @param memberId  成员ID
     * @param outId     成员外部系统ID
     * @param account   成员外部系统登录帐号
     * @param email     公有云帐号登录邮箱
     * @return ReturnResult
     */
    public ReturnResult getInfoByMembmer(int memberId, String outId, String account, String email) {
        HashMap<String, String> params = new HashMap<String, String>();
        if (memberId > 0) {
            params.put("member_id", Integer.toString(memberId));
        } else if (!Util.isEmpty(outId)) {
            params.put("out_id", outId);
        } else if (!Util.isEmpty(account)) {
            params.put("account", account);
        } else if (!Util.isEmpty(email)) {
            params.put("email", email);
        }
        return new RequestHelper().setParams(params).setUrl(URL_API_INFO_BY_MEMBER).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 设置成员个人库信息空间
     *
     * @param memberId  成员ID
     * @param outId     成员外部系统ID
     * @param account   成员外部系统登录帐号
     * @param email     公有云帐号登录邮箱
     * @param capacity  空间,单位字节
     * @return ReturnResult
     */
    public ReturnResult setByMembmer(int memberId, String outId, String account, String email, long capacity) {
        HashMap<String, String> params = new HashMap<String, String>();
        if (memberId > 0) {
            params.put("member_id", Integer.toString(memberId));
        } else if (!Util.isEmpty(outId)) {
            params.put("out_id", outId);
        } else if (!Util.isEmpty(account)) {
            params.put("account", account);
        } else if (!Util.isEmpty(email)) {
            params.put("email", email);
        }
        params.put("capacity", Long.toString(capacity));
        return new RequestHelper().setParams(params).setUrl(URL_API_SET_BY_MEMBER).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     *
     * @param orgId         库ID
     * @param acts          需要查询的动作, null表示返回所有动作
     * @param orderby       排序方式
     * @param startDateline 开始时间
     * @param endDateline   结束时间
     * @param start         开始位置
     * @param size          获取数量
     * @return ReturnResult
     */
    public ReturnResult getLogByOrgId(int orgId, Act[] acts, OrderBy orderby, Long startDateline, Long endDateline, int start, int size) {
        return this.getLog(orgId, 0, acts, orderby, startDateline, endDateline, start, size);
    }

    /**
     *
     * @param mountId       库空间ID
     * @param acts          需要查询的动作, null表示返回所有动作
     * @param orderby       排序方式
     * @param startDateline 开始时间
     * @param endDateline   结束时间
     * @param start         开始位置
     * @param size          获取数量
     * @return ReturnResult
     */
    public ReturnResult getLogByMountId(int mountId, Act[] acts, OrderBy orderby, Long startDateline, Long endDateline, int start, int size) {
        return this.getLog(mountId, 0, acts, orderby, startDateline, endDateline, start, size);
    }

    /**
     *
     * @param clientId       企业ID
     * @param name           库名称name，库名称前缀prefix, 模糊匹配, name 和 prefix 只需传其中一个
     * @param size           获取数量
     * @return ReturnResult
     */
    public ReturnResult getOrgSearchByClientId(String clientId, String name, int size) {

        String url = URL_API_GET_SEARCH;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("client_id", clientId);
        params.put("prefix", name);
        params.put("dateline", Util.getUnixDateline() + "");
        if (size > 0) {
            params.put("size", Integer.toString(size));
        }
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    private ReturnResult getLog(int orgId, int mountId, Act[] acts, OrderBy orderby, Long startDateline, Long endDateline, int start, int size) {
        HashMap<String, String> params = new HashMap<String, String>();
        if (orgId > 0) {
            params.put("org_id", Integer.toString(orgId));
        } else if (mountId > 0) {
            params.put("mount_id", Integer.toString(mountId));
        }
        if (acts != null && acts.length > 0) {
            params.put("act", Util.ArrayToString(acts, ","));
        }
        if (orderby != null) {
            params.put("orderby", orderby.toString());
        }
        if (startDateline != null) {
            params.put("start_dateline", startDateline.toString());
        }
        if (endDateline != null) {
            params.put("end_dateline", endDateline.toString());
        }
        if (start > 0) {
            params.put("start", Integer.toString(start));
        }
        if (size > 0) {
            params.put("size", Integer.toString(size));
        }
        return new RequestHelper().setParams(params).setUrl(URL_API_LOG).setMethod(RequestMethod.GET).executeSync();
    }

    public enum OrderBy {
        ASC("asc"),
        DESC("desc");

        private String order;

        OrderBy(String order) {
            this.order = order;
        }

        @Override
        public String toString() {
            return this.order;
        }
    }

    public enum Act {
        DELETE("0"),
        CREATE("1"),
        RENAME("2"),
        EDIT("3"),
        MOVE("4"),
        RECOVER("5"),
        REVERT("6"),
        LOCK("12"),
        UNLOCK("13"),
        DOWNLOAD("20"),
        PREVIEW("21"),
        LINK_CREATE("1014"),
        LINK_ACCESS("1015"),
        LINK_DOWNLOAD("1016"),
        LINK_SAVE("1017"),
        LINK_UPLOAD("1018");

        private String act;

        Act(String act) {
            this.act = act;
        }

        @Override
        public String toString() {
            return this.act;
        }
    }

}
