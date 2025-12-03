package com.yasinyuan.testspring.payment.dao;

import com.yasinyuan.testspring.core.Mapper;
import com.yasinyuan.testspring.payment.model.ReconciliationRecord;
import com.yasinyuan.testspring.payment.enums.PaymentChannel;

import java.util.Date;
import java.util.List;

/**
 * 对账记录Mapper
 */
public interface ReconciliationRecordMapper extends Mapper<ReconciliationRecord> {
    /**
     * 根据日期和渠道查询对账记录
     * @param reconciliationDate 对账日期
     * @param channel 支付渠道
     * @return 对账记录
     */
    ReconciliationRecord selectByDateAndChannel(Date reconciliationDate, PaymentChannel channel);
    
    /**
     * 查询待对账的记录
     * @return 待对账记录列表
     */
    List<ReconciliationRecord> selectPendingReconciliations();
    
    /**
     * 更新对账状态
     * @param id 记录ID
     * @param status 对账状态
     * @return 更新结果
     */
    int updateStatusById(Long id, Integer status);
}
