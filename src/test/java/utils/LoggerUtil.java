package com.mobile.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * LoggerUtil - Classe para logging centralizado
 */
public class LoggerUtil {
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

    public static void info(String message) {
        logger.info(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static void fatal(String message) {
        logger.fatal(message);
    }

    public static void passStep(String message) {
        logger.info("✓ PASS: " + message);
    }

    public static void failStep(String message) {
        logger.error("✗ FAIL: " + message);
    }

    public static void testInfo(String testName, String message) {
        logger.info("[" + testName + "] " + message);
    }
}

