package com.gokuai.yunku.demo.log;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.Log4JConfigHelper;
import com.gokuai.base.LogPrint;

public class PrintWIthLog4JEngine {

    private static final String TAG = "PrintWIthYourLogEngine";


    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;

        LogPrint.info(TAG,"System out log");

        new Log4JConfigHelper().useLog4j(Log4JConfigHelper.PRINT_LOG_IN_FILE);

        LogPrint.info(TAG, "log4j log");
    }
}
