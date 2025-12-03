package com.yasinyuan.testspring.payment.dao;

import com.yasinyuan.testspring.core.Mapper;
import com.yasinyuan.testspring.payment.model.MerchantAccount;

/**
 * 商户账户Mapper
 */
public interface MerchantAccountMapper extends Mapper<MerchantAccount> {
    /**
     * 根据商户ID查询账户信息
     * @param merchantId 商户ID
     * @return 商户账户信息
     */
    MerchantAccount selectByMerchantId(String merchantId);
    
    /**
     * 冻结商户账户金额
     * @param merchantId 商户ID
     * @param amount 冻结金额
     * @return 更新结果
     */
    int freezeAmount(String merchantId, BigDecimal amount);
    
    /**
     * 解冻商户账户金额
     * @param merchantId 商户ID
     * @param amount 解冻金额
     * @return 更新结果
     */
    int unfreezeAmount(String merchantId, BigDecimal amount);
    
    /**
     * 增加商户账户余额
     * @param merchantId 商户ID
     * @param amount 增加金额
     * @return 更新结果
     */
    int addBalance(String merchantId, BigDecimal amount);
    
    /**
     * 减少商户账户余额
     * @param merchantId 商户ID
     * @param amount 减少金额
     * @return 更新结果
     */
    int subtractBalance(String merchantId, BigDecimal amount);
}
