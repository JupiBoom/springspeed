package com.yasinyuan.testspring.payment.dao;

import com.yasinyuan.testspring.core.Mapper;
import com.yasinyuan.testspring.payment.model.PaymentTransaction;
import com.yasinyuan.testspring.payment.enums.PaymentStatus;

import java.util.Date;
import java.util.List;

/**
 * 支付交易Mapper
 */
public interface PaymentTransactionMapper extends Mapper<PaymentTransaction> {
    /**
     * 根据支付流水号查询
     * @param paymentNo 支付流水号
     * @return 支付交易记录
     */
    PaymentTransaction selectByPaymentNo(String paymentNo);
    
    /**
     * 根据订单号查询
     * @param orderNo 订单号
     * @return 支付交易记录
     */
    PaymentTransaction selectByOrderNo(String orderNo);
    
    /**
     * 查询超时未支付的订单
     * @param timeoutTime 超时时间
     * @param status 支付状态
     * @return 超时订单列表
     */
    List<PaymentTransaction> selectTimeoutOrders(Date timeoutTime, PaymentStatus status);
    
    /**
     * 更新支付状态
     * @param paymentNo 支付流水号
     * @param status 支付状态
     * @return 更新结果
     */
    int updateStatusByPaymentNo(String paymentNo, PaymentStatus status);
    
    /**
     * 根据条件统计交易数量和金额
     * @param merchantId 商户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param status 支付状态
     * @return 统计结果
     */
    List<PaymentTransaction> selectStatistics(String merchantId, Date startTime, Date endTime, PaymentStatus status);
}
