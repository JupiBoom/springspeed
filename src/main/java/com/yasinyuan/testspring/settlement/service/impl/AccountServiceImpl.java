package com.yasinyuan.testspring.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yasinyuan.testspring.settlement.dao.UserAccountMapper;
import com.yasinyuan.testspring.settlement.dao.SettlementAccountMapper;
import com.yasinyuan.testspring.settlement.model.UserAccount;
import com.yasinyuan.testspring.settlement.model.SettlementAccount;
import com.yasinyuan.testspring.settlement.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 账户服务实现类
 */
@Service
public class AccountServiceImpl implements AccountService {
    
    @Autowired
    private UserAccountMapper userAccountMapper;
    
    @Autowired
    private SettlementAccountMapper settlementAccountMapper;
    
    @Override
    @Transactional
    public UserAccount createUserAccount(Long userId, String accountType) {
        UserAccount account = new UserAccount();
        account.setUserId(userId);
        account.setAccountType(accountType);
        account.setBalance(BigDecimal.ZERO);
        account.setFrozenAmount(BigDecimal.ZERO);
        account.setAvailableBalance(BigDecimal.ZERO);
        account.setStatus("ACTIVE");
        account.setCreateTime(LocalDateTime.now());
        account.setUpdateTime(LocalDateTime.now());
        
        userAccountMapper.insert(account);
        return account;
    }
    
    @Override
    public UserAccount getUserAccount(Long userId, String accountType) {
        QueryWrapper<UserAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                    .eq("account_type", accountType);
        return userAccountMapper.selectOne(queryWrapper);
    }
    
    @Override
    @Transactional
    public UserAccount increaseUserAccountBalance(Long userId, String accountType, BigDecimal amount) {
        int rows = userAccountMapper.increaseBalance(userId, accountType, amount);
        if (rows == 0) {
            throw new RuntimeException("增加账户余额失败: 用户ID=" + userId + ", 账户类型=" + accountType);
        }
        return getUserAccount(userId, accountType);
    }
    
    @Override
    @Transactional
    public UserAccount decreaseUserAccountBalance(Long userId, String accountType, BigDecimal amount) {
        int rows = userAccountMapper.decreaseBalance(userId, accountType, amount);
        if (rows == 0) {
            throw new RuntimeException("减少账户余额失败: 用户ID=" + userId + ", 账户类型=" + accountType + ", 余额不足");
        }
        return getUserAccount(userId, accountType);
    }
    
    @Override
    @Transactional
    public UserAccount freezeUserAccountAmount(Long userId, String accountType, BigDecimal amount) {
        int rows = userAccountMapper.freezeAmount(userId, accountType, amount);
        if (rows == 0) {
            throw new RuntimeException("冻结账户金额失败: 用户ID=" + userId + ", 账户类型=" + accountType + ", 可用余额不足");
        }
        return getUserAccount(userId, accountType);
    }
    
    @Override
    @Transactional
    public UserAccount unfreezeUserAccountAmount(Long userId, String accountType, BigDecimal amount) {
        int rows = userAccountMapper.unfreezeAmount(userId, accountType, amount);
        if (rows == 0) {
            throw new RuntimeException("解冻账户金额失败: 用户ID=" + userId + ", 账户类型=" + accountType + ", 冻结金额不足");
        }
        return getUserAccount(userId, accountType);
    }
    
    @Override
    @Transactional
    public SettlementAccount createSettlementAccount(Long ownerId, String accountType, String accountName, 
                                                      String bankName, String bankAccount, String accountHolder, 
                                                      String bankCode, String settlementCycle, BigDecimal settlementThreshold) {
        SettlementAccount account = new SettlementAccount();
        account.setOwnerId(ownerId);
        account.setAccountType(accountType);
        account.setAccountName(accountName);
        account.setBankName(bankName);
        account.setBankAccount(bankAccount);
        account.setAccountHolder(accountHolder);
        account.setBankCode(bankCode);
        account.setStatus("ACTIVE");
        account.setSettlementCycle(settlementCycle);
        account.setSettlementThreshold(settlementThreshold);
        account.setPendingAmount(BigDecimal.ZERO);
        account.setCreateTime(LocalDateTime.now());
        account.setUpdateTime(LocalDateTime.now());
        
        settlementAccountMapper.insert(account);
        return account;
    }
    
    @Override
    public SettlementAccount getSettlementAccount(Long ownerId, String accountType) {
        return settlementAccountMapper.selectByOwnerIdAndType(ownerId, accountType);
    }
    
    @Override
    @Transactional
    public SettlementAccount increaseSettlementAccountPendingAmount(Long accountId, BigDecimal amount) {
        int rows = settlementAccountMapper.increasePendingAmount(accountId, amount);
        if (rows == 0) {
            throw new RuntimeException("增加待结算金额失败: 结算账户ID=" + accountId);
        }
        return settlementAccountMapper.selectById(accountId);
    }
    
    @Override
    @Transactional
    public SettlementAccount decreaseSettlementAccountPendingAmount(Long accountId, BigDecimal amount) {
        int rows = settlementAccountMapper.decreasePendingAmount(accountId, amount);
        if (rows == 0) {
            throw new RuntimeException("减少待结算金额失败: 结算账户ID=" + accountId + ", 待结算金额不足");
        }
        return settlementAccountMapper.selectById(accountId);
    }
    
    @Override
    public List<SettlementAccount> getSettlementAccountsReadyForSettlement() {
        QueryWrapper<SettlementAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "ACTIVE")
                    .ge("pending_amount", new BigDecimal("0.01")); // 待结算金额大于0
        
        return settlementAccountMapper.selectList(queryWrapper);
    }
}