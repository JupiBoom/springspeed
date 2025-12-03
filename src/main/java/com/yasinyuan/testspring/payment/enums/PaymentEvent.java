package com.yasinyuan.testspring.payment.enums;

/**
 * 支付事件枚举
 */
public enum PaymentEvent {
    /**
     * 开始支付
     */
    START_PAYMENT, 
    
    /**
     * 支付成功
     */
    PAY_SUCCESS, 
    
    /**
     * 支付失败
     */
    PAY_FAILED, 
    
    /**
     * 关闭支付
     */
    CLOSE_PAYMENT
}
