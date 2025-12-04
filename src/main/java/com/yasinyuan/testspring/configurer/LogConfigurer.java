package com.yasinyuan.testspring.configurer;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

/**
 * 日志配置类
 * @author yinyuan
 * @since 2024-01-01
 */
@Configuration
public class LogConfigurer {

    public LogConfigurer() {
        // 配置日志级别
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO);

        // 配置包级别的日志级别
        Logger daoLogger = loggerContext.getLogger("com.yasinyuan.testspring.dao");
        daoLogger.setLevel(Level.DEBUG);

        Logger serviceLogger = loggerContext.getLogger("com.yasinyuan.testspring.service");
        serviceLogger.setLevel(Level.INFO);

        Logger webLogger = loggerContext.getLogger("com.yasinyuan.testspring.web");
        webLogger.setLevel(Level.INFO);
    }
}