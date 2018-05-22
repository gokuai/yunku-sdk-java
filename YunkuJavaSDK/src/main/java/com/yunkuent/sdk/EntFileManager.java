package com.yunkuent.sdk;

import com.gokuai.base.RequestMethod;
import com.gokuai.base.ReturnResult;
import com.gokuai.base.utils.Util;
import com.google.gson.Gson;
import com.yunkuent.sdk.data.FileInfo;
import com.yunkuent.sdk.data.YunkuException;
import com.yunkuent.sdk.upload.IEntFileManager;
import com.yunkuent.sdk.upload.UploadCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Brandon on 2014/8/14.
 */
public class EntFileManager extends EntEngine implements IEntFileManager {

    private static final String TAG = "EntFileManager";

    protected static String DEFAULT_OPNAME = "";
    protected static int DEFAULT_BLOCKSIZE = 10485760;
    protected static String UPLOAD_ROOT_PATH = "";

    private final String URL_API_FILELIST = HostConfig.API_ENT_HOST + "/1/file/ls";
    private final String URL_API_UPDATE_LIST = HostConfig.API_ENT_HOST + "/1/file/updates";
    private final String URL_API_FILE_INFO = HostConfig.API_ENT_HOST + "/1/file/info";
    private final String URL_API_CREATE_FOLDER = HostConfig.API_ENT_HOST + "/1/file/create_folder";
    private final String URL_API_CREATE_FILE = HostConfig.API_ENT_HOST + "/1/file/create_file";
    private final String URL_API_COPY_FILE = HostConfig.API_ENT_HOST + "/1/file/copy";
    private final String URL_API_MCOPY_FILE = HostConfig.API_ENT_HOST + "/1/file/mcopy";
    private final String URL_API_DEL_FILE = HostConfig.API_ENT_HOST + "/1/file/del";
    private final String URL_API_RECYCLE_FILE = HostConfig.API_ENT_HOST + "/1/file/recycle";
    private final String URL_API_RECOVER_FILE = HostConfig.API_ENT_HOST + "/1/file/recover";
    private final String URL_API_DEL_COMPLETELY_FILE = HostConfig.API_ENT_HOST + "/1/file/del_completely";
    private final String URL_API_MOVE_FILE = HostConfig.API_ENT_HOST + "/1/file/move";
    private final String URL_API_HISTORY_FILE = HostConfig.API_ENT_HOST + "/1/file/history";
    private final String URL_API_LINK_FILE = HostConfig.API_ENT_HOST + "/1/file/link";
    private final String URL_API_GET_LINK = HostConfig.API_ENT_HOST + "/1/file/links";
    private final String URL_API_UPDATE_COUNT = HostConfig.API_ENT_HOST + "/1/file/updates_count";
    private final String URL_API_GET_SERVER_SITE = HostConfig.API_ENT_HOST + "/1/file/servers";
    private final String URL_API_UPLOAD_SERVERS = HostConfig.API_ENT_HOST + "/1/file/upload_servers";
    private final String URL_API_GET_UPLOAD_URL = HostConfig.API_ENT_HOST + "/1/file/download_url";
    private final String URL_API_FILE_SEARCH = HostConfig.API_ENT_HOST + "/1/file/search";
    private final String URL_API_PREVIEW_URL = HostConfig.API_ENT_HOST + "/1/file/preview_url";
    private final String URL_API_GET_PERMISSION = HostConfig.API_ENT_HOST + "/1/file/get_permission";
    private final String URL_API_SET_PERMISSION = HostConfig.API_ENT_HOST + "/1/file/file_permission";
    private final String URL_API_ADD_TAG = HostConfig.API_ENT_HOST + "/1/file/add_tag";
    private final String URL_API_DEL_TAG = HostConfig.API_ENT_HOST + "/1/file/del_tag";

    public EntFileManager(String orgClientId, String secret) {
        super(orgClientId, secret);
        this.clientIdKey = "org_client_id";
    }

    /**
     * 获取根目录文件列表
     *
     * @return
     */
    public ReturnResult getFileList() {
        return this.getFileList("", null, null, 0, 100, false);
    }

    /**
     * 获取文件列表
     *
     * @param fullpath 路径, 空字符串表示根目录
     * @return
     */
    public ReturnResult getFileList(String fullpath) {
        return this.getFileList(fullpath, null, null, 0, 100, false);
    }

    /**
     * 获取文件列表
     *
     * @param fullpath 路径, 空字符串表示根目录
     * @param order    排序
     * @return
     */
    public ReturnResult getFileList(String fullpath, String order) {
        return this.getFileList(fullpath, order, null, 0, 100, false);
    }

    /**
     * 获取文件列表
     *
     * @param fullpath 路径, 空字符串表示根目录
     * @param order    排序
     * @param tag      返回指定标签的文件
     * @param start    起始下标, 分页显示
     * @param size     返回文件/文件夹数量限制
     * @param dirOnly  只返回文件夹    @return
     */
    public ReturnResult getFileList(String fullpath, String order, String tag, int start, int size, boolean dirOnly) {
        String url = URL_API_FILELIST;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullpath", fullpath);
        params.put("tag", tag);
        params.put("start", Integer.toString(start));
        params.put("size", Integer.toString(size));
        params.put("order", order);
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
        if (isCompare) {
            params.put("mode", "compare");
        }
        params.put("fetch_dateline", Long.toString(fetchDateline));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 获取文件信息
     *
     * @param fullpath
     * @param net
     * @return
     */
    public ReturnResult getFileInfo(String fullpath, NetType net, boolean getAttribute) {
        String url = URL_API_FILE_INFO;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullpath", fullpath);
        params.put("attribute", (getAttribute ? "1" : "0"));
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
     * @param fullpath
     * @param opName
     * @return
     */
    public ReturnResult createFolder(String fullpath, String opName) {
        String url = URL_API_CREATE_FOLDER;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullpath", fullpath);
        params.put("op_name", opName);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    public ReturnResult createFile(String fullpath, String fileHash, long fileSize, int opId, String opName, boolean overwrite) {
        String url = URL_API_CREATE_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullpath", fullpath);
        params.put("filehash", fileHash);
        params.put("filesize", Long.toString(fileSize));
        if (opId > 0) {
            params.put("op_id", Integer.toString(opId));
        } else if (opName != null && !opName.isEmpty()) {
            params.put("op_name", opName);
        }
        params.put("overwrite", (overwrite ? "1" : "0"));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 获取实际的上传地址
     *
     * @return
     */
    private String getRealPath(String fullpath) {
        if (Util.isEmpty(UPLOAD_ROOT_PATH)) {
            return fullpath;
        } else {
            return UPLOAD_ROOT_PATH + fullpath;
        }
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
    public FileInfo uploadByBlock(String fullpath, String opName, int opId, String localFile, boolean overwrite, int blockSize) throws YunkuException {
        UploadManager manager = this.initUploadManager(opName, opId, blockSize);
        fullpath = getRealPath(fullpath);
        return manager.upload(localFile, fullpath, overwrite);
    }

    /**
     * 文件分块上传, 默认分块10MB
     *
     * @param fullpath
     * @param opName
     * @param opId
     * @param localFile
     * @param overwrite
     */
    public FileInfo uploadByBlock(String fullpath, String opName, int opId, String localFile, boolean overwrite) throws YunkuException {
        return this.uploadByBlock(fullpath, opName, opId, localFile, overwrite, DEFAULT_BLOCKSIZE);
    }

    /**
     * 文件流分块上传
     *
     * @param fullpath
     * @param opName
     * @param opId
     * @param stream
     * @param overwrite
     * @param blockSize
     */
    public FileInfo uploadByBlock(String fullpath, String opName, int opId, InputStream stream, boolean overwrite, int blockSize) throws YunkuException {
        UploadManager manager = this.initUploadManager(opName, opId, blockSize);
        fullpath = getRealPath(fullpath);
        return manager.upload(stream, fullpath, overwrite);
    }

    /**
     * 文件流分块上传, 默认分块10MB
     *
     * @param fullpath
     * @param opName
     * @param opId
     * @param stream
     * @param overwrite
     */
    public FileInfo uploadByBlock(String fullpath, String opName, int opId, InputStream stream, boolean overwrite) throws YunkuException {
        return this.uploadByBlock(fullpath, opName, opId, stream, overwrite, DEFAULT_BLOCKSIZE);
    }

    /**
     * 文件分块上传, 异步方式, 默认分块10MB
     *
     * @param fullpath
     * @param opName
     * @param opId
     * @param localFile
     * @param overwrite
     * @param callback
     * @return
     */
    public UploadManager uploadByBlockAsync(String fullpath, String opName, int opId, String localFile, boolean overwrite, UploadCallback callback) {
        return uploadByBlockAsync(fullpath, opName, opId, localFile, overwrite, DEFAULT_BLOCKSIZE, callback);
    }

    /**
     * 文件分块上传, 异步方式
     *
     * @param fullpath
     * @param opName
     * @param opId
     * @param localFile
     * @param overwrite
     * @param blockSize
     * @param callback
     */
    public UploadManager uploadByBlockAsync(String fullpath, String opName, int opId, String localFile, boolean overwrite, int blockSize, UploadCallback callback) {
        UploadManager manager = this.initUploadManager(opName, opId, blockSize);
        fullpath = getRealPath(fullpath);
        manager.uploadAsync(localFile, fullpath, overwrite, callback);
        return manager;
    }


    /**
     * 文件流分块上传, 异步方式, 默认分块10MB
     *
     * @param fullpath
     * @param opName
     * @param opId
     * @param stream
     * @param overwrite
     * @param callback
     * @return
     */
    public UploadManager uploadByBlockAsync(String fullpath, String opName, int opId, InputStream stream, boolean overwrite, UploadCallback callback) {
        return uploadByBlockAsync(fullpath, opName, opId, stream, overwrite, DEFAULT_BLOCKSIZE, callback);
    }

    /**
     * 文件流分块上传, 异步方式
     *
     * @param fullpath
     * @param opName
     * @param opId
     * @param stream
     * @param overwrite
     * @param blockSize
     * @param callback
     * @return
     */
    public UploadManager uploadByBlockAsync(String fullpath, String opName, int opId, InputStream stream, boolean overwrite, int blockSize, UploadCallback callback) {
        UploadManager manager = this.initUploadManager(opName, opId, blockSize);
        fullpath = getRealPath(fullpath);
        manager.uploadAsync(stream, fullpath, overwrite, callback);
        return manager;
    }

    /**
     * 复制文件
     *
     * @param originFullpath
     * @param targetFullpath
     * @param opName
     * @return
     */
    public ReturnResult copy(String originFullpath, String targetFullpath, String opName) {
        String url = URL_API_COPY_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("from_fullpath", originFullpath);
        params.put("fullpath", targetFullpath);
        params.put("op_name", opName);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }


    /**
     * 复制文件( 拷贝 tag 以及 opname )
     *
     * @param originFullpath
     * @param targetFullpaths
     * @param sp
     * @return
     */
    public ReturnResult copyAll(String originFullpath, String targetFullpaths, String sp) {
        String url = URL_API_MCOPY_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("from_fullpaths", originFullpath);
        params.put("paths", targetFullpaths);
        params.put("sp", sp);
        params.put("copy_all", "1");
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }


    /**
     * 删除文件
     *
     * @param fullpaths
     * @param opName
     * @return
     */
    public ReturnResult del(String fullpaths, String opName) {
        return del(fullpaths, opName, false);
    }

    /**
     * 删除文件
     *
     * @param fullpaths
     * @param opName
     * @param destroy
     * @return
     */
    public ReturnResult del(String fullpaths, String opName, boolean destroy) {
        String url = URL_API_DEL_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullpaths", fullpaths);
        params.put("destroy", destroy ? "1" : "0");
        params.put("op_name", opName);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }


    /**
     * 根据 tag 删除文件
     *
     * @param tag
     * @param path
     * @param opName @return
     */
    public ReturnResult delByTag(String tag, String path, String opName) {
        return delByTag(tag, path, opName, false);
    }

    /**
     * 根据 tag 删除文件
     *
     * @param tag
     * @param path
     * @param destroy
     * @param opName  @return
     */
    public ReturnResult delByTag(String tag, String path, String opName, boolean destroy) {
        String url = URL_API_DEL_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tag", tag);
        params.put("destroy", destroy ? "1" : "0");
        params.put("path", path);
        params.put("op_name", opName);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 回收站
     *
     * @param start
     * @param size
     * @return
     */
    public ReturnResult recycle(int start, int size) {
        String url = URL_API_RECYCLE_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("start", Integer.toString(start));
        params.put("size", Integer.toString(size));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 恢复删除文件
     *
     * @param fullpaths
     * @param opName
     * @return
     */
    public ReturnResult recover(String fullpaths, String opName) {
        String url = URL_API_RECOVER_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullpaths", fullpaths);
        params.put("op_name", opName);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 彻底删除文件（夹）
     *
     * @param fullpaths
     * @param opName
     * @return
     */
    public ReturnResult delCompletely(String[] fullpaths, String opName) {
        String url = URL_API_DEL_COMPLETELY_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullpaths", Util.strArrayToString(fullpaths, "|"));
        params.put("op_name", opName);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 移动文件
     *
     * @param fullpath
     * @param destfullpath
     * @param opName
     * @return
     */
    public ReturnResult move(String fullpath, String destfullpath, String opName) {
        String url = URL_API_MOVE_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullpath", fullpath);
        params.put("dest_fullpath", destfullpath);
        params.put("op_name", opName);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 获取文件历史
     *
     * @param fullpath
     * @param start
     * @param size
     * @return
     */
    public ReturnResult history(String fullpath, int start, int size) {
        String url = URL_API_HISTORY_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullpath", fullpath);
        params.put("start", Integer.toString(start));
        params.put("size", Integer.toString(size));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 获取文件链接
     *
     * @param fullpath
     * @param deadline
     * @param authType
     * @param password
     * @return
     */
    public ReturnResult link(String fullpath, int deadline, AuthType authType, String password) {
        String url = URL_API_LINK_FILE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullpath", fullpath);

        if (deadline != 0) {
            params.put("deadline", Integer.toString(deadline));
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
        params.put("begin_dateline", Long.toString(beginDateline));
        params.put("end_dateline", Long.toString(endDateline));
        params.put("showdel", (showDelete ? "1" : "0"));
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
        return new RequestHelper().setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }


    /**
     * 获取服务器地址
     *
     * @param type
     * @return
     */
    public ReturnResult getServerSite(String type) {
        String url = URL_API_GET_SERVER_SITE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", type);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 文件搜索
     *
     * @param keyWords
     * @param path
     * @param scopes
     * @param start
     * @param size
     * @return
     */
    public ReturnResult search(String keyWords, String path, int start, int size, ScopeType... scopes) {
        String url = URL_API_FILE_SEARCH;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("keywords", keyWords);
        params.put("path", path);
        if (scopes != null) {
            params.put("scope", new Gson().toJson(scopes).toLowerCase());
        }
        params.put("start", Integer.toString(start));
        params.put("size", Integer.toString(size));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }


    /**
     * 复制一个EntFileManager对象
     *
     * @return
     */
    public EntFileManager clone() {
        return new EntFileManager(mClientId, mSecret);
    }

    /**
     * 通过文件唯一标识获取下载地址
     *
     * @param hash
     * @param isOpen
     * @param net
     * @return
     */
    public ReturnResult getDownloadUrlByHash(String hash, final boolean isOpen, NetType net) {
        return getDownloadUrl(hash, null, isOpen, net, null);
    }

    /**
     * 通过文件路径获取下载地址
     *
     * @param fullpath
     * @param isOpen
     * @param net
     * @return
     */
    public ReturnResult getDownloadUrlByFullpath(String fullpath, final boolean isOpen, NetType net) {
        return getDownloadUrl(null, fullpath, isOpen, net, null);
    }

    /**
     * 获取下载地址
     *
     * @param hash
     * @param fullpath
     * @param isOpen
     * @param net
     * @return
     */
    private ReturnResult getDownloadUrl(String hash, String fullpath, final boolean isOpen, NetType net, String fileName) {
        String url = URL_API_GET_UPLOAD_URL;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("hash", hash);
        params.put("fullpath", fullpath);
        params.put("filename", fileName);
        params.put("open", (isOpen ? "1" : "0"));
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
     * 文件预览地址
     *
     * @param fullpath
     * @param showWaterMark
     * @param memberName
     * @return
     */
    public ReturnResult previewUrl(String fullpath, final boolean showWaterMark, String memberName) {
        String url = URL_API_PREVIEW_URL;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullpath", fullpath);
        params.put("member_name", memberName);
        params.put("watermark", (showWaterMark ? "1" : "0"));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 获取文件夹权限
     *
     * @param fullpath
     * @param memberId
     * @return
     */
    public ReturnResult getPermission(String fullpath, int memberId) {
        String url = URL_API_GET_PERMISSION;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullpath", fullpath);
        params.put("member_id", Integer.toString(memberId));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 修改文件夹权限
     *
     * @param fullpath
     * @param permissions
     * @return
     */
    public ReturnResult setPermission(String fullpath, int memberId, FilePermissions... permissions) {
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
        params.put("fullpath", fullpath);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 添加标签
     *
     * @param fullpath
     * @param tags
     * @return
     */
    public ReturnResult addTag(String fullpath, String[] tags) {
        String url = URL_API_ADD_TAG;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullpath", fullpath);
        params.put("tag", Util.strArrayToString(tags, ";"));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 删除标签
     *
     * @param fullpath
     * @param tags
     * @return
     */
    public ReturnResult delTag(String fullpath, String[] tags) {
        String url = URL_API_DEL_TAG;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullpath", fullpath);
        params.put("tag", Util.strArrayToString(tags, ";"));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
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
