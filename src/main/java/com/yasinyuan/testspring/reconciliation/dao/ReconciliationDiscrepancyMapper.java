package com.yasinyuan.testspring.reconciliation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.reconciliation.model.ReconciliationDiscrepancy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 对账差异记录Mapper接口
 */
@Mapper
public interface ReconciliationDiscrepancyMapper extends BaseMapper<ReconciliationDiscrepancy> {
    
    /**
     * 根据对账流水号查询差异记录
     * @param reconciliationNo 对账流水号
     * @return 差异记录列表
     */
    List<ReconciliationDiscrepancy> selectByReconciliationNo(@Param("reconciliationNo") String reconciliationNo);
    
    /**
     * 根据处理状态查询差异记录
     * @param status 处理状态
     * @return 差异记录列表
     */
    List<ReconciliationDiscrepancy> selectByStatus(@Param("status") String status);
    
    /**
     * 更新差异记录处理状态
     * @param id 差异记录ID
     * @param status 新的处理状态
     * @param handler 处理人
     * @param handleRemark 处理备注
     * @return 影响行数
     */
    @Update("UPDATE reconciliation_discrepancy SET status = #{status}, handler = #{handler}, handle_remark = #{handleRemark}, handle_time = NOW(), update_time = NOW() WHERE id = #{id}")
    int updateHandleStatus(@Param("id") Long id, @Param("status") String status, 
                           @Param("handler") String handler, @Param("handleRemark") String handleRemark);
}