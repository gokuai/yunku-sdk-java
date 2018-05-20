package com.yunkuent.sdk;

import com.gokuai.base.ReturnResult;
import com.yunkuent.sdk.data.FileInfo;
import com.yunkuent.sdk.data.YunkuException;
import com.yunkuent.sdk.upload.UploadCallback;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by qp on 2017/3/8.
 */
public class YunkuEntFileTest {

    public static final String ORG_CLIENT_ID = "";
    public static final String ORG_CLIENT_SECRET = "";

    public static final String TEST_FILE_PATH = "testData/1.xlsx";

    @Test
    public void getFileList() throws Exception{
            EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
            ReturnResult r = entFile.getFileList();
            Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void getUpdateList() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.getUpdateList(false, 0);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void getFileInfo() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.getFileInfo("doc/1.xlsx", EntFileManager.NetType.DEFAULT, false);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void createFolder() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.createFolder("test","");
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void uploadByBlock() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        final CountDownLatch latch = new CountDownLatch(1);
        Assert.assertEquals(true,new File(TEST_FILE_PATH).exists());
        FileInfo info = entFile.uploadByBlock("doc/1.xlsx", "", 0, TEST_FILE_PATH, true, 10485760);
    }

    @Test
    public void uploadByBlockAsync() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        final CountDownLatch latch = new CountDownLatch(1);
        Assert.assertEquals(true,new File(TEST_FILE_PATH).exists());
        entFile.uploadByBlockAsync("doc/1.xlsx", "", 0, TEST_FILE_PATH, true, 10485760, new UploadCallback() {
            @Override
            public void onSuccess(String fullpath, FileInfo file) {
                latch.countDown();
                System.out.println("success:" + file.fullpath);
            }

            @Override
            public void onFail(String fullpath, YunkuException e) {
                Assert.fail();
                String msg = "fail:" + fullpath + " errorMsg:" + e.getMessage();
                if (e.getReturnResult() != null) {
                    msg += " body:" + e.getReturnResult().getBody();
                }
                System.out.println(msg);
            }

            @Override
            public void onProgress(String fullpath, float percent) {
                System.out.println("onProgress:" + fullpath + " " + percent * 100 + "%");
            }
        });
        latch.await();
    }

    @Test
    public void move() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.move("qq.jpg","test/qq.jpg","");
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void link() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.link("qq.jpg", 0, EntFileManager.AuthType.DEFAULT, null);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void links() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.links(true);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void getUpdateCounts() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);//昨天
        Date date = calendar.getTime();
        ReturnResult r = entFile.getUpdateCounts( date.getTime(), System.currentTimeMillis(), false);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void getUploadServers() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.getUploadServers();
        Assert.assertEquals(200,r.getCode());
    }

    @Ignore
    public void getServerSite() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.getServerSite("");
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void search() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.search("tes", "", 0, 100, ScopeType.FILENAME, ScopeType.TAG, ScopeType.CONTENT);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void getDownloadUrlByHash() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.getDownloadUrlByHash("26fc89ba1076ead1946f08759d6afe196716ba10",false,EntFileManager.NetType.DEFAULT);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void getDownloadUrlByFullPath() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.getDownloadUrlByFullpath("qq.jpg",false, EntFileManager.NetType.DEFAULT);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void del() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.del("test.jpg","");
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void AddTag() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.addTag("test", new String[]{"test","test1"});
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void copy() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.copy("qq.jpg", "test/qq.jpg", "");
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void delCompletely() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.delCompletely(new String[]{"test.jpg","test(3).jpg"}, "");
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void delTag() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.delTag("test", new String[]{"test","test1"});
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void getPermission() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.getPermission("test", 216144);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void history() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.history("test", 0, 100);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void previewUrl() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.previewUrl( "test.jpg", false, "");
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void recover() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.recover("qq.jpg", "");
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void recycle() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.recycle(0, 100);
        Assert.assertEquals(200,r.getCode());
    }

    @Test
    public void setPermission() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.setPermission("test", 4,  FilePermissions.FILE_PREVIEW,FilePermissions.FILE_DELETE,FilePermissions.FILE_READ);
        Assert.assertEquals(200,r.getCode());
    }

}