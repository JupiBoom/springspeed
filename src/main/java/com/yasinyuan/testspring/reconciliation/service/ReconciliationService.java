package com.yasinyuan.testspring.reconciliation.service;

import com.yasinyuan.testspring.reconciliation.model.ReconciliationRecord;
import com.yasinyuan.testspring.reconciliation.model.ReconciliationDiscrepancy;

import java.time.LocalDate;
import java.util.List;

/**
 * 对账服务接口
 * 定义对账相关的业务方法
 */
public interface ReconciliationService {
    
    /**
     * 创建对账任务
     * @param reconciliationDate 对账日期
     * @param reconciliationType 对账类型
     * @param paymentChannel 支付渠道（可选，默认ALL）
     * @return 对账记录
     */
    ReconciliationRecord createReconciliationTask(String reconciliationDate, String reconciliationType, String paymentChannel);
    
    /**
     * 根据对账流水号查询对账记录
     * @param reconciliationNo 对账流水号
     * @return 对账记录
     */
    ReconciliationRecord getReconciliationRecordByNo(String reconciliationNo);
    
    /**
     * 根据日期和类型查询对账记录
     * @param reconciliationDate 对账日期
     * @param reconciliationType 对账类型
     * @param paymentChannel 支付渠道（可选）
     * @return 对账记录列表
     */
    List<ReconciliationRecord> getReconciliationRecordsByDateAndType(String reconciliationDate, String reconciliationType, String paymentChannel);
    
    /**
     * 发起对账
     * @param reconciliationNo 对账流水号
     * @return 对账记录
     */
    ReconciliationRecord initiateReconciliation(String reconciliationNo);
    
    /**
     * 批量发起对账
     * @param reconciliationNos 对账流水号列表
     * @return 对账记录列表
     */
    List<ReconciliationRecord> batchInitiateReconciliation(List<String> reconciliationNos);
    
    /**
     * 自动对账
     * 处理指定日期的所有对账任务
     * @param reconciliationDate 对账日期（可选，默认昨天）
     * @return 对账记录列表
     */
    List<ReconciliationRecord> autoReconciliation(String reconciliationDate);
    
    /**
     * 查询待对账的任务
     * @return 待对账任务列表
     */
    List<ReconciliationRecord> getPendingReconciliations();
    
    /**
     * 根据对账流水号查询差异记录
     * @param reconciliationNo 对账流水号
     * @return 差异记录列表
     */
    List<ReconciliationDiscrepancy> getDiscrepanciesByReconciliationNo(String reconciliationNo);
    
    /**
     * 根据处理状态查询差异记录
     * @param status 处理状态
     * @return 差异记录列表
     */
    List<ReconciliationDiscrepancy> getDiscrepanciesByStatus(String status);
    
    /**
     * 处理差异记录
     * @param discrepancyId 差异记录ID
     * @param status 处理状态
     * @param handler 处理人
     * @param handleRemark 处理备注
     * @return 差异记录
     */
    ReconciliationDiscrepancy handleDiscrepancy(Long discrepancyId, String status, String handler, String handleRemark);
}