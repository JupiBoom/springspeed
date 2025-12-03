package com.yasinyuan.testspring.payment.service;

import com.yasinyuan.testspring.core.Service;
import com.yasinyuan.testspring.payment.dto.PaymentRequest;
import com.yasinyuan.testspring.payment.dto.PaymentResponse;
import com.yasinyuan.testspring.payment.model.PaymentTransaction;
import com.yasinyuan.testspring.payment.enums.PaymentStatus;

import java.util.List;
import java.util.Map;

/**
 * 支付服务接口
 */
public interface PaymentService extends Service<PaymentTransaction> {
    /**
     * 创建支付订单
     * @param request 支付请求
     * @return 支付响应
     */
    PaymentResponse createPayment(PaymentRequest request);
    
    /**
     * 执行支付
     * @param paymentNo 支付流水号
     * @return 支付响应
     */
    PaymentResponse executePayment(String paymentNo);
    
    /**
     * 查询支付状态
     * @param paymentNo 支付流水号
     * @return 支付交易记录
     */
    PaymentTransaction queryPaymentStatus(String paymentNo);
    
    /**
     * 关闭支付订单
     * @param paymentNo 支付流水号
     * @return 操作结果
     */
    boolean closePayment(String paymentNo);
    
    /**
     * 处理支付回调
     * @param channel 支付渠道
     * @param params 回调参数
     * @return 处理结果
     */
    Map<String, Object> handlePaymentCallback(String channel, Map<String, String> params);
    
    /**
     * 处理超时订单
     * @return 处理的订单数量
     */
    int processTimeoutOrders();
    
    /**
     * 根据条件查询支付记录
     * @param orderNo 订单号
     * @param status 支付状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 支付记录列表
     */
    List<PaymentTransaction> queryPayments(String orderNo, PaymentStatus status, String startTime, String endTime);
}
