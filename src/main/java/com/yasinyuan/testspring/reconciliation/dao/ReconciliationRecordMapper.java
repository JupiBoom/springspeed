package com.yasinyuan.testspring.reconciliation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.reconciliation.model.ReconciliationRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 对账记录Mapper接口
 */
@Mapper
public interface ReconciliationRecordMapper extends BaseMapper<ReconciliationRecord> {
    
    /**
     * 根据对账流水号查询对账记录
     * @param reconciliationNo 对账流水号
     * @return 对账记录
     */
    ReconciliationRecord selectByReconciliationNo(@Param("reconciliationNo") String reconciliationNo);
    
    /**
     * 根据对账日期和类型查询对账记录
     * @param reconciliationDate 对账日期
     * @param reconciliationType 对账类型
     * @param paymentChannel 支付渠道（可选）
     * @return 对账记录列表
     */
    List<ReconciliationRecord> selectByDateAndType(@Param("reconciliationDate") String reconciliationDate, 
                                                   @Param("reconciliationType") String reconciliationType, 
                                                   @Param("paymentChannel") String paymentChannel);
    
    /**
     * 更新对账状态
     * @param id 对账记录ID
     * @param status 新的对账状态
     * @param endTime 结束时间（可选）
     * @param failReason 失败原因（可选）
     * @return 影响行数
     */
    @Update("UPDATE reconciliation_record SET status = #{status}, end_time = #{endTime}, fail_reason = #{failReason}, update_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status, 
                     @Param("endTime") LocalDateTime endTime, @Param("failReason") String failReason);
    
    /**
     * 更新对账结果
     * @param id 对账记录ID
     * @param reconciliationResult 对账结果
     * @param discrepancyCount 差异笔数
     * @param discrepancyAmount 差异金额
     * @return 影响行数
     */
    @Update("UPDATE reconciliation_record SET reconciliation_result = #{reconciliationResult}, discrepancy_count = #{discrepancyCount}, discrepancy_amount = #{discrepancyAmount}, update_time = NOW() WHERE id = #{id}")
    int updateReconciliationResult(@Param("id") Long id, @Param("reconciliationResult") String reconciliationResult, 
                                   @Param("discrepancyCount") Integer discrepancyCount, @Param("discrepancyAmount") BigDecimal discrepancyAmount);
    
    /**
     * 查询待对账的记录
     * @return 待对账记录列表
     */
    List<ReconciliationRecord> selectPendingReconciliations();
}