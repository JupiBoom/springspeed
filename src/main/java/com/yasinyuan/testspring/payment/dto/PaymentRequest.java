package com.yasinyuan.testspring.payment.dto;

import com.yasinyuan.testspring.payment.enums.PaymentChannel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付请求DTO
 * @author yinyuan
 */
@Data
public class PaymentRequest {
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 支付金额
     */
    private BigDecimal amount;
    
    /**
     * 支付渠道
     */
    private PaymentChannel channel;
    
    /**
     * 商品描述
     */
    private String description;
    
    /**
     * 回调URL
     */
    private String notifyUrl;
    
    /**
     * 客户端IP
     */
    private String clientIp;
}
