package com.yasinyuan.testspring.payment.dao;

import com.yasinyuan.testspring.core.Mapper;
import com.yasinyuan.testspring.payment.model.SettlementRecord;

import java.util.Date;
import java.util.List;

/**
 * 结算记录Mapper
 */
public interface SettlementRecordMapper extends Mapper<SettlementRecord> {
    /**
     * 根据结算单号查询
     * @param settlementNo 结算单号
     * @return 结算记录
     */
    SettlementRecord selectBySettlementNo(String settlementNo);
    
    /**
     * 根据商户ID和状态查询结算记录
     * @param merchantId 商户ID
     * @param status 结算状态
     * @return 结算记录列表
     */
    List<SettlementRecord> selectByMerchantIdAndStatus(String merchantId, Integer status);
    
    /**
     * 查询待结算的记录
     * @param currentTime 当前时间
     * @return 待结算记录列表
     */
    List<SettlementRecord> selectPendingSettlements(Date currentTime);
    
    /**
     * 更新结算状态
     * @param settlementNo 结算单号
     * @param status 结算状态
     * @return 更新结果
     */
    int updateStatusBySettlementNo(String settlementNo, Integer status);
}
