package com.yasinyuan.testspring.settlement.service;

import com.yasinyuan.testspring.settlement.model.SettlementRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 结算服务接口
 * 定义结算相关的业务方法
 */
public interface SettlementService {
    
    /**
     * 创建结算记录
     * @param ownerId 所属方ID
     * @param accountId 结算账户ID
     * @param settlementType 结算类型
     * @param settlementPeriod 结算周期
     * @param totalAmount 结算总金额
     * @param feeAmount 手续费金额
     * @param actualAmount 实际结算金额
     * @return 结算记录
     */
    SettlementRecord createSettlementRecord(Long ownerId, Long accountId, String settlementType, 
                                           String settlementPeriod, BigDecimal totalAmount, 
                                           BigDecimal feeAmount, BigDecimal actualAmount);
    
    /**
     * 根据结算流水号查询结算记录
     * @param settlementNo 结算流水号
     * @return 结算记录
     */
    SettlementRecord getSettlementRecordByNo(String settlementNo);
    
    /**
     * 根据所属方ID查询结算记录
     * @param ownerId 所属方ID
     * @param status 结算状态（可选）
     * @param startDate 开始时间（可选）
     * @param endDate 结束时间（可选）
     * @return 结算记录列表
     */
    List<SettlementRecord> getSettlementRecordsByOwnerId(Long ownerId, String status, 
                                                         LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * 更新结算状态
     * @param settlementNo 结算流水号
     * @param status 新的结算状态
     * @param bankFlowNo 银行流水号（可选）
     * @param failReason 失败原因（可选）
     * @return 更新后的结算记录
     */
    SettlementRecord updateSettlementStatus(String settlementNo, String status, 
                                           String bankFlowNo, String failReason);
    
    /**
     * 发起结算
     * @param settlementNo 结算流水号
     * @return 结算记录
     */
    SettlementRecord initiateSettlement(String settlementNo);
    
    /**
     * 批量结算
     * @param settlementNos 结算流水号列表
     * @return 结算记录列表
     */
    List<SettlementRecord> batchInitiateSettlement(List<String> settlementNos);
    
    /**
     * 自动结算
     * 处理所有符合结算条件的账户
     * @return 结算记录列表
     */
    List<SettlementRecord> autoSettlement();
    
    /**
     * 查询待结算的记录
     * @return 待结算记录列表
     */
    List<SettlementRecord> getPendingSettlements();
}