package com.yasinyuan.testspring.settlement;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户资金账户实体类
 */
public class MerchantAccount {

    /**
     * 账户ID
     */
    private Long id;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 可提现余额
     */
    private BigDecimal withdrawableBalance;

    /**
     * 冻结余额
     */
    private BigDecimal frozenBalance;

    /**
     * 账户状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    // 构造方法、getter和setter
    public MerchantAccount() {
    }

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getWithdrawableBalance() {
        return withdrawableBalance;
    }

    public void setWithdrawableBalance(BigDecimal withdrawableBalance) {
        this.withdrawableBalance = withdrawableBalance;
    }

    public BigDecimal getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(BigDecimal frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
