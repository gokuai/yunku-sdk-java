package com.gokuai.base;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 输出日志   System.out.println
 */
public class LogPrint {

    private static final String TAG = "LogPrint";

    public static final String INFO = "info";

    public static final String DEBUG = "debug";

    public static final String ERROR = "error";

    public static final String WARN = "warn";

    /**
     * 示例：
     */
    private static final String LOG_CONTENT_FORMAT = "%s %s\tYunkuJavaSDK %s - %s";

    private static DebugConfig.LogDetector mDetector;

    public static void setLogDetector(DebugConfig.LogDetector detector) {
        mDetector = detector;
    }


    /**
     * 打印 INFO 级别以下的日志
     *
     * @param logTag
     * @param log
     */
    public static void info(String logTag, String log) {
        print(logTag, INFO, log);
    }

    public static void debug(String logTag, String log) {
        print(logTag, DEBUG, log);
    }

    /**
     * 打印 ERROR 级别以下的日志
     *
     * @param logTag
     * @param log
     */
    public static void error(String logTag, String log) {
        print(logTag, ERROR, log);
    }

    /**
     * 打印 WARN 级别以下的日志
     *
     * @param logTag
     * @param log
     */
    public static void warn(String logTag, String log) {
        print(logTag, WARN, log);
    }

    private static void print(String logTag, String level, String log) {
        if (DebugConfig.DEBUG) {

            if (mDetector != null) {
                mDetector.getLog(logTag, level, log);
                return;
            }

            String time = getCurrentTimeStamp();
            String logContent = String.format(LOG_CONTENT_FORMAT, time, level.toUpperCase(), logTag, log);

            if (WARN.equals(level) || ERROR.equals(level)) {
                System.err.print(logContent);

            } else if (INFO.equals(level) || DEBUG.equals(level)) {
                System.out.println(logContent);
            }
        }
    }

    private static String getCurrentTimeStamp() {
        return new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
    }


}
