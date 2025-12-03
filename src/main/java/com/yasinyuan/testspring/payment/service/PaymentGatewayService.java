package com.yasinyuan.testspring.payment.service;

import com.yasinyuan.testspring.payment.dto.PaymentRequest;
import com.yasinyuan.testspring.payment.dto.PaymentResponse;
import com.yasinyuan.testspring.payment.enums.PaymentChannel;

/**
 * 统一支付网关服务
 * @author yinyuan
 */
public interface PaymentGatewayService {
    
    /**
     * 统一支付入口
     */
    PaymentResponse pay(PaymentRequest request);
    
    /**
     * 处理支付回调
     */
    void handleCallback(PaymentChannel channel, String requestBody);
    
    /**
     * 查询支付状态
     */
    String queryPaymentStatus(String paymentNo, PaymentChannel channel);
    
    /**
     * 关闭支付
     */
    boolean closePayment(String paymentNo);
}
