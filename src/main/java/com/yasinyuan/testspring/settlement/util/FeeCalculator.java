package com.yasinyuan.testspring.settlement.util;

import com.yasinyuan.testspring.settlement.dao.FeeRuleMapper;
import com.yasinyuan.testspring.settlement.model.FeeRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * 手续费计算器
 * 根据交易信息和手续费规则计算手续费
 */
@Component
public class FeeCalculator {
    
    @Autowired
    private FeeRuleMapper feeRuleMapper;
    
    /**
     * 计算手续费
     * @param paymentChannel 支付渠道
     * @param tradeType 交易类型
     * @param amount 交易金额
     * @return 手续费金额
     */
    public BigDecimal calculateFee(String paymentChannel, String tradeType, BigDecimal amount) {
        // 查询有效的手续费规则
        FeeRule feeRule = feeRuleMapper.selectEffectiveRule(paymentChannel, tradeType, LocalDateTime.now());
        
        if (feeRule == null) {
            throw new RuntimeException("未找到有效的手续费规则: paymentChannel=" + paymentChannel + ", tradeType=" + tradeType);
        }
        
        BigDecimal fee = BigDecimal.ZERO;
        
        switch (feeRule.getRuleType()) {
            case "FIXED":
                // 固定金额
                fee = feeRule.getFixedFee();
                break;
            case "PERCENTAGE":
                // 百分比
                fee = amount.multiply(feeRule.getPercentage()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                break;
            case "HYBRID":
                // 混合模式（固定金额 + 百分比）
                BigDecimal fixedFee = feeRule.getFixedFee();
                BigDecimal percentageFee = amount.multiply(feeRule.getPercentage()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                fee = fixedFee.add(percentageFee);
                break;
            default:
                throw new RuntimeException("不支持的手续费规则类型: " + feeRule.getRuleType());
        }
        
        // 应用最低和最高手续费限制
        BigDecimal minFee = feeRule.getMinFee();
        BigDecimal maxFee = feeRule.getMaxFee();
        
        if (minFee != null && fee.compareTo(minFee) < 0) {
            fee = minFee;
        }
        
        if (maxFee != null && fee.compareTo(maxFee) > 0) {
            fee = maxFee;
        }
        
        return fee.setScale(2, RoundingMode.HALF_UP);
    }
}