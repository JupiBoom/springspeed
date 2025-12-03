package com.yasinyuan.testspring.transaction.dao;

import com.yasinyuan.testspring.core.Mapper;
import com.yasinyuan.testspring.transaction.model.TransactionRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 交易记录Mapper
 * @author yinyuan
 */
public interface TransactionRecordMapper extends Mapper<TransactionRecord> {
    
    /**
     * 根据订单号查询交易记录
     */
    TransactionRecord selectByOrderNo(@Param("orderNo") String orderNo);
    
    /**
     * 根据支付流水号查询交易记录
     */
    TransactionRecord selectByPaymentNo(@Param("paymentNo") String paymentNo);
    
    /**
     * 查询超时未支付的交易记录
     */
    List<TransactionRecord> selectTimeoutTransactions(@Param("currentTime") Date currentTime);
    
    /**
     * 更新支付状态
     */
    int updatePaymentStatus(@Param("transactionNo") String transactionNo, @Param("status") String status);
    
    /**
     * 统计指定日期的交易总额
     */
    Long countTotalAmountByDate(@Param("date") String date);
}
