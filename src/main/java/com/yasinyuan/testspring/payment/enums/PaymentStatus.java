package com.yasinyuan.testspring.payment.enums;

/**
 * 支付状态枚举
 */
public enum PaymentStatus {
    /**
     * 待支付
     */
    PENDING, 
    
    /**
     * 支付中
     */
    PROCESSING, 
    
    /**
     * 支付成功
     */
    SUCCESS, 
    
    /**
     * 支付失败
     */
    FAILED, 
    
    /**
     * 已关闭
     */
    CLOSED
}
