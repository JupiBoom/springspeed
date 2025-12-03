package com.yasinyuan.testspring.reconciliation;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 渠道交易记录DTO
 */
public class ChannelTransactionRecord {

    /**
     * 渠道交易流水号
     */
    private String channelPayNo;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 交易时间
     */
    private Date transactionTime;

    /**
     * 交易状态
     */
    private String status;

    /**
     * 手续费
     */
    private BigDecimal serviceFee;

    // 构造方法、getter和setter
    public ChannelTransactionRecord() {
    }

    public String getChannelPayNo() {
        return channelPayNo;
    }

    public void setChannelPayNo(String channelPayNo) {
        this.channelPayNo = channelPayNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }
}
