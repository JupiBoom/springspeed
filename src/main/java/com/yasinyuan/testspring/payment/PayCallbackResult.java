package com.yasinyuan.testspring.payment;

/**
 * 支付回调结果DTO
 */
public class PayCallbackResult {

    /**
     * 回调处理是否成功
     */
    private boolean success;

    /**
     * 处理结果代码
     */
    private String resultCode;

    /**
     * 处理结果信息
     */
    private String resultMsg;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 支付流水号
     */
    private String payNo;

    // 构造方法、getter和setter
    public PayCallbackResult() {
    }

    public PayCallbackResult(boolean success, String resultCode, String resultMsg) {
        this.success = success;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public PayCallbackResult(boolean success, String resultCode, String resultMsg, String orderId, String payNo) {
        this.success = success;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.orderId = orderId;
        this.payNo = payNo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
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
}
