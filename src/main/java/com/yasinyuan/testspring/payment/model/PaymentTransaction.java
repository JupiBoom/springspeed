package com.yasinyuan.testspring.payment.model;

import com.yasinyuan.testspring.payment.enums.PaymentChannel;
import com.yasinyuan.testspring.payment.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付交易记录
 */
public class PaymentTransaction {
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 支付流水号
     */
    private String paymentNo;
    
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
     * 支付状态
     */
    private PaymentStatus status;
    
    /**
     * 支付渠道交易号
     */
    private String channelTradeNo;
    
    /**
     * 商品描述
     */
    private String description;
    
    /**
     * 附加信息
     */
    private String extraParams;
    
    /**
     * 超时时间
     */
    private Date timeoutTime;
    
    /**
     * 支付时间
     */
    private Date payTime;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    // getter和setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(String extraParams) {
        this.extraParams = extraParams;
    }

    public Date getTimeoutTime() {
        return timeoutTime;
    }

    public void setTimeoutTime(Date timeoutTime) {
        this.timeoutTime = timeoutTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
