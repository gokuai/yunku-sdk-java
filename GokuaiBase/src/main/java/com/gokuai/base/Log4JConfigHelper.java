package com.gokuai.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import static com.gokuai.base.LogPrint.*;

public class Log4JConfigHelper {
    private static final String TAG = "LogConfigHelper";

    public static final int PRINT_LOG_TO_CONSOLE = 0;//在控制台里写日志

    public static final int PRINT_LOG_IN_FILE = 1;//在控制台里写日志，，并写入文件

    private static final String LOG_PRINT_PATTERN = "%d{HH:mm:ss.SSS} %-5level YunkuJavaSDK %c - %msg%xEx%n";

    private static String PRINT_LOG_FILE_SIZE = "1MB";


    class Detector implements DebugConfig.LogDetector {

        @Override
        public void getLog(String logTag, String level, String message) {
            Logger logger = LogManager.getLogger(logTag);
            if (INFO.equals(level)) {
                logger.info(message);

            } else if (ERROR.equals(level)) {
                logger.error(message);

            } else if (WARN.equals(level)) {
                logger.warn(message);

            }

        }
    }

    /**
     * 在控制台打印 log
     */
    public void useLog4j() {
        useLog4j(PRINT_LOG_TO_CONSOLE);
    }


    /**
     * 打印log
     * @param printType 0为控制台，1为文件中
     */
    public void useLog4j(int printType) {
        DebugConfig.PRINT_LOG_TYPE = printType;

        if (printType != PRINT_LOG_TO_CONSOLE && printType != PRINT_LOG_IN_FILE) {

            LogPrint.error(TAG, "Error log setting");
        }

        DebugConfig.setListener(new Detector());
        logConfiguration();
        LogPrint.info(TAG, "Log4j handle log now");
    }

    /**
     * 日志打印配置信息
     */
    private static void logConfiguration() {
        if (DebugConfig.PRINT_LOG) {

            ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
            builder.setStatusLevel(org.apache.logging.log4j.Level.INFO);
            builder.setConfigurationName("RollingBuilder");
            AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE")
                    .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
            appenderBuilder.add(builder.newLayout("PatternLayout")
                    .addAttribute("pattern", LOG_PRINT_PATTERN));
            builder.add(appenderBuilder);

            switch (DebugConfig.PRINT_LOG_TYPE) {
                case PRINT_LOG_TO_CONSOLE:
                    builder.add(builder.newLogger("YunKuJavaSDK", org.apache.logging.log4j.Level.INFO)
                            .add(builder.newAppenderRef("Stdout")));
                    builder.add(builder.newRootLogger(org.apache.logging.log4j.Level.INFO)
                            .add(builder.newAppenderRef("Stdout")));
                    Configurator.initialize(builder.build());
                    break;
                case PRINT_LOG_IN_FILE:
                    LayoutComponentBuilder layoutBuilder = builder.newLayout("PatternLayout")
                            .addAttribute("pattern", LOG_PRINT_PATTERN);
                    ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
                            .addComponent(builder.newComponent("CronTriggeringPolicy").addAttribute("schedule", "0 0 0 * * ?"))
                            .addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", PRINT_LOG_FILE_SIZE));
                    ComponentBuilder defaultRolloverStrategy = builder.newComponent("DefaultRolloverStrategy")
                            .addAttribute("fileIndex", "min")
                            .addAttribute("min", 1)
                            .addAttribute("max", 1024);
                    appenderBuilder = builder.newAppender("rolling", "RollingFile")
                            .addAttribute("fileName", DebugConfig.LOG_PATH + "YunkuJavaSDK.log")
                            .addAttribute("filePattern", DebugConfig.LOG_PATH + "YunkuJavaSDK-%d{yyyy-MM}-%i.log")
                            .add(layoutBuilder)
                            .addComponent(triggeringPolicy)
                            .addComponent(defaultRolloverStrategy);
                    builder.add(appenderBuilder);
                    builder.add(builder.newLogger("YunKuJavaSDK", org.apache.logging.log4j.Level.INFO)
                            .add(builder.newAppenderRef("Stdout"))
                            .add(builder.newAppenderRef("rolling"))
                            .addAttribute("additivity", false));
                    builder.add(builder.newRootLogger(org.apache.logging.log4j.Level.INFO)
                            .add(builder.newAppenderRef("Stdout"))
                            .add(builder.newAppenderRef("rolling")));
                    Configurator.initialize(builder.build());
                    break;
            }
        }
    }
}
