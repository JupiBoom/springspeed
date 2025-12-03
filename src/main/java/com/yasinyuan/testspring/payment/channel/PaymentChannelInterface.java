package com.yasinyuan.testspring.payment.channel;

import com.yasinyuan.testspring.payment.dto.PaymentRequest;
import com.yasinyuan.testspring.payment.dto.PaymentResponse;

import java.util.Map;

/**
 * 支付渠道接口
 */
public interface PaymentChannelInterface {
    /**
     * 统一支付接口
     * @param request 支付请求
     * @return 支付响应
     */
    PaymentResponse pay(PaymentRequest request);
    
    /**
     * 查询支付状态
     * @param paymentNo 支付流水号
     * @return 支付响应
     */
    PaymentResponse queryPaymentStatus(String paymentNo);
    
    /**
     * 关闭支付订单
     * @param paymentNo 支付流水号
     * @return 支付响应
     */
    PaymentResponse closePayment(String paymentNo);
    
    /**
     * 处理支付回调
     * @param params 回调参数
     * @return 处理结果
     */
    Map<String, Object> handleCallback(Map<String, String> params);
    
    /**
     * 下载对账单
     * @param date 日期（格式：yyyyMMdd）
     * @return 对账单数据
     */
    String downloadBill(String date);
}
