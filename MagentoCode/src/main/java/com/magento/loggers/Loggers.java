package com.magento.loggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * setLogger() to set the Logger with Class Name dynamically
 * getLogger() to return the Logger reference
 */
public class Loggers {
    private static StackTraceElement stackTrace;
    private static Logger log;

    /**
     * @return Logger
     */
    public static Logger getLogger() {
        return log;
    }

    /**
     * Setting the Loggers
     * @param className
     */
    public static void setLogger(String className) {
        stackTrace = new Throwable().getStackTrace()[1];
        log = LogManager.getLogger(stackTrace.getClassName());
    }
}