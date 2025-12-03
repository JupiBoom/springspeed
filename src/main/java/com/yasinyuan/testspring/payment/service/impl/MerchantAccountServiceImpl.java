package com.yasinyuan.testspring.payment.service.impl;

import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.payment.dao.MerchantAccountMapper;
import com.yasinyuan.testspring.payment.model.MerchantAccount;
import com.yasinyuan.testspring.payment.service.MerchantAccountService;
import com.yasinyuan.testspring.tools.exception.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户账户服务实现类
 */
@Service
public class MerchantAccountServiceImpl extends AbstractService<MerchantAccount> implements MerchantAccountService {
    
    @Autowired
    private MerchantAccountMapper merchantAccountMapper;
    
    @Override
    public MerchantAccount getByMerchantId(String merchantId) {
        return merchantAccountMapper.selectByMerchantId(merchantId);
    }
    
    @Override
    @Transactional
    public boolean createMerchantAccount(MerchantAccount merchantAccount) {
        // 检查商户是否已存在
        MerchantAccount existingAccount = merchantAccountMapper.selectByMerchantId(merchantAccount.getMerchantId());
        if (existingAccount != null) {
            throw new RRException("商户账户已存在");
        }
        
        // 设置默认值
        if (merchantAccount.getAvailableBalance() == null) {
            merchantAccount.setAvailableBalance(BigDecimal.ZERO);
        }
        if (merchantAccount.getFrozenBalance() == null) {
            merchantAccount.setFrozenBalance(BigDecimal.ZERO);
        }
        if (merchantAccount.getTotalIncome() == null) {
            merchantAccount.setTotalIncome(BigDecimal.ZERO);
        }
        if (merchantAccount.getTotalExpense() == null) {
            merchantAccount.setTotalExpense(BigDecimal.ZERO);
        }
        if (merchantAccount.getSettlementCycle() == null) {
            merchantAccount.setSettlementCycle(1); // 默认T+1结算
        }
        if (merchantAccount.getFeeRate() == null) {
            merchantAccount.setFeeRate(new BigDecimal("0.006")); // 默认0.6%手续费率
        }
        if (merchantAccount.getStatus() == null) {
            merchantAccount.setStatus(1); // 默认启用
        }
        
        merchantAccount.setCreateTime(new Date());
        merchantAccount.setUpdateTime(new Date());
        
        int result = merchantAccountMapper.insert(merchantAccount);
        return result > 0;
    }
    
    @Override
    @Transactional
    public boolean updateMerchantAccount(MerchantAccount merchantAccount) {
        merchantAccount.setUpdateTime(new Date());
        int result = merchantAccountMapper.updateByPrimaryKeySelective(merchantAccount);
        return result > 0;
    }
    
    @Override
    @Transactional
    public boolean freezeAmount(String merchantId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RRException("冻结金额必须大于0");
        }
        
        MerchantAccount account = merchantAccountMapper.selectByMerchantId(merchantId);
        if (account == null) {
            throw new RRException("商户账户不存在");
        }
        
        if (account.getAvailableBalance().compareTo(amount) < 0) {
            throw new RRException("可用余额不足");
        }
        
        int result = merchantAccountMapper.freezeAmount(merchantId, amount);
        return result > 0;
    }
    
    @Override
    @Transactional
    public boolean unfreezeAmount(String merchantId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RRException("解冻金额必须大于0");
        }
        
        MerchantAccount account = merchantAccountMapper.selectByMerchantId(merchantId);
        if (account == null) {
            throw new RRException("商户账户不存在");
        }
        
        if (account.getFrozenBalance().compareTo(amount) < 0) {
            throw new RRException("冻结余额不足");
        }
        
        int result = merchantAccountMapper.unfreezeAmount(merchantId, amount);
        return result > 0;
    }
    
    @Override
    @Transactional
    public boolean addBalance(String merchantId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RRException("增加金额必须大于0");
        }
        
        MerchantAccount account = merchantAccountMapper.selectByMerchantId(merchantId);
        if (account == null) {
            throw new RRException("商户账户不存在");
        }
        
        int result = merchantAccountMapper.addBalance(merchantId, amount);
        if (result > 0) {
            // 更新累计收入
            account.setTotalIncome(account.getTotalIncome().add(amount));
            merchantAccountMapper.updateByPrimaryKeySelective(account);
        }
        
        return result > 0;
    }
    
    @Override
    @Transactional
    public boolean subtractBalance(String merchantId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RRException("减少金额必须大于0");
        }
        
        MerchantAccount account = merchantAccountMapper.selectByMerchantId(merchantId);
        if (account == null) {
            throw new RRException("商户账户不存在");
        }
        
        if (account.getAvailableBalance().compareTo(amount) < 0) {
            throw new RRException("可用余额不足");
        }
        
        int result = merchantAccountMapper.subtractBalance(merchantId, amount);
        if (result > 0) {
            // 更新累计支出
            account.setTotalExpense(account.getTotalExpense().add(amount));
            merchantAccountMapper.updateByPrimaryKeySelective(account);
        }
        
        return result > 0;
    }
}
