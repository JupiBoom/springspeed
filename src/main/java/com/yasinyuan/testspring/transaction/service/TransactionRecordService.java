package com.yasinyuan.testspring.transaction.service;

import com.yasinyuan.testspring.transaction.model.TransactionRecord;

import java.util.List;
import java.util.Map;

/**
 * 交易记录服务
 * @author yinyuan
 */
public interface TransactionRecordService {
    
    /**
     * 创建交易记录
     */
    TransactionRecord createTransaction(TransactionRecord record);
    
    /**
     * 根据交易流水号查询
     */
    TransactionRecord getByTransactionNo(String transactionNo);
    
    /**
     * 根据订单号查询
     */
    TransactionRecord getByOrderNo(String orderNo);
    
    /**
     * 根据支付流水号查询
     */
    TransactionRecord getByPaymentNo(String paymentNo);
    
    /**
     * 更新支付状态
     */
    boolean updatePaymentStatus(String transactionNo, String status);
    
    /**
     * 分页查询交易记录
     */
    List<TransactionRecord> findByPage(Map<String, Object> params, int page, int limit);
    
    /**
     * 查询超时未支付的交易
     */
    List<TransactionRecord> findTimeoutTransactions();
    
    /**
     * 统计交易记录总数
     */
    int count(Map<String, Object> params);
}
