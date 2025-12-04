package com.yasinyuan.testspring.exception;

/**
 * 业务异常类
 * @author yinyuan
 * @since 2024-01-01
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}