package com.yasinyuan.testspring.payment.service;

import com.yasinyuan.testspring.core.Service;
import com.yasinyuan.testspring.payment.model.MerchantAccount;

import java.math.BigDecimal;

/**
 * 商户账户服务接口
 */
public interface MerchantAccountService extends Service<MerchantAccount> {
    /**
     * 根据商户ID获取账户信息
     * @param merchantId 商户ID
     * @return 商户账户信息
     */
    MerchantAccount getByMerchantId(String merchantId);
    
    /**
     * 创建商户账户
     * @param merchantAccount 商户账户信息
     * @return 创建结果
     */
    boolean createMerchantAccount(MerchantAccount merchantAccount);
    
    /**
     * 更新商户账户信息
     * @param merchantAccount 商户账户信息
     * @return 更新结果
     */
    boolean updateMerchantAccount(MerchantAccount merchantAccount);
    
    /**
     * 冻结商户账户金额
     * @param merchantId 商户ID
     * @param amount 冻结金额
     * @return 操作结果
     */
    boolean freezeAmount(String merchantId, BigDecimal amount);
    
    /**
     * 解冻商户账户金额
     * @param merchantId 商户ID
     * @param amount 解冻金额
     * @return 操作结果
     */
    boolean unfreezeAmount(String merchantId, BigDecimal amount);
    
    /**
     * 增加商户账户余额
     * @param merchantId 商户ID
     * @param amount 增加金额
     * @return 操作结果
     */
    boolean addBalance(String merchantId, BigDecimal amount);
    
    /**
     * 减少商户账户余额
     * @param merchantId 商户ID
     * @param amount 减少金额
     * @return 操作结果
     */
    boolean subtractBalance(String merchantId, BigDecimal amount);
}
