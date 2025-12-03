package com.yasinyuan.testspring.payment;

import java.math.BigDecimal;

/**
 * 支付请求DTO
 */
public class PayRequest {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 支付渠道
     */
    private String payChannel;

    /**
     * 订单描述
     */
    private String orderDesc;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 回调URL
     */
    private String callbackUrl;

    // 构造方法、getter和setter
    public PayRequest() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @Override
    public String toString() {
        return "PayRequest{" +
                "orderId='" + orderId + "'" +
                ", amount=" + amount +
                ", payChannel='" + payChannel + "'" +
                ", orderDesc='" + orderDesc + "'" +
                ", clientIp='" + clientIp + "'" +
                ", callbackUrl='" + callbackUrl + "'" +
                '}';
    }
}
