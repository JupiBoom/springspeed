package com.yasinyuan.testspring.payment.channel;

import com.yasinyuan.testspring.payment.enums.PaymentChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付渠道工厂
 */
@Component
public class PaymentChannelFactory {
    
    private final Map<PaymentChannel, PaymentChannelInterface> channelMap = new HashMap<>();
    
    @Autowired
    public PaymentChannelFactory(AlipayChannel alipayChannel, 
                               WechatPayChannel wechatPayChannel, 
                               UnionpayChannel unionpayChannel) {
        channelMap.put(PaymentChannel.ALIPAY, alipayChannel);
        channelMap.put(PaymentChannel.WECHAT_PAY, wechatPayChannel);
        channelMap.put(PaymentChannel.UNIONPAY, unionpayChannel);
    }
    
    /**
     * 根据支付渠道获取支付渠道实现
     * @param channel 支付渠道
     * @return 支付渠道实现
     */
    public PaymentChannelInterface getPaymentChannel(PaymentChannel channel) {
        PaymentChannelInterface paymentChannel = channelMap.get(channel);
        if (paymentChannel == null) {
            throw new IllegalArgumentException("不支持的支付渠道: " + channel);
        }
        return paymentChannel;
    }
}
