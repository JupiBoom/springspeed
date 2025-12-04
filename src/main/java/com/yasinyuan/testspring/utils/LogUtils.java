package com.yasinyuan.testspring.utils;

import com.yasinyuan.testspring.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 * @author yinyuan
 * @since 2024-01-01
 */
public class LogUtils {
    private static final Logger operationLogger = LoggerFactory.getLogger(Constants.LOG_TYPE_OPERATION);
    private static final Logger exceptionLogger = LoggerFactory.getLogger(Constants.LOG_TYPE_EXCEPTION);

    /**
     * 记录操作日志
     */
    public static void recordOperationLog(String userId, String operationType, String operationContent) {
        operationLogger.info("用户ID: {}, 操作类型: {}, 操作内容: {}", userId, operationType, operationContent);
    }

    /**
     * 记录异常日志
     */
    public static void recordExceptionLog(String userId, String exceptionType, String exceptionContent, Throwable throwable) {
        exceptionLogger.error("用户ID: {}, 异常类型: {}, 异常内容: {}", userId, exceptionType, exceptionContent, throwable);
    }

    /**
     * 记录异常日志
     */
    public static void recordExceptionLog(String exceptionType, String exceptionContent, Throwable throwable) {
        exceptionLogger.error("异常类型: {}, 异常内容: {}", exceptionType, exceptionContent, throwable);
    }

    /**
     * 记录调试日志
     */
    public static void debug(String message) {
        operationLogger.debug(message);
    }

    /**
     * 记录调试日志
     */
    public static void debug(String message, Object... args) {
        operationLogger.debug(message, args);
    }

    /**
     * 记录信息日志
     */
    public static void info(String message) {
        operationLogger.info(message);
    }

    /**
     * 记录信息日志
     */
    public static void info(String message, Object... args) {
        operationLogger.info(message, args);
    }

    /**
     * 记录警告日志
     */
    public static void warn(String message) {
        operationLogger.warn(message);
    }

    /**
     * 记录警告日志
     */
    public static void warn(String message, Object... args) {
        operationLogger.warn(message, args);
    }

    /**
     * 记录错误日志
     */
    public static void error(String message) {
        exceptionLogger.error(message);
    }

    /**
     * 记录错误日志
     */
    public static void error(String message, Object... args) {
        exceptionLogger.error(message, args);
    }

    /**
     * 记录错误日志
     */
    public static void error(String message, Throwable throwable) {
        exceptionLogger.error(message, throwable);
    }
}