package com.yasinyuan.testspring.payment.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户资金账户
 */
public class MerchantAccount {
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 商户ID
     */
    private String merchantId;
    
    /**
     * 可用余额
     */
    private BigDecimal availableBalance;
    
    /**
     * 冻结余额
     */
    private BigDecimal frozenBalance;
    
    /**
     * 累计收入
     */
    private BigDecimal totalIncome;
    
    /**
     * 累计支出
     */
    private BigDecimal totalExpense;
    
    /**
     * 结算周期（T+N）
     */
    private Integer settlementCycle;
    
    /**
     * 手续费率
     */
    private BigDecimal feeRate;
    
    /**
     * 状态（0:禁用, 1:启用）
     */
    private Integer status;
    
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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(BigDecimal frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Integer getSettlementCycle() {
        return settlementCycle;
    }

    public void setSettlementCycle(Integer settlementCycle) {
        this.settlementCycle = settlementCycle;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
