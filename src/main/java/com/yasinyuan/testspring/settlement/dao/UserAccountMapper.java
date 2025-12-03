package com.yasinyuan.testspring.settlement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.settlement.model.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 用户账户Mapper接口
 */
@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccount> {
    
    /**
     * 增加账户余额
     * @param userId 用户ID
     * @param accountType 账户类型
     * @param amount 增加金额
     * @return 影响行数
     */
    @Update("UPDATE user_account SET balance = balance + #{amount}, available_balance = available_balance + #{amount}, update_time = NOW() WHERE user_id = #{userId} AND account_type = #{accountType}")
    int increaseBalance(@Param("userId") Long userId, @Param("accountType") String accountType, @Param("amount") java.math.BigDecimal amount);
    
    /**
     * 减少账户余额
     * @param userId 用户ID
     * @param accountType 账户类型
     * @param amount 减少金额
     * @return 影响行数
     */
    @Update("UPDATE user_account SET balance = balance - #{amount}, available_balance = available_balance - #{amount}, update_time = NOW() WHERE user_id = #{userId} AND account_type = #{accountType} AND available_balance >= #{amount}")
    int decreaseBalance(@Param("userId") Long userId, @Param("accountType") String accountType, @Param("amount") java.math.BigDecimal amount);
    
    /**
     * 冻结金额
     * @param userId 用户ID
     * @param accountType 账户类型
     * @param amount 冻结金额
     * @return 影响行数
     */
    @Update("UPDATE user_account SET frozen_amount = frozen_amount + #{amount}, available_balance = available_balance - #{amount}, update_time = NOW() WHERE user_id = #{userId} AND account_type = #{accountType} AND available_balance >= #{amount}")
    int freezeAmount(@Param("userId") Long userId, @Param("accountType") String accountType, @Param("amount") java.math.BigDecimal amount);
    
    /**
     * 解冻金额
     * @param userId 用户ID
     * @param accountType 账户类型
     * @param amount 解冻金额
     * @return 影响行数
     */
    @Update("UPDATE user_account SET frozen_amount = frozen_amount - #{amount}, available_balance = available_balance + #{amount}, update_time = NOW() WHERE user_id = #{userId} AND account_type = #{accountType} AND frozen_amount >= #{amount}")
    int unfreezeAmount(@Param("userId") Long userId, @Param("accountType") String accountType, @Param("amount") java.math.BigDecimal amount);
}