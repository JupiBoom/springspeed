package com.yasinyuan.testspring.payment.enums;

/**
 * 支付事件枚举
 * @author yinyuan
 */
public enum PaymentEvent {
    /**
     * 发起支付
     */
    INITIATE_PAYMENT,
    
    /**
     * 支付成功
     */
    PAYMENT_SUCCESS,
    
    /**
     * 支付失败
     */
    PAYMENT_FAILED,
    
    /**
     * 支付超时
     */
    PAYMENT_TIMEOUT,
    
    /**
     * 关闭支付
     */
    CLOSE_PAYMENT
}
