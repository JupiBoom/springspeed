package com.yasinyuan.testspring.payment.service;

import com.yasinyuan.testspring.core.Service;
import com.yasinyuan.testspring.payment.model.ReconciliationRecord;
import com.yasinyuan.testspring.payment.enums.PaymentChannel;

import java.util.Date;
import java.util.List;

/**
 * 对账服务接口
 */
public interface ReconciliationService extends Service<ReconciliationRecord> {
    /**
     * 下载渠道对账单
     * @param channel 支付渠道
     * @param date 对账日期
     * @return 对账单文件路径
     */
    String downloadChannelBill(PaymentChannel channel, Date date);
    
    /**
     * 生成系统对账单
     * @param channel 支付渠道
     * @param date 对账日期
     * @return 对账单文件路径
     */
    String generateSystemBill(PaymentChannel channel, Date date);
    
    /**
     * 执行对账
     * @param channel 支付渠道
     * @param date 对账日期
     * @return 对账记录
     */
    ReconciliationRecord executeReconciliation(PaymentChannel channel, Date date);
    
    /**
     * 批量执行对账
     * @param date 对账日期
     * @return 对账结果列表
     */
    List<ReconciliationRecord> batchExecuteReconciliation(Date date);
    
    /**
     * 查询对账记录
     * @param channel 支付渠道
     * @param date 对账日期
     * @param status 对账状态
     * @param page 页码
     * @param size 每页数量
     * @return 对账记录列表
     */
    List<ReconciliationRecord> queryReconciliations(PaymentChannel channel, Date date, Integer status, int page, int size);
    
    /**
     * 获取对账差异详情
     * @param reconciliationId 对账记录ID
     * @return 差异详情列表
     */
    List<String> getReconciliationDiff(Long reconciliationId);
}
