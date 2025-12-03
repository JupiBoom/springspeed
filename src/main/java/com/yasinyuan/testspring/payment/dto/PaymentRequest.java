package com.yasinyuan.testspring.payment.dto;

import com.yasinyuan.testspring.payment.enums.PaymentChannel;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付请求DTO
 */
public class PaymentRequest {
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 支付金额
     */
    private BigDecimal amount;
    
    /**
     * 支付渠道
     */
    private PaymentChannel channel;
    
    /**
     * 商品描述
     */
    private String description;
    
    /**
     * 附加信息
     */
    private Map<String, String> extraParams;
    
    /**
     * 超时时间（分钟）
     */
    private Integer timeoutMinutes;
    
    // getter和setter方法
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentChannel getChannel() {
        return channel;
    }

    public void setChannel(PaymentChannel channel) {
        this.channel = channel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, String> extraParams) {
        this.extraParams = extraParams;
    }

    public Integer getTimeoutMinutes() {
        return timeoutMinutes;
    }

    public void setTimeoutMinutes(Integer timeoutMinutes) {
        this.timeoutMinutes = timeoutMinutes;
    }
}
