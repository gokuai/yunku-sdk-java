package com.yunkuent.sdk;

import java.util.logging.Level;

/**
 * 输出日志   System.out.println
 */
class LogPrint {

    private static YunkuMemoryHandler mHander;

    private synchronized static YunkuMemoryHandler getHandlerInstance() {
        if (mHander == null) {
            mHander = new YunkuMemoryHandler();
        }
        return mHander;
    }

    private static DebugConfig.LogDetector mDectector;

    public static void print(String log) {
        print(Level.INFO, log);
    }

    public static void print(Level level, String log) {
        if (DebugConfig.PRINT_LOG) {
            switch (DebugConfig.PRINT_LOG_TYPE) {
                case DebugConfig.LOG_TYPE_DETECTOR:
                    if (mDectector != null) {
                        mDectector.getLog(level, log);
                    }
                    break;
                case DebugConfig.LOG_TYPE_MEMORY_HANDLER:
                    YunkuMemoryHandler mt = getHandlerInstance();
                    // 在MemoryHandler中缓存日志记录
                    mt.getLogger().log(level, log);
                    mt.getMhandler().push();
                    break;
            }
        }

    }

    public static void setLogDetector(DebugConfig.LogDetector detector) {
        mDectector = detector;
    }


}
