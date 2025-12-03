package com.yasinyuan.testspring.payment.strategy.impl;

import com.yasinyuan.testspring.payment.dto.PaymentRequest;
import com.yasinyuan.testspring.payment.dto.PaymentResponse;
import com.yasinyuan.testspring.payment.enums.PaymentChannel;
import com.yasinyuan.testspring.payment.strategy.PaymentChannelStrategy;
import com.yasinyuan.testspring.tools.wxmanager.WxPayConfiguration;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import org.springframework.stereotype.Component;

/**
 * 微信支付策略实现
 * @author yinyuan
 */
@Component
public class WechatPayStrategy implements PaymentChannelStrategy {

    @Override
    public PaymentChannel getChannel() {
        return PaymentChannel.WECHAT_PAY;
    }

    @Override
    public PaymentResponse pay(PaymentRequest request) {
        PaymentResponse response = new PaymentResponse();
        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setOutTradeNo(request.getOrderNo());
            orderRequest.setTotalFee(request.getAmount().multiply(new java.math.BigDecimal(100)).intValue());
            orderRequest.setBody(request.getDescription());
            orderRequest.setSpbillCreateIp(request.getClientIp());
            orderRequest.setNotifyUrl(request.getNotifyUrl());
            orderRequest.setTradeType("NATIVE");

            WxPayUnifiedOrderResult result = WxPayConfiguration.getPayService().unifiedOrder(orderRequest);
            
            response.setSuccess(true);
            response.setPaymentNo(result.getTransactionId());
            response.setQrCodeUrl(result.getCodeUrl());
            response.setStatus("PROCESSING");
            response.setChannelInfo("微信支付预下单成功");
            
        } catch (WxPayException e) {
            response.setSuccess(false);
            response.setErrorMsg("微信支付失败: " + e.getMessage());
        }
        return response;
    }

    @Override
    public void handleCallback(String requestBody) {
        try {
            // 验证签名
            WxPayConfiguration.getPayService().parseOrderNotifyResult(requestBody);
            // 处理业务逻辑，更新支付状态等
            System.out.println("微信支付回调处理成功: " + requestBody);
        } catch (WxPayException e) {
            System.err.println("微信支付回调处理失败: " + e.getMessage());
        }
    }

    @Override
    public String queryPaymentStatus(String paymentNo) {
        try {
            return WxPayConfiguration.getPayService().queryOrder(null, paymentNo).getTradeState();
        } catch (WxPayException e) {
            return "UNKNOWN";
        }
    }
}
