package com.gokuai.yunku.demo.log;

import com.gokuai.base.DebugConfig;

public class PrintWithLogWithYourEngine {

    public static class YourDetector implements DebugConfig.LogDetector {

        @Override
        public void getLog(String logtag, String level, String message) {
            // Add you log printer here
        }
    }

    public static void main(String[] args) {

        DebugConfig.DEBUG = true;

        DebugConfig.setListener(new YourDetector());

    }
}
