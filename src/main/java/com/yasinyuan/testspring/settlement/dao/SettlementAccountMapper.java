package com.yasinyuan.testspring.settlement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.settlement.model.SettlementAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 结算账户Mapper接口
 */
@Mapper
public interface SettlementAccountMapper extends BaseMapper<SettlementAccount> {
    
    /**
     * 增加待结算金额
     * @param accountId 结算账户ID
     * @param amount 增加金额
     * @return 影响行数
     */
    @Update("UPDATE settlement_account SET pending_amount = pending_amount + #{amount}, update_time = NOW() WHERE id = #{accountId}")
    int increasePendingAmount(@Param("accountId") Long accountId, @Param("amount") java.math.BigDecimal amount);
    
    /**
     * 减少待结算金额
     * @param accountId 结算账户ID
     * @param amount 减少金额
     * @return 影响行数
     */
    @Update("UPDATE settlement_account SET pending_amount = pending_amount - #{amount}, update_time = NOW() WHERE id = #{accountId} AND pending_amount >= #{amount}")
    int decreasePendingAmount(@Param("accountId") Long accountId, @Param("amount") java.math.BigDecimal amount);
    
    /**
     * 根据所属方ID和账户类型查询结算账户
     * @param ownerId 所属方ID
     * @param accountType 账户类型
     * @return 结算账户
     */
    SettlementAccount selectByOwnerIdAndType(@Param("ownerId") Long ownerId, @Param("accountType") String accountType);
}