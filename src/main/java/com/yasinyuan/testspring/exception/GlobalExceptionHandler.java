package com.yasinyuan.testspring.exception;

import com.yasinyuan.testspring.tools.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 * @author yinyuan
 * @since 2024-01-01
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public R handleBusinessException(BusinessException e) {
        logger.error("业务异常: {}", e.getMessage());
        return R.error(e.getMessage());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        logger.error("系统异常: {}", e.getMessage(), e);
        return R.error("系统异常，请联系管理员");
    }
}