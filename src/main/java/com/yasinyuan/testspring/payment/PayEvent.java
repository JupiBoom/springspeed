package com.yasinyuan.testspring.payment;

/**
 * 支付事件枚举
 */
public enum PayEvent {
    /**
     * 开始支付
     */
    START_PAY,
    
    /**
     * 支付成功
     */
    PAY_SUCCESS_EVENT,
    
    /**
     * 支付失败
     */
    PAY_FAILED_EVENT,
    
    /**
     * 支付超时
     */
    PAY_TIMEOUT
}
