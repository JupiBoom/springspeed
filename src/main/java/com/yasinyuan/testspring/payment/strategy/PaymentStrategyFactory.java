package com.yasinyuan.testspring.payment.strategy;

import com.yasinyuan.testspring.payment.enums.PaymentChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付策略工厂
 * @author yinyuan
 */
@Component
public class PaymentStrategyFactory {
    
    private final Map<PaymentChannel, PaymentChannelStrategy> strategyMap = new HashMap<>();
    
    @Autowired
    public PaymentStrategyFactory(List<PaymentChannelStrategy> strategies) {
        for (PaymentChannelStrategy strategy : strategies) {
            strategyMap.put(strategy.getChannel(), strategy);
        }
    }
    
    /**
     * 根据支付渠道获取对应的策略
     */
    public PaymentChannelStrategy getStrategy(PaymentChannel channel) {
        return strategyMap.get(channel);
    }
}
