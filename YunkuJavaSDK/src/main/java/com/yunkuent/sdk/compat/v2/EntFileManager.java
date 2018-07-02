package com.yunkuent.sdk.compat.v2;

import com.gokuai.base.HttpEngine;
import com.gokuai.base.RequestMethod;
import com.gokuai.base.ReturnResult;
import com.gokuai.base.utils.Util;
import com.google.gson.Gson;
import com.yunkuent.sdk.FilePermissions;
import com.yunkuent.sdk.UploadManager;
import com.yunkuent.sdk.data.FileInfo;
import com.yunkuent.sdk.data.YunkuException;
import com.yunkuent.sdk.upload.IEntFileManager;
import com.yunkuent.sdk.upload.UploadCallback;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Brandon on 2017/3/20.
 */
public class EntFileManager extends HttpEngine implements IEntFileManager {

    private static final String TAG = "EntFileManager";

    static String DEFAULT_OPNAME = "";
    static String UPLOAD_ROOT_PATH = "";

    private final String URL_API_FILELIST = HostConfig.API_ENT_HOST_V2 + "/1/file/ls";
    private final String URL_API_UPDATE_LIST = HostConfig.API_ENT_HOST_V2 + "/1/file/updates";
    private final String URL_API_FILE_INFO = HostConfig.API_ENT_HOST_V2 + "/1/file/info";
    private final String URL_API_CREATE_FOLDER = HostConfig.API_ENT_HOST_V2 + "/1/file/create_folder";
    private final String URL_API_CREATE_FILE = HostConfig.API_ENT_HOST_V2 + "/1/file/create_file";
    private final String URL_API_DEL_FILE = HostConfig.API_ENT_HOST_V2 + "/1/file/del";
    private final String URL_API_MOVE_FILE = HostConfig.API_ENT_HOST_V2 + "/1/file/move";
    private final String URL_API_HISTORY_FILE = HostConfig.API_ENT_HOST_V2 + "/1/file/history";
    private final String URL_API_LINK_FILE = HostConfig.API_ENT_HOST_V2 + "/1/file/link";
    private final String URL_API_SENDMSG = HostConfig.API_ENT_HOST_V2 + "/1/file/sendmsg";
    private final String URL_API_GET_LINK = HostConfig.API_ENT_HOST_V2 + "/1/file/links";
    private final String URL_API_UPDATE_COUNT = HostConfig.API_ENT_HOST_V2 + "/1/file/updates_count";
    private final String URL_API_GET_SERVER_SITE = HostConfig.API_ENT_HOST_V2 + "/1/file/servers";
    private final String URL_API_CREATE_FILE_BY_URL = HostConfig.API_ENT_HOST_V2 + "/1/file/create_file_by_url";
    private final String URL_API_UPLOAD_SERVERS = HostConfig.API_ENT_HOST_V2 + "/1/file/upload_servers";
    private final String URL_API_FILE_SEARCH = HostConfig.API_ENT_HOST_V2 + "/1/file/search";
    private final String URL_API_GET_PERMISSION = HostConfig.API_ENT_HOST_V2 + "/1/file/get_permission";
    private final String URL_API_SET_PERMISSION = HostConfig.API_ENT_HOST_V2 + "/1/file/file_permission";
    private final String URL_API_ADD_TAG = HostConfig.API_ENT_HOST_V2 + "/1/file/add_tag";
    private final String URL_API_DEL_TAG = HostConfig.API_ENT_HOST_V2 + "/1/file/del_tag";

    public EntFileManager(String clientId, String clientSecret) {
        super(clientId, clientSecret);
    }

    /**
     * 获取根目录文件列表
     *
     * @return
     */
    public ReturnResult getFileList() {
        return this.getFileList("", 0, 100, false);
    }

    /**
     * 获取文件列表
     *
     * @param fullPath 路径, 空字符串表示根目录
     * @return
     */
    public ReturnResult getFileList(String fullPath) {
        return this.getFileList(fullPath, 0, 100, false);
    }

    /**
     * 获取文件列表
     *
     * @param fullPath 路径, 空字符串表示根目录
     * @param start    起始下标, 分页显示
     * @param size     返回文件/文件夹数量限制
     * @return
     */
    public ReturnResult getFileList(String fullPath, int start, int size, boolean dirOnly) {
        String url = URL_API_FILELIST;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);
        params.put("start", start + "");
        params.put("size", size + "");
        if (dirOnly) {
            params.put("dir", "1");
        }
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 获取更新列表
     *
     * @param isCompare
     * @param fetchDateline
     * @return
     */
    public ReturnResult getUpdateList(boolean isCompare, long fetchDateline) {
        String url = URL_API_UPDATE_LIST;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        if (isCompare) {
            params.put("mode", "compare");
        }
        params.put("fetch_dateline", fetchDateline + "");
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 获取文件信息
     *
     * @param fullPath
     * @param net
     * @return
     */
    public ReturnResult getFileInfo(String fullPath, EntFileManager.NetType net, boolean getAttribute) {
        String url = URL_API_FILE_INFO;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);
        params.put("attribute", (getAttribute ? 1 : 0) + "");
        switch (net) {
            case DEFAULT:
                break;
            case IN:
                params.put("net", net.name().toLowerCase());
                break;
        }
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 创建文件夹
     *
     * @param fullPath
     * @param opName
     * @return
     */
    public ReturnResult createFolder(String fullPath, String opName) {
        String url = URL_API_CREATE_FOLDER;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);
        params.put("op_name", opName);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 获取实际的上传地址
     *
     * @return
     */
    private String getRealPath(String fullPath) {
        if (!Util.isEmpty(UPLOAD_ROOT_PATH)) {
            return UPLOAD_ROOT_PATH + fullPath;
        }
        return fullPath;
    }

    public ReturnResult createFile(String fullpath, String fileHash, long fileSize, boolean overwrite, String opName, int opId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("fullpath", fullpath);
        if (opId > 0) {
            params.put("op_id", Integer.toString(opId));
        } else if (opName != null && !opName.isEmpty()) {
            params.put("op_name", opName);
        }
        params.put("filehash", fileHash);
        params.put("filesize", Long.toString(fileSize));
        params.put("overwrite", (overwrite ? "1" : "0"));
        params.put("dateline", Long.toString(Util.getUnixDateline()));

        return new RequestHelper().setParams(params).setUrl(URL_API_CREATE_FILE).setMethod(RequestMethod.POST).executeSync();
    }

    private UploadManager initUploadManager(String opName, int opId, int blockSize) {
        opName = Util.isEmpty(opName) ? DEFAULT_OPNAME : opName;
        UploadManager manager = new UploadManager(blockSize, this);
        if (opId > 0) {
            manager.setOperator(opId);
        }
        if (!Util.isEmpty(opName)) {
            manager.setOperator(opName);
        }
        return manager;
    }

    /**
     * 文件分块上传
     *
     * @param fullpath
     * @param opName
     * @param opId
     * @param localFile
     * @param overwrite
     * @param blockSize
     */
    public FileInfo uploadByBlock(String fullpath, String opName, int opId, String localFile,
                                  boolean overwrite, int blockSize) throws YunkuException {
        UploadManager manager = this.initUploadManager(opName, opId, blockSize);
        fullpath = getRealPath(fullpath);
        return manager.upload(localFile, fullpath, overwrite);
    }

    /**
     * 文件分块上传, 异步方式
     *
     * @param fullpath
     * @param opName
     * @param opId
     * @param localFile
     * @param overwrite
     * @param callback
     */
    public UploadManager uploadByBlockAsync(String fullpath, String opName, int opId, String localFile,
                                       boolean overwrite, int blockSize, UploadCallback callback) {
        UploadManager manager = this.initUploadManager(opName, opId, blockSize);
        fullpath = getRealPath(fullpath);
        manager.uploadAsync(localFile, fullpath, overwrite, callback);
        return manager;
    }

    /**
     * 删除文件
     *
     * @param fullPaths
     * @param opName
     * @return
     */
    public ReturnResult del(String fullPaths, String opName) {
        String url = URL_API_DEL_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpaths", fullPaths);
        params.put("op_name", opName);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 移动文件
     *
     * @param fullPath
     * @param destFullPath
     * @param opName
     * @return
     */
    public ReturnResult move(String fullPath, String destFullPath, String opName) {
        String url = URL_API_MOVE_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);
        params.put("dest_fullpath", destFullPath);
        params.put("op_name", opName);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 获取文件历史
     *
     * @param fullPath
     * @param start
     * @param size
     * @return
     */
    public ReturnResult history(String fullPath, int start, int size) {
        String url = URL_API_HISTORY_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);
        params.put("start", start + "");
        params.put("size", size + "");
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 获取文件链接
     *
     * @param fullPath
     * @param deadline
     * @param authType
     * @param password
     * @return
     */
    public ReturnResult link(String fullPath, int deadline, EntFileManager.AuthType authType, String password) {
        String url = URL_API_LINK_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);

        if (deadline != 0) {
            params.put("deadline", deadline + "");
        }

        if (!authType.equals(AuthType.DEFAULT)) {
            params.put("auth", authType.toString().toLowerCase());
        }
        params.put("password", password);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 获取当前库所有外链
     *
     * @return
     */
    public ReturnResult links(boolean fileOnly) {
        String url = URL_API_GET_LINK;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        if (fileOnly) {
            params.put("file", "1");
        }
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 文件更新数量
     *
     * @param beginDateline
     * @param endDateline
     * @param showDelete
     * @return
     */
    public ReturnResult getUpdateCounts(long beginDateline, long endDateline, boolean showDelete) {
        String url = URL_API_UPDATE_COUNT;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("begin_dateline", beginDateline + "");
        params.put("end_dateline", endDateline + "");
        params.put("showdel", (showDelete ? 1 : 0) + "");
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 获取上传地址
     * <p>
     * (支持50MB以上文件的上传)
     *
     * @return
     */
    public ReturnResult getUploadServers() {
        String url = URL_API_UPLOAD_SERVERS;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }


    /**
     * 获取服务器地址
     *
     * @param type
     * @return
     */
    public ReturnResult getServers(String type) {
        String url = URL_API_GET_SERVER_SITE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("type", type);
        params.put("dateline", Util.getUnixDateline() + "");
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 文件搜索
     *
     * @param keyWords
     * @param path
     * @param start
     * @param size
     * @return
     */
    public ReturnResult search(String keyWords, String path, int start, int size) {
        String url = URL_API_FILE_SEARCH;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("keywords", keyWords);
        params.put("path", path);
        params.put("start", start + "");
        params.put("size", size + "");
        params.put("dateline", Util.getUnixDateline() + "");
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 获取文件夹权限
     *
     * @param fullPath
     * @param memberId
     * @return
     */
    public ReturnResult getPermission(String fullPath, int memberId) {
        String url = URL_API_GET_PERMISSION;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);
        params.put("member_id", memberId + "");
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 修改文件夹权限
     *
     * @param fullPath
     * @param permissions
     * @return
     */
    public ReturnResult setPermission(String fullPath, int memberId, FilePermissions... permissions) {
        String url = URL_API_SET_PERMISSION;
        HashMap<String, String> params = new HashMap<String, String>();
        if (permissions != null) {
            HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
            ArrayList<String> list = new ArrayList<String>();
            for (FilePermissions p : permissions) {
                list.add(p.toString().toLowerCase());
            }
            map.put(memberId, list);
            params.put("permissions", new Gson().toJson(map));
        }
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 添加标签
     *
     * @param fullPath
     * @param tags
     * @return
     */
    public ReturnResult addTag(String fullPath, String[] tags) {
        String url = URL_API_ADD_TAG;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);
        params.put("tag", Util.strArrayToString(tags, ";") + "");
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除标签
     *
     * @param fullPath
     * @param tags
     * @return
     */
    public ReturnResult delTag(String fullPath, String[] tags) {
        String url = URL_API_DEL_TAG;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("org_client_id", mClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);
        params.put("tag", Util.strArrayToString(tags, ";") + "");
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 复制一个EntFileManager对象
     *
     * @return
     */
    public EntFileManager clone() {
        return new EntFileManager(mClientId, mSecret);
    }

    public enum AuthType {
        DEFAULT,
        PREVIEW,
        DOWNLOAD,
        UPLOAD
    }

    public enum NetType {
        DEFAULT,
        IN
    }
}
