package com.yasinyuan.testspring.transaction;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易记录实体类
 */
public class TransactionRecord {

    /**
     * 交易记录ID
     */
    private Long id;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 支付流水号
     */
    private String payNo;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 支付渠道
     */
    private String payChannel;

    /**
     * 交易状态
     */
    private String status;

    /**
     * 交易时间
     */
    private Date transactionTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    // 构造方法、getter和setter
    public TransactionRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
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
