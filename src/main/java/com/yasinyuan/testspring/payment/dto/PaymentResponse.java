package com.yasinyuan.testspring.payment.dto;

import lombok.Data;

/**
 * 支付响应DTO
 * @author yinyuan
 */
@Data
public class PaymentResponse {
    /**
     * 支付流水号
     */
    private String paymentNo;
    
    /**
     * 支付状态
     */
    private String status;
    
    /**
     * 支付渠道返回的信息
     */
    private String channelInfo;
    
    /**
     * 二维码URL（用于扫码支付）
     */
    private String qrCodeUrl;
    
    /**
     * 错误信息
     */
    private String errorMsg;
    
    /**
     * 是否成功
     */
    private boolean success;
}
