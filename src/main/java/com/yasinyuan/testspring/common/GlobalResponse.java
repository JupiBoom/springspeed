package com.yasinyuan.testspring.common;

import java.io.Serializable;

/**
 * 全局响应类
 * 统一API响应格式
 */
public class GlobalResponse<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int code;
    private String message;
    private T data;
    private long timestamp;
    
    public GlobalResponse() {
        this.timestamp = System.currentTimeMillis();
    }
    
    public GlobalResponse(int code, String message) {
        this();
        this.code = code;
        this.message = message;
    }
    
    public GlobalResponse(int code, String message, T data) {
        this(code, message);
        this.data = data;
    }
    
    public static <T> GlobalResponse<T> success(T data) {
        return new GlobalResponse<>(200, "success", data);
    }
    
    public static <T> GlobalResponse<T> success(String message, T data) {
        return new GlobalResponse<>(200, message, data);
    }
    
    public static <T> GlobalResponse<T> success() {
        return new GlobalResponse<>(200, "success");
    }
    
    public static <T> GlobalResponse<T> error(int code, String message) {
        return new GlobalResponse<>(code, message);
    }
    
    public static <T> GlobalResponse<T> error(String message) {
        return new GlobalResponse<>(500, message);
    }
    
    // Getters and Setters
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}