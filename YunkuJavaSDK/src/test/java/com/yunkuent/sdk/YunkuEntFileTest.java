package com.yunkuent.sdk;

import com.gokuai.base.ReturnResult;
import com.yunkuent.sdk.upload.UploadCallBack;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
            String s = entFile.getFileList();
            ReturnResult r = ReturnResult.create(s);
            Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getUpdateList() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.getUpdateList(false, 0);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getFileInfo() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.getFileInfo("doc/1.xlsx", EntFileManager.NetType.DEFAULT, false);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void createFolder() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.createFolder("test","");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void createFile() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.createFile("doc/1.xlsx","",TEST_FILE_PATH,false);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void createFileAndOverWrite() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.createFile("doc/1.xlsx","",TEST_FILE_PATH);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void createFileByStream() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        FileInputStream stream = new FileInputStream(new File(TEST_FILE_PATH));
        String s = entFile.createFile("doc/1.xlsx","",stream,false);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void createFileByStreamAndOverWrite() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        FileInputStream stream = new FileInputStream(new File(TEST_FILE_PATH));
        String s = entFile.createFile("doc/1.xlsx","",stream);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void uploadByBlock() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        final CountDownLatch latch = new CountDownLatch(1);
        Assert.assertEquals(true,new File(TEST_FILE_PATH).exists());
        entFile.uploadByBlock("doc/1.xlsx", "", 0, TEST_FILE_PATH, false, 10485760, new UploadCallBack() {
            @Override
            public void onSuccess(long threadId, String fileHash) {
                latch.countDown();
                Assert.assertEquals(4,threadId);
                System.out.println("success:" + threadId);
            }

            @Override
            public void onFail(long threadId, String errorMsg) {
                Assert.fail();
                System.out.println("fail:" + threadId + " errorMsg:" + errorMsg);
            }

            @Override
            public void onProgress(long threadId, float percent) {
                System.out.println("onProgress:" + threadId + " onProgress:" + percent * 100);
            }
        });
        latch.await();
    }

    @Test
    public void uploadByBlockAndOverWrite() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        final CountDownLatch latch = new CountDownLatch(1);
        Assert.assertEquals(true,new File(TEST_FILE_PATH).exists());
        entFile.uploadByBlock("doc/1.xlsx", "", 0, TEST_FILE_PATH, 10485760, new UploadCallBack() {
            @Override
            public void onSuccess(long threadId, String fileHash) {
                latch.countDown();
                Assert.assertEquals(2,threadId);
                System.out.println("success:" + threadId);
            }

            @Override
            public void onFail(long threadId, String errorMsg) {
                Assert.fail();
                System.out.println("fail:" + threadId + " errorMsg:" + errorMsg);
            }

            @Override
            public void onProgress(long threadId, float percent) {
                System.out.println("onProgress:" + threadId + " onProgress:" + percent * 100);
            }
        });
            latch.await();
    }

    @Test
    public void uploadByStream() throws Exception {
        InputStream inputStream = new FileInputStream(new File(TEST_FILE_PATH));
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        Assert.assertEquals(true,new File(TEST_FILE_PATH).exists());
        final CountDownLatch Latch = new CountDownLatch(1);
        entFile.uploadByBlock("test.jpg", "",0, inputStream, false, 10485760, new UploadCallBack() {
            @Override
            public void onSuccess(long threadId, String fileHash) {
                Latch.countDown();
                Assert.assertEquals(3,threadId);
                System.out.println("success:" + threadId);
            }

            @Override
            public void onFail(long threadId, String errorMsg) {
                Assert.fail();
                System.out.println("fail:" + threadId + " errorMsg:" + errorMsg);
            }

            @Override
            public void onProgress(long threadId, float percent) {
                System.out.println("onProgress:" + threadId + " onProgress:" + percent * 100);
            }
        });
            Latch.await();
    }

    @Test
    public void uploadByStreamAndOverWrite() throws Exception {
        InputStream inputStream = new FileInputStream(new File(TEST_FILE_PATH));
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        final CountDownLatch latch = new CountDownLatch(1);
        Assert.assertEquals(true,new File(TEST_FILE_PATH).exists());
        entFile.uploadByBlock("test.jpg", "",0, inputStream, 10485760, new UploadCallBack() {
            @Override
            public void onSuccess(long threadId, String fileHash) {
                latch.countDown();
                Assert.assertEquals(1,threadId);
                System.out.println("success:" + threadId);
            }

            @Override
            public void onFail(long threadId, String errorMsg) {
                Assert.fail();
                System.out.println("fail:" + threadId + " errorMsg:" + errorMsg);
            }

            @Override
            public void onProgress(long threadId, float percent) {
                System.out.println("onProgress:" + threadId + " onProgress:" + percent * 100);
            }
        });
            latch.await();
    }

    @Test
    public void move() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.move("qq.jpg","test/qq.jpg","");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void link() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.link("qq.jpg", 0, EntFileManager.AuthType.DEFAULT, null);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void sendmsg() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.sendmsg("msgTest", "msg", "", "", "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void links() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.links(true);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getUpdateCounts() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);//昨天
        Date date = calendar.getTime();
        String s = entFile.getUpdateCounts( date.getTime(), System.currentTimeMillis(), false);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void createFileByUrlAndOverWrite() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.createFileByUrl("qq.jpg", 0,
                "", "http://reso2.yiihuu.com/976162-z.jpg");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void createFileByUrl() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.createFileByUrl("qq.jpg", 0,
                "", true, "http://reso2.yiihuu.com/976162-z.jpg");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getUploadServers() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.getUploadServers();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Ignore
    public void getServerSite() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.getServerSite("");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void search() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.search("tes", "", 0, 100, ScopeType.FILENAME, ScopeType.TAG, ScopeType.CONTENT);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getDownloadUrlByHash() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.getDownloadUrlByHash("26fc89ba1076ead1946f08759d6afe196716ba10",false,EntFileManager.NetType.DEFAULT);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getDownloadUrlByFullPath() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.getDownloadUrlByFullPath("qq.jpg",false, EntFileManager.NetType.DEFAULT);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void del() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.del("test.jpg","");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void AddTag() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.addTag("test", new String[]{"test","test1"});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void copy() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.copy("qq.jpg", "test/qq.jpg", "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void delCompletely() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.delCompletely(new String[]{"test.jpg","test(3).jpg"}, "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void delTag() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.delTag("test", new String[]{"test","test1"});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void getPermission() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.getPermission("test", 216144);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void history() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.history("test", 0, 100);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void previewUrl() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.previewUrl( "test.jpg", false, "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void recover() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.recover("qq.jpg", "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void recycle() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.recycle(0, 100);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void setPermission() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.setPermission("test", 4,  FilePermissions.FILE_PREVIEW,FilePermissions.FILE_DELETE,FilePermissions.FILE_READ);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

}