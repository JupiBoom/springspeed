package com.yasinyuan.testspring.payment.strategy;

import com.yasinyuan.testspring.payment.dto.PaymentRequest;
import com.yasinyuan.testspring.payment.dto.PaymentResponse;
import com.yasinyuan.testspring.payment.enums.PaymentChannel;

/**
 * 支付渠道策略接口
 * @author yinyuan
 */
public interface PaymentChannelStrategy {
    
    /**
     * 获取支付渠道
     */
    PaymentChannel getChannel();
    
    /**
     * 发起支付
     */
    PaymentResponse pay(PaymentRequest request);
    
    /**
     * 处理支付回调
     */
    void handleCallback(String requestBody);
    
    /**
     * 查询支付状态
     */
    String queryPaymentStatus(String paymentNo);
}
