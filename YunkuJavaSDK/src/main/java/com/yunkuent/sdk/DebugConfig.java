package com.yunkuent.sdk;

import java.util.logging.Level;

/**
 * 调试配置
 */
public class DebugConfig {

    public static boolean PRINT_LOG = false;
    public static int PRINT_LOG_TYPE = 0;
    public static final int LOG_TYPE_MEMORY_HANDLER = 0;//在控制台里写日志，并写入文件
    public static final int LOG_TYPE_DETECTOR = 1;//返回监听到的日志内容，自己做打日志处理
    public static String LOG_PATH = "LogPath/";

    public interface LogDetector {
        void getLog(Level level, String message);
    }

    public static void setListener(LogDetector listener) {
        LogPrint.setLogDetector(listener);
    }

}
