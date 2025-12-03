package com.yasinyuan.testspring.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 支付控制器
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    private static final Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private PayService payService;

    /**
     * 统一支付接口
     * @param request 支付请求参数
     * @return 支付结果
     */
    @PostMapping("/unifiedPay")
    public PayResult unifiedPay(@RequestBody PayRequest request) {
        logger.info("统一支付请求: {}", request);
        
        try {
            PayResult result = payService.unifiedPay(request);
            logger.info("统一支付结果: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("统一支付失败: {}", request, e);
            return new PayResult(false, "PAY_ERROR", "支付失败: " + e.getMessage(), null, null);
        }
    }

    /**
     * 查询支付状态接口
     * @param orderId 订单ID
     * @return 支付状态查询结果
     */
    @GetMapping("/queryPayStatus")
    public PayResult queryPayStatus(@RequestParam String orderId) {
        logger.info("查询支付状态请求: orderId={}", orderId);
        
        try {
            PayResult result = payService.queryPayStatus(orderId);
            logger.info("查询支付状态结果: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("查询支付状态失败: orderId={}", orderId, e);
            return new PayResult(false, "QUERY_ERROR", "查询失败: " + e.getMessage(), null, null);
        }
    }

    /**
     * 支付回调接口
     * @param payChannel 支付渠道
     * @param callbackData 回调数据
     * @return 回调处理结果
     */
    @PostMapping("/callback/{payChannel}")
    public PayCallbackResult callback(@PathVariable String payChannel, @RequestBody Map<String, Object> callbackData) {
        logger.info("支付回调请求: payChannel={}, callbackData={}", payChannel, callbackData);
        
        try {
            // 将String类型的payChannel转换为PayChannel枚举类型
            PayChannel channel = PayChannel.valueOf(payChannel);
            PayCallbackResult result = payService.handlePayCallback(channel, callbackData);
            logger.info("支付回调处理结果: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("支付回调处理失败: payChannel={}, callbackData={}", payChannel, callbackData, e);
            return new PayCallbackResult(false, "CALLBACK_ERROR", "回调处理失败: " + e.getMessage(), null, null);
        }
    }

    /**
     * 关闭超时订单接口
     * @param orderId 订单ID
     * @return 关闭结果
     */
    @PostMapping("/closeTimeoutOrder")
    public boolean closeTimeoutOrder(@RequestParam String orderId) {
        logger.info("关闭超时订单请求: orderId={}", orderId);
        
        try {
            boolean result = payService.closeTimeoutOrder(orderId);
            logger.info("关闭超时订单结果: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("关闭超时订单失败: orderId={}", orderId, e);
            return false;
        }
    }
}
