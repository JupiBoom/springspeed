package com.yasinyuan.testspring.payment.model;

import com.yasinyuan.testspring.payment.enums.PaymentChannel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 对账记录
 */
public class ReconciliationRecord {
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 对账日期
     */
    private Date reconciliationDate;
    
    /**
     * 支付渠道
     */
    private PaymentChannel channel;
    
    /**
     * 渠道交易总笔数
     */
    private Integer channelTotalCount;
    
    /**
     * 渠道交易总金额
     */
    private BigDecimal channelTotalAmount;
    
    /**
     * 系统交易总笔数
     */
    private Integer systemTotalCount;
    
    /**
     * 系统交易总金额
     */
    private BigDecimal systemTotalAmount;
    
    /**
     * 对账状态（0:待对账, 1:对账中, 2:对账成功, 3:对账失败）
     */
    private Integer status;
    
    /**
     * 差异笔数
     */
    private Integer diffCount;
    
    /**
     * 差异金额
     */
    private BigDecimal diffAmount;
    
    /**
     * 对账单文件路径
     */
    private String billFilePath;
    
    /**
     * 对账结果描述
     */
    private String resultDesc;
    
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

    public Date getReconciliationDate() {
        return reconciliationDate;
    }

    public void setReconciliationDate(Date reconciliationDate) {
        this.reconciliationDate = reconciliationDate;
    }

    public PaymentChannel getChannel() {
        return channel;
    }

    public void setChannel(PaymentChannel channel) {
        this.channel = channel;
    }

    public Integer getChannelTotalCount() {
        return channelTotalCount;
    }

    public void setChannelTotalCount(Integer channelTotalCount) {
        this.channelTotalCount = channelTotalCount;
    }

    public BigDecimal getChannelTotalAmount() {
        return channelTotalAmount;
    }

    public void setChannelTotalAmount(BigDecimal channelTotalAmount) {
        this.channelTotalAmount = channelTotalAmount;
    }

    public Integer getSystemTotalCount() {
        return systemTotalCount;
    }

    public void setSystemTotalCount(Integer systemTotalCount) {
        this.systemTotalCount = systemTotalCount;
    }

    public BigDecimal getSystemTotalAmount() {
        return systemTotalAmount;
    }

    public void setSystemTotalAmount(BigDecimal systemTotalAmount) {
        this.systemTotalAmount = systemTotalAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDiffCount() {
        return diffCount;
    }

    public void setDiffCount(Integer diffCount) {
        this.diffCount = diffCount;
    }

    public BigDecimal getDiffAmount() {
        return diffAmount;
    }

    public void setDiffAmount(BigDecimal diffAmount) {
        this.diffAmount = diffAmount;
    }

    public String getBillFilePath() {
        return billFilePath;
    }

    public void setBillFilePath(String billFilePath) {
        this.billFilePath = billFilePath;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
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
