package com.yasinyuan.testspring.reconciliation;

import java.util.List;

/**
 * 对账结果DTO
 */
public class ReconciliationResult {

    /**
     * 对账是否成功
     */
    private boolean success;

    /**
     * 系统交易总笔数
     */
    private int systemTotalCount;

    /**
     * 系统交易总金额
     */
    private double systemTotalAmount;

    /**
     * 渠道交易总笔数
     */
    private int channelTotalCount;

    /**
     * 渠道交易总金额
     */
    private double channelTotalAmount;

    /**
     * 匹配成功笔数
     */
    private int matchedCount;

    /**
     * 匹配成功金额
     */
    private double matchedAmount;

    /**
     * 系统单边账笔数
     */
    private int systemUnilateralCount;

    /**
     * 系统单边账金额
     */
    private double systemUnilateralAmount;

    /**
     * 渠道单边账笔数
     */
    private int channelUnilateralCount;

    /**
     * 渠道单边账金额
     */
    private double channelUnilateralAmount;

    /**
     * 金额不匹配笔数
     */
    private int amountMismatchCount;

    /**
     * 对账记录列表
     */
    private List<ReconciliationRecord> reconciliationRecords;

    // 构造方法、getter和setter
    public ReconciliationResult() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getSystemTotalCount() {
        return systemTotalCount;
    }

    public void setSystemTotalCount(int systemTotalCount) {
        this.systemTotalCount = systemTotalCount;
    }

    public double getSystemTotalAmount() {
        return systemTotalAmount;
    }

    public void setSystemTotalAmount(double systemTotalAmount) {
        this.systemTotalAmount = systemTotalAmount;
    }

    public int getChannelTotalCount() {
        return channelTotalCount;
    }

    public void setChannelTotalCount(int channelTotalCount) {
        this.channelTotalCount = channelTotalCount;
    }

    public double getChannelTotalAmount() {
        return channelTotalAmount;
    }

    public void setChannelTotalAmount(double channelTotalAmount) {
        this.channelTotalAmount = channelTotalAmount;
    }

    public int getMatchedCount() {
        return matchedCount;
    }

    public void setMatchedCount(int matchedCount) {
        this.matchedCount = matchedCount;
    }

    public double getMatchedAmount() {
        return matchedAmount;
    }

    public void setMatchedAmount(double matchedAmount) {
        this.matchedAmount = matchedAmount;
    }

    public int getSystemUnilateralCount() {
        return systemUnilateralCount;
    }

    public void setSystemUnilateralCount(int systemUnilateralCount) {
        this.systemUnilateralCount = systemUnilateralCount;
    }

    public double getSystemUnilateralAmount() {
        return systemUnilateralAmount;
    }

    public void setSystemUnilateralAmount(double systemUnilateralAmount) {
        this.systemUnilateralAmount = systemUnilateralAmount;
    }

    public int getChannelUnilateralCount() {
        return channelUnilateralCount;
    }

    public void setChannelUnilateralCount(int channelUnilateralCount) {
        this.channelUnilateralCount = channelUnilateralCount;
    }

    public double getChannelUnilateralAmount() {
        return channelUnilateralAmount;
    }

    public void setChannelUnilateralAmount(double channelUnilateralAmount) {
        this.channelUnilateralAmount = channelUnilateralAmount;
    }

    public int getAmountMismatchCount() {
        return amountMismatchCount;
    }

    public void setAmountMismatchCount(int amountMismatchCount) {
        this.amountMismatchCount = amountMismatchCount;
    }

    public List<ReconciliationRecord> getReconciliationRecords() {
        return reconciliationRecords;
    }

    public void setReconciliationRecords(List<ReconciliationRecord> reconciliationRecords) {
        this.reconciliationRecords = reconciliationRecords;
    }
}
