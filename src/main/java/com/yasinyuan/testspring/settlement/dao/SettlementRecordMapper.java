package com.yasinyuan.testspring.settlement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.settlement.model.SettlementRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 结算记录Mapper接口
 */
@Mapper
public interface SettlementRecordMapper extends BaseMapper<SettlementRecord> {
    
    /**
     * 根据结算流水号查询结算记录
     * @param settlementNo 结算流水号
     * @return 结算记录
     */
    SettlementRecord selectBySettlementNo(@Param("settlementNo") String settlementNo);
    
    /**
     * 根据所属方ID查询结算记录
     * @param ownerId 所属方ID
     * @param status 结算状态（可选）
     * @param startDate 开始时间（可选）
     * @param endDate 结束时间（可选）
     * @return 结算记录列表
     */
    List<SettlementRecord> selectByOwnerId(@Param("ownerId") Long ownerId, @Param("status") String status, 
                                           @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    /**
     * 更新结算状态
     * @param id 结算记录ID
     * @param status 新的结算状态
     * @param bankFlowNo 银行流水号（可选）
     * @param failReason 失败原因（可选）
     * @return 影响行数
     */
    @Update("UPDATE settlement_record SET status = #{status}, bank_flow_no = #{bankFlowNo}, fail_reason = #{failReason}, update_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status, 
                     @Param("bankFlowNo") String bankFlowNo, @Param("failReason") String failReason);
    
    /**
     * 查询待结算的记录
     * @return 待结算记录列表
     */
    List<SettlementRecord> selectPendingSettlements();
}