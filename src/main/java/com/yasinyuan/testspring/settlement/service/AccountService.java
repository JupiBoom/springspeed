package com.yasinyuan.testspring.settlement.service;

import com.yasinyuan.testspring.settlement.model.UserAccount;
import com.yasinyuan.testspring.settlement.model.SettlementAccount;

import java.math.BigDecimal;
import java.util.List;

/**
 * 账户服务接口
 * 定义账户管理相关的业务方法
 */
public interface AccountService {
    
    /**
     * 创建用户账户
     * @param userId 用户ID
     * @param accountType 账户类型
     * @return 用户账户
     */
    UserAccount createUserAccount(Long userId, String accountType);
    
    /**
     * 根据用户ID和账户类型查询用户账户
     * @param userId 用户ID
     * @param accountType 账户类型
     * @return 用户账户
     */
    UserAccount getUserAccount(Long userId, String accountType);
    
    /**
     * 增加用户账户余额
     * @param userId 用户ID
     * @param accountType 账户类型
     * @param amount 增加金额
     * @return 更新后的用户账户
     */
    UserAccount increaseUserAccountBalance(Long userId, String accountType, BigDecimal amount);
    
    /**
     * 减少用户账户余额
     * @param userId 用户ID
     * @param accountType 账户类型
     * @param amount 减少金额
     * @return 更新后的用户账户
     */
    UserAccount decreaseUserAccountBalance(Long userId, String accountType, BigDecimal amount);
    
    /**
     * 冻结用户账户金额
     * @param userId 用户ID
     * @param accountType 账户类型
     * @param amount 冻结金额
     * @return 更新后的用户账户
     */
    UserAccount freezeUserAccountAmount(Long userId, String accountType, BigDecimal amount);
    
    /**
     * 解冻用户账户金额
     * @param userId 用户ID
     * @param accountType 账户类型
     * @param amount 解冻金额
     * @return 更新后的用户账户
     */
    UserAccount unfreezeUserAccountAmount(Long userId, String accountType, BigDecimal amount);
    
    /**
     * 创建结算账户
     * @param ownerId 所属方ID
     * @param accountType 账户类型
     * @param accountName 账户名称
     * @param bankName 银行名称
     * @param bankAccount 银行账号
     * @param accountHolder 开户人姓名
     * @param bankCode 开户行联行号
     * @param settlementCycle 结算周期
     * @param settlementThreshold 结算阈值
     * @return 结算账户
     */
    SettlementAccount createSettlementAccount(Long ownerId, String accountType, String accountName, 
                                              String bankName, String bankAccount, String accountHolder, 
                                              String bankCode, String settlementCycle, BigDecimal settlementThreshold);
    
    /**
     * 根据所属方ID和账户类型查询结算账户
     * @param ownerId 所属方ID
     * @param accountType 账户类型
     * @return 结算账户
     */
    SettlementAccount getSettlementAccount(Long ownerId, String accountType);
    
    /**
     * 增加结算账户待结算金额
     * @param accountId 结算账户ID
     * @param amount 增加金额
     * @return 更新后的结算账户
     */
    SettlementAccount increaseSettlementAccountPendingAmount(Long accountId, BigDecimal amount);
    
    /**
     * 减少结算账户待结算金额
     * @param accountId 结算账户ID
     * @param amount 减少金额
     * @return 更新后的结算账户
     */
    SettlementAccount decreaseSettlementAccountPendingAmount(Long accountId, BigDecimal amount);
    
    /**
     * 查询所有符合结算条件的结算账户
     * @return 结算账户列表
     */
    List<SettlementAccount> getSettlementAccountsReadyForSettlement();
}