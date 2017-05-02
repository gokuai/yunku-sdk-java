package com.gokuai.base;

/**
 * 调试配置
 */
public class DebugConfig {

    public static boolean PRINT_LOG = false;
    public static int PRINT_LOG_TYPE = 1;

    public static final int PRINT_LOG_TO_CONSOLE = 0;//在控制台里写日志

    public static final int PRINT_LOG_IN_FILE = 1;//在控制台里写日志，，并写入文件

    public static final int LOG_TYPE_DETECTOR = 2;//返回监听到的日志内容，自己做打日志处理

    @Deprecated
    public static final int LOG_TYPE_MEMORY_HANDLER = PRINT_LOG_IN_FILE;//在控制台里写日志，并写入文件

    public static String LOG_PATH = "LogPath/";

    public static String TEST_FILE_PATH = "YunkuJavaSDK/testData/test.jpg";


    public static final String LOG_PRINT_PATTERN = "%d{HH:mm:ss.SSS} %-5level YunkuJavaSDK %c - %msg%xEx%n";

    public static String PRINT_LOG_FILE_SIZE = "1MB";

    public interface LogDetector {
        void getLog(String level, String message);
    }

    public static void setListener(LogDetector listener) {
        LogPrint.setLogDetector(listener);
    }

}
