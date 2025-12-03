package com.yasinyuan.testspring.settlement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 结算服务实现类
 */
@Service
public class SettlementServiceImpl implements SettlementService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 支付渠道手续费率配置
     */
    private static final BigDecimal WECHAT_PAY_FEE_RATE = new BigDecimal("0.006");  // 微信支付手续费率：0.6%
    private static final BigDecimal ALIPAY_FEE_RATE = new BigDecimal("0.0055");  // 支付宝手续费率：0.55%
    private static final BigDecimal UNIONPAY_FEE_RATE = new BigDecimal("0.0065");  // 银联支付手续费率：0.65%

    /**
     * 根据商户ID查询商户资金账户实现
     */
    @Override
    public MerchantAccount queryMerchantAccount(String merchantId) {
        try {
            // 这里简化实现，实际项目中应该从数据库中查询商户资金账户
            // 为了演示，我们从Redis中获取商户资金账户的关键信息
            String key = "merchant_account:" + merchantId;
            String accountStr = redisTemplate.opsForValue().get(key);

            if (accountStr != null) {
                // 这里简化实现，实际项目中应该将字符串解析为MerchantAccount对象
                MerchantAccount account = new MerchantAccount();
                account.setMerchantId(merchantId);
                account.setBalance(new BigDecimal("10000.00"));  // 默认余额10000元
                account.setWithdrawableBalance(new BigDecimal("8000.00"));  // 默认可提现余额8000元
                account.setFrozenBalance(new BigDecimal("2000.00"));  // 默认冻结余额2000元
                account.setStatus("ACTIVE");  // 默认账户状态为活跃
                return account;
            } else {
                // 如果商户账户不存在，创建一个默认的商户账户
                MerchantAccount account = new MerchantAccount();
                account.setMerchantId(merchantId);
                account.setBalance(new BigDecimal("0.00"));
                account.setWithdrawableBalance(new BigDecimal("0.00"));
                account.setFrozenBalance(new BigDecimal("0.00"));
                account.setStatus("ACTIVE");
                account.setCreateTime(new Date());
                account.setUpdateTime(new Date());

                // 保存到Redis中
                saveMerchantAccountToRedis(account);

                return account;
            }
        } catch (Exception e) {
            // 处理异常，返回查询失败
            return null;
        }
    }

    /**
     * 增加商户账户余额实现
     */
    @Override
    public boolean increaseMerchantBalance(String merchantId, BigDecimal amount) {
        try {
            // 这里简化实现，实际项目中应该更新数据库中的商户资金账户余额
            // 为了演示，我们从Redis中获取商户资金账户，更新余额后再保存到Redis中
            MerchantAccount account = queryMerchantAccount(merchantId);
            if (account != null) {
                // 增加账户余额
                BigDecimal newBalance = account.getBalance().add(amount);
                account.setBalance(newBalance);

                // 增加可提现余额
                BigDecimal newWithdrawableBalance = account.getWithdrawableBalance().add(amount);
                account.setWithdrawableBalance(newWithdrawableBalance);

                account.setUpdateTime(new Date());

                // 保存到Redis中
                saveMerchantAccountToRedis(account);

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // 处理异常，返回增加失败
            return false;
        }
    }

    /**
     * 减少商户账户余额实现
     */
    @Override
    public boolean decreaseMerchantBalance(String merchantId, BigDecimal amount) {
        try {
            // 这里简化实现，实际项目中应该更新数据库中的商户资金账户余额
            // 为了演示，我们从Redis中获取商户资金账户，更新余额后再保存到Redis中
            MerchantAccount account = queryMerchantAccount(merchantId);
            if (account != null) {
                // 检查账户余额是否充足
                if (account.getBalance().compareTo(amount) < 0) {
                    return false;
                }

                // 减少账户余额
                BigDecimal newBalance = account.getBalance().subtract(amount);
                account.setBalance(newBalance);

                // 减少可提现余额
                BigDecimal newWithdrawableBalance = account.getWithdrawableBalance().subtract(amount);
                account.setWithdrawableBalance(newWithdrawableBalance);

                account.setUpdateTime(new Date());

                // 保存到Redis中
                saveMerchantAccountToRedis(account);

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // 处理异常，返回减少失败
            return false;
        }
    }

    /**
     * 冻结商户账户余额实现
     */
    @Override
    public boolean freezeMerchantBalance(String merchantId, BigDecimal amount) {
        try {
            // 这里简化实现，实际项目中应该更新数据库中的商户资金账户余额
            // 为了演示，我们从Redis中获取商户资金账户，更新余额后再保存到Redis中
            MerchantAccount account = queryMerchantAccount(merchantId);
            if (account != null) {
                // 检查可提现余额是否充足
                if (account.getWithdrawableBalance().compareTo(amount) < 0) {
                    return false;
                }

                // 减少可提现余额
                BigDecimal newWithdrawableBalance = account.getWithdrawableBalance().subtract(amount);
                account.setWithdrawableBalance(newWithdrawableBalance);

                // 增加冻结余额
                BigDecimal newFrozenBalance = account.getFrozenBalance().add(amount);
                account.setFrozenBalance(newFrozenBalance);

                account.setUpdateTime(new Date());

                // 保存到Redis中
                saveMerchantAccountToRedis(account);

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // 处理异常，返回冻结失败
            return false;
        }
    }

    /**
     * 解冻商户账户余额实现
     */
    @Override
    public boolean unfreezeMerchantBalance(String merchantId, BigDecimal amount) {
        try {
            // 这里简化实现，实际项目中应该更新数据库中的商户资金账户余额
            // 为了演示，我们从Redis中获取商户资金账户，更新余额后再保存到Redis中
            MerchantAccount account = queryMerchantAccount(merchantId);
            if (account != null) {
                // 检查冻结余额是否充足
                if (account.getFrozenBalance().compareTo(amount) < 0) {
                    return false;
                }

                // 减少冻结余额
                BigDecimal newFrozenBalance = account.getFrozenBalance().subtract(amount);
                account.setFrozenBalance(newFrozenBalance);

                // 增加可提现余额
                BigDecimal newWithdrawableBalance = account.getWithdrawableBalance().add(amount);
                account.setWithdrawableBalance(newWithdrawableBalance);

                account.setUpdateTime(new Date());

                // 保存到Redis中
                saveMerchantAccountToRedis(account);

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // 处理异常，返回解冻失败
            return false;
        }
    }

    /**
     * 计算支付手续费实现
     */
    @Override
    public BigDecimal calculateServiceFee(BigDecimal amount, String payChannel) {
        // 根据支付渠道选择不同的手续费率
        BigDecimal feeRate = BigDecimal.ZERO;
        switch (payChannel) {
            case "WECHAT_PAY":
                feeRate = WECHAT_PAY_FEE_RATE;
                break;
            case "ALIPAY":
                feeRate = ALIPAY_FEE_RATE;
                break;
            case "UNIONPAY":
                feeRate = UNIONPAY_FEE_RATE;
                break;
            default:
                throw new IllegalArgumentException("不支持的支付渠道: " + payChannel);
        }

        // 计算手续费（支付金额 × 手续费率）
        BigDecimal serviceFee = amount.multiply(feeRate);

        // 手续费保留两位小数
        return serviceFee.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 计算结算金额实现
     */
    @Override
    public BigDecimal calculateSettlementAmount(BigDecimal amount, String payChannel) {
        // 结算金额 = 支付金额 - 手续费
        BigDecimal serviceFee = calculateServiceFee(amount, payChannel);
        BigDecimal settlementAmount = amount.subtract(serviceFee);

        // 结算金额保留两位小数
        return settlementAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 获取T+N结算日期实现
     */
    @Override
    public Date getSettlementDate(Date transactionTime, int n) {
        // 这里简化实现，实际项目中应该考虑节假日等因素
        // 为了演示，我们直接在交易时间的基础上增加N天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(transactionTime);
        calendar.add(Calendar.DAY_OF_MONTH, n);

        return calendar.getTime();
    }

    /**
     * 将商户账户信息保存到Redis中
     */
    private void saveMerchantAccountToRedis(MerchantAccount account) {
        String key = "merchant_account:" + account.getMerchantId();
        // 这里简化实现，实际项目中应该将MerchantAccount对象序列化为JSON字符串
        String accountStr = account.toString();
        redisTemplate.opsForValue().set(key, accountStr, 24, TimeUnit.HOURS);
    }
}
