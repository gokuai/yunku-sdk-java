package com.yunkuent.sdk;

import com.google.gson.Gson;
import com.yunkuent.sdk.upload.UploadCallBack;
import com.yunkuent.sdk.utils.Util;

import java.io.*;
import java.util.HashMap;

/**
 * Created by Brandon on 2014/8/14.
 */
public class EntFileManager extends HttpEngine implements HostConfig {

    private static final int UPLOAD_LIMIT_SIZE = 52428800;
    private static final String URL_API_FILELIST = API_ENT_HOST + "/1/file/ls";
    private static final String URL_API_UPDATE_LIST = API_ENT_HOST + "/1/file/updates";
    private static final String URL_API_FILE_INFO = API_ENT_HOST + "/1/file/info";
    private static final String URL_API_CREATE_FOLDER = API_ENT_HOST + "/1/file/create_folder";
    private static final String URL_API_CREATE_FILE = API_ENT_HOST + "/1/file/create_file";
    private static final String URL_API_DEL_FILE = API_ENT_HOST + "/1/file/del";
    private static final String URL_API_MOVE_FILE = API_ENT_HOST + "/1/file/move";
    private static final String URL_API_LINK_FILE = API_ENT_HOST + "/1/file/link";
    private static final String URL_API_SENDMSG = API_ENT_HOST + "/1/file/sendmsg";
    private static final String URL_API_GET_LINK = API_ENT_HOST + "/1/file/links";
    private static final String URL_API_UPDATE_COUNT = API_ENT_HOST + "/1/file/updates_count";
    private static final String URL_API_GET_SERVER_SITE = API_ENT_HOST + "/1/file/servers";
    private static final String URL_API_CREATE_FILE_BY_URL = API_ENT_HOST + "/1/file/create_file_by_url";
    private static final String URL_API_UPLOAD_SERVERS = API_ENT_HOST + "/1/file/upload_servers";
    private static final String URL_API_GET_UPLOAD_URL = API_ENT_HOST + "/1/file/download_url";
    private static final String URL_API_FILE_SEARCH = API_ENT_HOST + "/1/file/search";

    private String mOrgClientId;

    public EntFileManager(String orgClientId, String orgClientSecret) {
        mOrgClientId = orgClientId;
        mClientSecret = orgClientSecret;
    }

    /**
     * 获取根目录文件列表
     *
     * @return
     */
    public String getFileList() {
        return this.getFileList("", 0, 100, false);
    }

    /**
     * 获取文件列表
     *
     * @param fullPath 路径, 空字符串表示根目录
     * @return
     */
    public String getFileList(String fullPath) {
        return this.getFileList(fullPath, 0, 100, false);
    }

    /**
     * 获取文件列表
     *
     * @param fullPath 路径, 空字符串表示根目录
     * @param start    起始下标, 分页显示
     * @param size     返回文件/文件夹数量限制
     * @param dirOnly  只返回文件夹
     * @return
     */
    public String getFileList(String fullPath, int start, int size, boolean dirOnly) {
        String url = URL_API_FILELIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);
        params.put("start", start + "");
        params.put("size", size + "");
        if (dirOnly) {
            params.put("dir", "1");
        }
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 获取更新列表
     *
     * @param isCompare
     * @param fetchDateline
     * @return
     */
    public String getUpdateList(boolean isCompare, long fetchDateline) {
        String url = URL_API_UPDATE_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        if (isCompare) {
            params.put("mode", "compare");
        }
        params.put("fetch_dateline", fetchDateline + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 获取文件信息
     *
     * @param fullPath
     * @param net
     * @return
     */
    public String getFileInfo(String fullPath, NetType net) {
        String url = URL_API_FILE_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);
        switch (net) {
            case DEFAULT:
                break;
            case IN:
                params.put("net", net.name().toLowerCase());
                break;
        }
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 创建文件夹
     *
     * @param fullPath
     * @param opName
     * @return
     */
    public String createFolder(String fullPath, String opName) {
        String url = URL_API_CREATE_FOLDER;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);
        params.put("op_name", opName);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 通过文件流上传 (覆盖同名文件)
     *
     * @param fullPath
     * @param opName
     * @param stream
     * @return
     */
    public String createFile(String fullPath, String opName, FileInputStream stream) {
        return createFile(fullPath, opName, stream, true);
    }

    /**
     * 通过文件流上传
     *
     * @param fullPath
     * @param opName
     * @param stream
     * @return
     */
    public String createFile(String fullPath, String opName, FileInputStream stream, boolean overWrite) {
        try {
            if (stream.available() > UPLOAD_LIMIT_SIZE) {
                LogPrint.print("文件大小超过50MB");
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileName = Util.getNameFromPath(fullPath);

        try {
            long dateline = Util.getUnixDateline();

            HashMap<String, String> params = new HashMap<>();
            params.put("org_client_id", mOrgClientId);
            params.put("dateline", dateline + "");
            params.put("fullpath", fullPath);
            params.put("op_name", opName);
            params.put("overwrite", (overWrite ? 1 : 0) + "");
            params.put("filefield", "file");

            MsMultiPartFormData multipart = new MsMultiPartFormData(URL_API_CREATE_FILE, "UTF-8");
            multipart.addFormField("org_client_id", mOrgClientId);
            multipart.addFormField("dateline", dateline + "");
            multipart.addFormField("fullpath", fullPath);
            multipart.addFormField("op_name", opName);
            multipart.addFormField("overwrite", (overWrite ? 1 : 0) + "");
            multipart.addFormField("filefield", "file");
            multipart.addFormField("sign", generateSign(params));

            multipart.addFilePart("file", stream, fileName);

            return multipart.finish();

        } catch (IOException ex) {
            System.err.println(ex);
        }
        return "";
    }


    /**
     * 文件分块上传 (覆盖同名文件)
     *
     * @param fullPath
     * @param opName
     * @param opId
     * @param localFilePath
     * @param callBack
     * @return
     */
    public UploadRunnable uploadByBlock(String fullPath, String opName, int opId, String localFilePath,
                                        UploadCallBack callBack) {
        return uploadByBlock(fullPath, opName, opId, localFilePath, true, callBack);
    }

    /**
     * 文件分块上传
     *
     * @param fullPath
     * @param opName
     * @param opId
     * @param localFilePath
     * @param overWrite
     * @param callBack
     */
    public UploadRunnable uploadByBlock(String fullPath, String opName, int opId, String localFilePath,
                                        boolean overWrite, UploadCallBack callBack) {
        UploadRunnable uploadRunnable = new UploadRunnable(URL_API_CREATE_FILE, localFilePath, fullPath, opName, opId, mOrgClientId, Util.getUnixDateline(), callBack, mClientSecret, overWrite);
        Thread thread = new Thread(uploadRunnable);
        thread.start();
        return uploadRunnable;
    }


    /**
     * 文件流分块上传 (覆盖同名文件)
     *
     * @param fullPath
     * @param opName
     * @param opId
     * @param inputStream
     * @param callBack
     * @return
     */
    public UploadRunnable uploadByBlock(String fullPath, String opName, int opId, InputStream inputStream,
                                        UploadCallBack callBack) {
        return uploadByBlock(fullPath, opName, opId, inputStream, true, callBack);
    }

    /**
     * 通过文件流分块上传
     *
     * @param fullPath
     * @param opName
     * @param opId
     * @param localFilePath
     * @param overWrite
     * @param callBack
     * @return
     */
    public UploadRunnable uploadByBlock(String fullPath, String opName, int opId, InputStream localFilePath,
                                        boolean overWrite, UploadCallBack callBack) {
        UploadRunnable uploadRunnable = new UploadRunnable(URL_API_CREATE_FILE, localFilePath, fullPath, opName, opId, mOrgClientId, Util.getUnixDateline(), callBack, mClientSecret, overWrite);
        Thread thread = new Thread(uploadRunnable);
        thread.start();
        return uploadRunnable;
    }


    /**
     * 通过本地路径上传 （覆盖同名文件）
     *
     * @param fullPath
     * @param opName
     * @param localPath
     * @return
     */
    public String createFile(String fullPath, String opName, String localPath) {
        return createFile(fullPath, opName, localPath, true);
    }

    /**
     * 通过本地路径上传
     *
     * @param fullPath
     * @param opName
     * @param localPath
     * @param overWrite
     * @return
     */
    public String createFile(String fullPath, String opName, String localPath, boolean overWrite) {
        File file = new File(localPath.trim());
        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                return createFile(fullPath, opName, inputStream, overWrite);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            LogPrint.print("file not exist");
        }

        return "";

    }

    /**
     * 删除文件
     *
     * @param fullPaths
     * @param opName
     * @return
     */
    public String del(String fullPaths, String opName) {
        String url = URL_API_DEL_FILE;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpaths", fullPaths);
        params.put("op_name", opName);
        params.put("sign", generateSign(params));
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
    public String move(String fullPath, String destFullPath, String opName) {
        String url = URL_API_MOVE_FILE;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);
        params.put("dest_fullpath", destFullPath);
        params.put("op_name", opName);
        params.put("sign", generateSign(params));
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
    public String link(String fullPath, int deadline, AuthType authType, String password) {
        String url = URL_API_LINK_FILE;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("fullpath", fullPath);

        if (deadline != 0) {
            params.put("deadline", deadline + "");
        }

        if (!authType.equals(AuthType.DEFAULT)) {
            params.put("auth", authType.toString().toLowerCase());
        }
        params.put("password", password);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }


    /**
     * 发送消息
     *
     * @param title
     * @param text
     * @param image
     * @param linkUrl
     * @param opName
     * @return
     */
    public String sendmsg(String title, String text, String image, String linkUrl, String opName) {
        String url = URL_API_SENDMSG;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("title", title);
        params.put("text", text);
        params.put("image", image);
        params.put("url", linkUrl);
        params.put("op_name", opName);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }


    /**
     * 获取当前库所有外链
     *
     * @return
     */
    public String links(boolean fileOnly) {
        String url = URL_API_GET_LINK;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        if (fileOnly) {
            params.put("file", "1");
        }
        params.put("sign", generateSign(params));
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
    public String getUpdateCounts(long beginDateline, long endDateline, boolean showDelete) {
        String url = URL_API_UPDATE_COUNT;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("begin_dateline", beginDateline + "");
        params.put("end_dateline", endDateline + "");
        params.put("showdel", (showDelete ? 1 : 0) + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }

    /**
     * 通过链接上传文件（覆盖同名文件）
     *
     * @param fullPath
     * @param opId
     * @param opName
     * @param fileUrl
     * @return
     */
    public String createFileByUrl(String fullPath, int opId, String opName, String fileUrl) {
        return createFileByUrl(fullPath, opId, opName, true, fileUrl);
    }

    /**
     * 通过链接上传文件
     *
     * @param fullPath
     * @param opId
     * @param opName
     * @param overwrite
     * @param fileUrl
     * @return
     */
    public String createFileByUrl(String fullPath, int opId, String opName, boolean overwrite, String fileUrl) {
        String url = URL_API_CREATE_FILE_BY_URL;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("fullpath", fullPath);
        params.put("dateline", Util.getUnixDateline() + "");
        if (opId > 0) {
            params.put("op_id", opId + "");
        } else {
            params.put("op_name", opName + "");
        }
        params.put("overwrite", (overwrite ? 1 : 0) + "");
        params.put("url", fileUrl);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
    }

    /**
     * 获取上传地址
     * <p>
     * (支持50MB以上文件的上传)
     *
     * @return
     */
    public String getUploadServers() {
        String url = URL_API_UPLOAD_SERVERS;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }


    /**
     * 获取服务器地址
     *
     * @param type
     * @return
     */
    public String getServerSite(String type) {
        String url = URL_API_GET_SERVER_SITE;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("type", type);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("sign", generateSign(params));
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
    public String search(String keyWords, String path, int start, int size, ScopeType... scopes) {
        String url = URL_API_FILE_SEARCH;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("keywords", keyWords);
        params.put("path", path);
        if (scopes != null) {
            params.put("scope", new Gson().toJson(scopes).toLowerCase());
        }
        params.put("start", start + "");
        params.put("size", size + "");
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
    }


    /**
     * 复制一个EntFileManager对象
     *
     * @return
     */
    public EntFileManager clone() {
        return new EntFileManager(mOrgClientId, mClientSecret);
    }

    /**
     * 通过文件唯一标识获取下载地址
     *
     * @param hash
     * @param isOpen
     * @param net
     * @return
     */
    public String getDownloadUrlByHash(String hash, final boolean isOpen, NetType net) {
        return getDownloadUrl(hash, null, isOpen, net);
    }

    /**
     * 通过文件路径获取下载地址
     *
     * @param fullPath
     * @param isOpen
     * @param net
     * @return
     */
    public String getDownloadUrlByFullPath(String fullPath, final boolean isOpen, NetType net) {
        return getDownloadUrl(null, fullPath, isOpen, net);
    }

    /**
     * 获取下载地址
     *
     * @param hash
     * @param fullPath
     * @param isOpen
     * @param net
     * @return
     */
    private String getDownloadUrl(String hash, String fullPath, final boolean isOpen, NetType net) {
        String url = URL_API_GET_UPLOAD_URL;
        HashMap<String, String> params = new HashMap<>();
        params.put("org_client_id", mOrgClientId);
        params.put("dateline", Util.getUnixDateline() + "");
        params.put("hash", hash);
        params.put("fullpath", fullPath);
        params.put("open", (isOpen ? 1 : 0) + "");
        switch (net) {
            case DEFAULT:
                break;
            case IN:
                params.put("net", net.name().toLowerCase());
                break;
        }
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).executeSync();
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
