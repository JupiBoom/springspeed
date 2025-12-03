package com.yasinyuan.testspring.payment.service.impl;

import com.yasinyuan.testspring.payment.dto.PaymentRequest;
import com.yasinyuan.testspring.payment.dto.PaymentResponse;
import com.yasinyuan.testspring.payment.enums.PaymentChannel;
import com.yasinyuan.testspring.payment.service.PaymentGatewayService;
import com.yasinyuan.testspring.payment.strategy.PaymentChannelStrategy;
import com.yasinyuan.testspring.payment.strategy.PaymentStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 统一支付网关服务实现
 * @author yinyuan
 */
@Service
public class PaymentGatewayServiceImpl implements PaymentGatewayService {
    
    @Autowired
    private PaymentStrategyFactory strategyFactory;

    @Override
    public PaymentResponse pay(PaymentRequest request) {
        PaymentChannelStrategy strategy = strategyFactory.getStrategy(request.getChannel());
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的支付渠道: " + request.getChannel());
        }
        return strategy.pay(request);
    }

    @Override
    public void handleCallback(PaymentChannel channel, String requestBody) {
        PaymentChannelStrategy strategy = strategyFactory.getStrategy(channel);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的支付渠道: " + channel);
        }
        strategy.handleCallback(requestBody);
    }

    @Override
    public String queryPaymentStatus(String paymentNo, PaymentChannel channel) {
        PaymentChannelStrategy strategy = strategyFactory.getStrategy(channel);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的支付渠道: " + channel);
        }
        return strategy.queryPaymentStatus(paymentNo);
    }

    @Override
    public boolean closePayment(String paymentNo) {
        // TODO: 实现关闭支付逻辑
        return true;
    }
}
