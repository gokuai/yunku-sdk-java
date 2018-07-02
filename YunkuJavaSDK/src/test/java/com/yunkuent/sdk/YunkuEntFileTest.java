package com.yunkuent.sdk;

import com.gokuai.base.ReturnResult;
import com.yunkuent.sdk.data.FileInfo;
import com.yunkuent.sdk.data.YunkuException;
import com.yunkuent.sdk.upload.UploadCallback;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.util.concurrent.CountDownLatch;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class YunkuEntFileTest {

    public static final String ORG_CLIENT_ID = "";
    public static final String ORG_CLIENT_SECRET = "";
    public static final String TEST_FILE_PATH = "/tmp/test.xlsx";
    public static final String TEST_FILE_FULLLPATH = "test/1.xlsx";
    public static final String TEST_FILE_FOLDER = "test";

    @Test
    public void t001() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.createFolder(TEST_FILE_FOLDER, "tom");
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void t002() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        Assert.assertEquals(true, new File(TEST_FILE_PATH).exists());
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        try {
            FileInfo info = entFile.uploadByBlock(TEST_FILE_FULLLPATH, "tom", 0, TEST_FILE_PATH, true, 10485760);
        } catch (YunkuException e) {
            String msg = "errorMsg:" + e.getMessage();
            if (e.getReturnResult() != null) {
                msg += " body:" + e.getReturnResult().getBody();
            }
            System.out.println(msg);
            Assert.fail();
        }
    }

    @Test
    public void t003() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        final CountDownLatch latch = new CountDownLatch(1);
        Assert.assertEquals(true, new File(TEST_FILE_PATH).exists());
        entFile.uploadByBlockAsync(TEST_FILE_FULLLPATH, "tom", 0, TEST_FILE_PATH, true, 1024, new UploadCallback() {
            @Override
            public void onSuccess(String fullpath, FileInfo file) {
                latch.countDown();
                System.out.println("success:" + file.fullpath);
            }

            @Override
            public void onFail(String fullpath, YunkuException e) {
                String msg = "fail:" + fullpath + " errorMsg:" + e.getMessage();
                if (e.getReturnResult() != null) {
                    msg += " body:" + e.getReturnResult().getBody();
                }
                System.out.println(msg);
                Assert.fail();
            }

            @Override
            public void onProgress(String fullpath, float percent) {
                System.out.println("onProgress:" + fullpath + " " + percent * 100 + "%");
            }
        });
        latch.await();
    }

    @Test
    public void t004() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.getFileInfo(TEST_FILE_FULLLPATH);
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore("getDownloadUrlByHash is ignored")
    @Test
    public void t005() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.getDownloadUrlByHash("", "tom");
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void t006() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.getDownloadUrlByFullpath(TEST_FILE_FULLLPATH, "tom");
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void t007() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.previewUrl(TEST_FILE_FULLLPATH, true, "tom", "tom");
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void t008() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.history(TEST_FILE_FULLLPATH, 0, 100);
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void t009() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.link(TEST_FILE_FULLLPATH, 0, EntFileManager.AuthType.DEFAULT, null, "tom");
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void t010() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.addTag(TEST_FILE_FULLLPATH, new String[]{"test", "test1"}, "tom");
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void t011() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.delTag(TEST_FILE_FULLLPATH, new String[]{"test", "test1"}, "tom");
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void t012() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.search("test", "", 0, 100, ScopeType.FILENAME, ScopeType.TAG, ScopeType.CONTENT);
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore("copy is ignored")
    @Test
    public void t013() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.copy(TEST_FILE_FULLLPATH, "2" + TEST_FILE_FULLLPATH, "tom");
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore("move is ignored")
    @Test
    public void t014() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.move(TEST_FILE_FULLLPATH, "1.xlsx", "tom");
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore("recover is ignored")
    @Test
    public void t015() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.recover(new String[]{TEST_FILE_FULLLPATH}, "tom");
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void t016() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.recycle(0, 10);
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore("setPermission is ignored")
    @Test
    public void t017() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.setPermission(TEST_FILE_FOLDER, 1, "tom", FilePermissions.FILE_PREVIEW, FilePermissions.FILE_DELETE, FilePermissions.FILE_READ);
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore("getPermission is ignored")
    @Test
    public void t018() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.getPermission(TEST_FILE_FOLDER, 1);
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void t100() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.getFileList(0, 100);
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void t200() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.del(new String[]{TEST_FILE_FULLLPATH}, false, "tom");
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void t201() throws Exception {
        EntFileManager entFile = new EntFileManager(ORG_CLIENT_ID, ORG_CLIENT_SECRET);
        ReturnResult r = entFile.delCompletely(new String[]{TEST_FILE_FULLLPATH}, "tom");
        Assert.assertEquals(200, r.getCode());
    }
}