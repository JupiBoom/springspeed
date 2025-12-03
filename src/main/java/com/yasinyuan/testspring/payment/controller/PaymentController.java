package com.yasinyuan.testspring.payment.controller;

import com.yasinyuan.testspring.tools.R;
import com.yasinyuan.testspring.payment.enums.PaymentChannel;
import com.yasinyuan.testspring.payment.enums.PaymentStatus;
import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import com.yasinyuan.testspring.payment.dto.PaymentRequest;
import com.yasinyuan.testspring.payment.dto.PaymentResponse;
import com.yasinyuan.testspring.payment.model.PaymentTransaction;
import com.yasinyuan.testspring.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 支付控制器
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;
    
    /**
     * 创建支付订单
     * @param orderNo 订单号
     * @param amount 支付金额
     * @param channel 支付渠道
     * @param description 商品描述
     * @param attach 附加信息
     * @param timeoutMinutes 超时时间（分钟）
     * @return 支付订单信息
     */
    @PostMapping("/create")
    public R createPayment(@RequestBody PaymentRequest request) {
        try {
            PaymentResponse response = paymentService.createPayment(request);
            return R.ok().put("data", response);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 执行支付
     * @param paymentNo 支付流水号
     * @return 支付结果
     */
    @PostMapping("/execute")
    public R executePayment(@RequestParam String paymentNo) {
        try {
            PaymentResponse response = paymentService.executePayment(paymentNo);
            return R.ok().put("data", response);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 查询支付状态
     * @param paymentNo 支付流水号
     * @return 支付状态
     */
    @GetMapping("/status/{paymentNo}")
    public R queryPaymentStatus(@PathVariable String paymentNo) {
        try {
            PaymentTransaction transaction = paymentService.queryPaymentStatus(paymentNo);
            return R.ok().put("data", transaction);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 关闭支付订单
     * @param paymentNo 支付流水号
     * @return 关闭结果
     */
    @PostMapping("/close/{paymentNo}")
    public R closePayment(@PathVariable String paymentNo) {
        try {
            paymentService.closePayment(paymentNo);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 支付回调
     * @param channel 支付渠道
     * @param requestBody 回调请求体
     * @return 回调响应
     */
    @PostMapping("/callback/{channel}")
    public R handlePaymentCallback(@PathVariable String channel,
                                       @RequestBody String requestBody) {
        try {
            // 将请求体转换为Map
            JSONObject jsonObject = JSONObject.parseObject(requestBody);
            Map<String, String> params = jsonObject.toJavaObject(Map.class);
            
            Map<String, Object> result = paymentService.handlePaymentCallback(channel, params);
            return R.ok().put("data", result);
        } catch (Exception e) {
            // 返回渠道要求的失败响应格式
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 条件查询支付记录
     * @param orderNo 订单号
     * @param status 支付状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 支付记录列表
     */
    @GetMapping("/list")
    public R queryPayments(@RequestParam(required = false) String orderNo,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) String startTime,
                          @RequestParam(required = false) String endTime) {
        try {
            List<PaymentTransaction> payments = paymentService.queryPayments(orderNo, status != null ? PaymentStatus.valueOf(status) : null, startTime, endTime);
            return R.ok().put("data", payments);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 处理超时订单
     * @return 处理结果
     */
    @PostMapping("/process-timeout")
    public R processTimeoutOrders() {
        try {
            int count = paymentService.processTimeoutOrders();
            return R.ok().put("data", "处理了 " + count + " 个超时订单");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
}
