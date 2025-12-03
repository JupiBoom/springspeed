package com.yasinyuan.testspring.payment;

import java.math.BigDecimal;

/**
 * 支付服务接口
 */
public interface PayService {

    /**
     * 统一支付接口
     * @param request 支付请求参数
     * @return 支付结果
     */
    PayResult unifiedPay(PayRequest request);

    /**
     * 查询支付状态
     * @param orderId 订单ID
     * @return 支付结果
     */
    PayResult queryPayStatus(String orderId);

    /**
     * 支付回调处理
     * @param payChannel 支付渠道
     * @param callbackData 回调数据
     * @return 回调处理结果
     */
    PayCallbackResult handlePayCallback(PayChannel payChannel, Map<String, Object> callbackData);

    /**
     * 关闭超时订单
     * @param orderId 订单ID
     * @return 关闭结果
     */
    boolean closeTimeoutOrder(String orderId);
}
