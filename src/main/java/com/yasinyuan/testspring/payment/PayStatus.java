package com.yasinyuan.testspring.payment;

/**
 * 支付状态枚举
 */
public enum PayStatus {
    /**
     * 待支付
     */
    WAITING_PAY,
    
    /**
     * 支付中
     */
    PAYING,
    
    /**
     * 支付成功
     */
    PAY_SUCCESS,
    
    /**
     * 支付失败
     */
    PAY_FAILED,
    
    /**
     * 支付关闭（超时未支付）
     */
    PAY_CLOSED
}
