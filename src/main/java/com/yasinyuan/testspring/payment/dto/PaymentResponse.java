package com.yasinyuan.testspring.payment.dto;

import com.yasinyuan.testspring.payment.enums.PaymentStatus;

import java.util.Map;

/**
 * 支付响应DTO
 */
public class PaymentResponse {
    /**
     * 支付流水号
     */
    private String paymentNo;
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 支付状态
     */
    private PaymentStatus status;
    
    /**
     * 支付渠道返回的交易号
     */
    private String channelTradeNo;
    
    /**
     * 支付渠道返回的响应数据
     */
    private Map<String, Object> channelResponse;
    
    /**
     * 错误信息
     */
    private String errorMessage;
    
    // getter和setter方法
    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getChannelTradeNo() {
        return channelTradeNo;
    }

    public void setChannelTradeNo(String channelTradeNo) {
        this.channelTradeNo = channelTradeNo;
    }

    public Map<String, Object> getChannelResponse() {
        return channelResponse;
    }

    public void setChannelResponse(Map<String, Object> channelResponse) {
        this.channelResponse = channelResponse;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
