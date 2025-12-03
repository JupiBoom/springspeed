package com.yasinyuan.testspring.payment.service;

import com.yasinyuan.testspring.core.Service;
import com.yasinyuan.testspring.payment.model.SettlementRecord;

import java.util.Date;
import java.util.List;

/**
 * 结算服务接口
 */
public interface SettlementService extends Service<SettlementRecord> {
    /**
     * 创建结算单
     * @param merchantId 商户ID
     * @param cycleStartTime 结算周期开始时间
     * @param cycleEndTime 结算周期结束时间
     * @return 结算记录
     */
    SettlementRecord createSettlement(String merchantId, Date cycleStartTime, Date cycleEndTime);
    
    /**
     * 执行结算
     * @param settlementNo 结算单号
     * @return 执行结果
     */
    boolean executeSettlement(String settlementNo);
    
    /**
     * 批量生成结算单
     * @param date 结算日期
     * @return 生成的结算单数量
     */
    int batchCreateSettlements(Date date);
    
    /**
     * 批量执行结算
     * @return 执行的结算单数量
     */
    int batchExecuteSettlements();
    
    /**
     * 根据商户ID查询结算记录
     * @param merchantId 商户ID
     * @param status 结算状态
     * @param page 页码
     * @param size 每页数量
     * @return 结算记录列表
     */
    List<SettlementRecord> querySettlementsByMerchant(String merchantId, Integer status, int page, int size);
    
    /**
     * 计算手续费
     * @param amount 交易金额
     * @param feeRate 手续费率
     * @return 手续费金额
     */
    BigDecimal calculateFee(BigDecimal amount, BigDecimal feeRate);
}
