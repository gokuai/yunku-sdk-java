package com.gokuai.base;

/**
 * 调试配置
 */
public class DebugConfig {

    public static boolean PRINT_LOG = false;

    public static String LOG_PATH = "LogPath/";


    static int PRINT_LOG_TYPE = -1;

    public interface LogDetector {
        void getLog(String logtag, String level, String message);
    }

    public static void setListener(LogDetector listener) {
        LogPrint.setLogDetector(listener);
    }

}
