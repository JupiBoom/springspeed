package com.yasinyuan.testspring.transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 交易服务接口
 */
public interface TransactionService {

    /**
     * 保存交易记录
     * @param transactionRecord 交易记录实体
     * @return 保存是否成功
     */
    boolean saveTransactionRecord(TransactionRecord transactionRecord);

    /**
     * 根据订单ID查询交易记录
     * @param orderId 订单ID
     * @return 交易记录列表
     */
    List<TransactionRecord> queryByOrderId(String orderId);

    /**
     * 根据支付流水号查询交易记录
     * @param payNo 支付流水号
     * @return 交易记录
     */
    TransactionRecord queryByPayNo(String payNo);

    /**
     * 根据时间范围查询交易记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 交易记录列表
     */
    List<TransactionRecord> queryByTimeRange(Date startTime, Date endTime);

    /**
     * 更新交易记录状态
     * @param payNo 支付流水号
     * @param status 交易状态
     * @return 更新是否成功
     */
    boolean updateTransactionStatus(String payNo, String status);

    /**
     * 生成全局唯一的交易流水号
     * @return 交易流水号
     */
    String generateTransactionNo();
}
