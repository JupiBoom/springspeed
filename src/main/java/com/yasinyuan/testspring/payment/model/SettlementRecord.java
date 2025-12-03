package com.yasinyuan.testspring.payment.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算记录
 */
public class SettlementRecord {
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 结算单号
     */
    private String settlementNo;
    
    /**
     * 商户ID
     */
    private String merchantId;
    
    /**
     * 结算周期开始时间
     */
    private Date cycleStartTime;
    
    /**
     * 结算周期结束时间
     */
    private Date cycleEndTime;
    
    /**
     * 交易总金额
     */
    private BigDecimal totalAmount;
    
    /**
     * 手续费总金额
     */
    private BigDecimal totalFee;
    
    /**
     * 实际结算金额
     */
    private BigDecimal settlementAmount;
    
    /**
     * 交易笔数
     */
    private Integer transactionCount;
    
    /**
     * 结算状态（0:待结算, 1:结算中, 2:结算成功, 3:结算失败）
     */
    private Integer status;
    
    /**
     * 结算时间
     */
    private Date settlementTime;
    
    /**
     * 银行流水号
     */
    private String bankTradeNo;
    
    /**
     * 失败原因
     */
    private String failReason;
    
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

    public String getSettlementNo() {
        return settlementNo;
    }

    public void setSettlementNo(String settlementNo) {
        this.settlementNo = settlementNo;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Date getCycleStartTime() {
        return cycleStartTime;
    }

    public void setCycleStartTime(Date cycleStartTime) {
        this.cycleStartTime = cycleStartTime;
    }

    public Date getCycleEndTime() {
        return cycleEndTime;
    }

    public void setCycleEndTime(Date cycleEndTime) {
        this.cycleEndTime = cycleEndTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(BigDecimal settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public Integer getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(Integer transactionCount) {
        this.transactionCount = transactionCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(Date settlementTime) {
        this.settlementTime = settlementTime;
    }

    public String getBankTradeNo() {
        return bankTradeNo;
    }

    public void setBankTradeNo(String bankTradeNo) {
        this.bankTradeNo = bankTradeNo;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
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
