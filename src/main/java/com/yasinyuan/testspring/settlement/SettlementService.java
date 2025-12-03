package com.yasinyuan.testspring.settlement;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算服务接口
 */
public interface SettlementService {

    /**
     * 根据商户ID查询商户资金账户
     * @param merchantId 商户ID
     * @return 商户资金账户
     */
    MerchantAccount queryMerchantAccount(String merchantId);

    /**
     * 增加商户账户余额
     * @param merchantId 商户ID
     * @param amount 增加金额
     * @return 增加是否成功
     */
    boolean increaseMerchantBalance(String merchantId, BigDecimal amount);

    /**
     * 减少商户账户余额
     * @param merchantId 商户ID
     * @param amount 减少金额
     * @return 减少是否成功
     */
    boolean decreaseMerchantBalance(String merchantId, BigDecimal amount);

    /**
     * 冻结商户账户余额
     * @param merchantId 商户ID
     * @param amount 冻结金额
     * @return 冻结是否成功
     */
    boolean freezeMerchantBalance(String merchantId, BigDecimal amount);

    /**
     * 解冻商户账户余额
     * @param merchantId 商户ID
     * @param amount 解冻金额
     * @return 解冻是否成功
     */
    boolean unfreezeMerchantBalance(String merchantId, BigDecimal amount);

    /**
     * 计算支付手续费
     * @param amount 支付金额
     * @param payChannel 支付渠道
     * @return 手续费金额
     */
    BigDecimal calculateServiceFee(BigDecimal amount, String payChannel);

    /**
     * 计算结算金额（支付金额 - 手续费）
     * @param amount 支付金额
     * @param payChannel 支付渠道
     * @return 结算金额
     */
    BigDecimal calculateSettlementAmount(BigDecimal amount, String payChannel);

    /**
     * 获取T+N结算日期
     * @param transactionTime 交易时间
     * @param n N值（如T+1中的1）
     * @return 结算日期
     */
    Date getSettlementDate(Date transactionTime, int n);
}
