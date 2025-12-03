package com.yasinyuan.testspring.reconciliation;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 对账记录实体类
 */
public class ReconciliationRecord {

    /**
     * 对账记录ID
     */
    private Long id;

    /**
     * 对账日期
     */
    private Date reconciliationDate;

    /**
     * 支付渠道
     */
    private String payChannel;

    /**
     * 系统交易流水号
     */
    private String systemPayNo;

    /**
     * 渠道交易流水号
     */
    private String channelPayNo;

    /**
     * 系统交易金额
     */
    private BigDecimal systemAmount;

    /**
     * 渠道交易金额
     */
    private BigDecimal channelAmount;

    /**
     * 对账状态
     */
    private String status;

    /**
     * 差异说明
     */
    private String differenceDescription;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    // 构造方法、getter和setter
    public ReconciliationRecord() {
    }

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

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getSystemPayNo() {
        return systemPayNo;
    }

    public void setSystemPayNo(String systemPayNo) {
        this.systemPayNo = systemPayNo;
    }

    public String getChannelPayNo() {
        return channelPayNo;
    }

    public void setChannelPayNo(String channelPayNo) {
        this.channelPayNo = channelPayNo;
    }

    public BigDecimal getSystemAmount() {
        return systemAmount;
    }

    public void setSystemAmount(BigDecimal systemAmount) {
        this.systemAmount = systemAmount;
    }

    public BigDecimal getChannelAmount() {
        return channelAmount;
    }

    public void setChannelAmount(BigDecimal channelAmount) {
        this.channelAmount = channelAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDifferenceDescription() {
        return differenceDescription;
    }

    public void setDifferenceDescription(String differenceDescription) {
        this.differenceDescription = differenceDescription;
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
