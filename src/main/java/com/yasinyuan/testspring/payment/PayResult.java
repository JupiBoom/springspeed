package com.yasinyuan.testspring.payment;

/**
 * 支付结果DTO
 */
public class PayResult {

    /**
     * 支付是否成功
     */
    private boolean success;

    /**
     * 支付结果代码
     */
    private String resultCode;

    /**
     * 支付结果信息
     */
    private String resultMsg;

    /**
     * 支付流水号
     */
    private String payNo;

    /**
     * 第三方支付平台返回的支付页面或数据
     */
    private String payData;

    // 构造方法、getter和setter
    public PayResult() {
    }

    public PayResult(boolean success, String resultCode, String resultMsg) {
        this.success = success;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public PayResult(boolean success, String resultCode, String resultMsg, String payNo, String payData) {
        this.success = success;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.payNo = payNo;
        this.payData = payData;
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

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPayData() {
        return payData;
    }

    public void setPayData(String payData) {
        this.payData = payData;
    }
}
