package com.yunkuent.sdk.compat.v2;

import com.yunkuent.sdk.data.ReturnResult;
import com.yunkuent.sdk.upload.UploadCallBack;
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

    public static final String TEST_FILE_PATH = "testData/test.jpg";

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
        String s = entFile.getFileInfo("test.jpg", EntFileManager.NetType.DEFAULT);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void createFolder() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.createFolder("test","Brandon");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void createFile() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.createFile("test.jpg","Brandon",TEST_FILE_PATH);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void createFileByStream() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        FileInputStream stream = new FileInputStream(new File(TEST_FILE_PATH));
        String s = entFile.createFile("qq.jpg","Brandon",stream);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Test
    public void uploadByBlock() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        CountDownLatch latch = new CountDownLatch(1);
        Assert.assertEquals(true,new File(TEST_FILE_PATH).exists());
        entFile.uploadByBlock("test.jpg", "Brandon", 0, TEST_FILE_PATH, false, new UploadCallBack() {
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
        String s = entFile.move("qq.jpg","test/qq.jpg","Brandon");
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
        String s = entFile.sendmsg("msgTest", "msg", "", "", "Brandon");
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
    public void createFileByUrl() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.createFileByUrl("qq.jpg", 0,
                "Brandon", true, "http://reso2.yiihuu.com/976162-z.jpg");
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

    @Ignore("getServerSite is ignored")
    @Test
    public void getServerSite() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.getServerSite("");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

    @Ignore("del is ignored")
    @Test
    public void del() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        String s = entFile.del("test.jpg","Brandon");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200,r.getStatusCode());
    }

}