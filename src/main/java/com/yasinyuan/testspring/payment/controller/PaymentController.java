package com.yasinyuan.testspring.payment.controller;

import com.yasinyuan.testspring.payment.dto.PaymentRequest;
import com.yasinyuan.testspring.payment.dto.PaymentResponse;
import com.yasinyuan.testspring.payment.enums.PaymentChannel;
import com.yasinyuan.testspring.payment.service.PaymentGatewayService;
import com.yasinyuan.testspring.tools.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 支付控制器
 * @author yinyuan
 */
@RestController
@RequestMapping("/api/payment")
@Api(tags = "支付管理")
public class PaymentController {
    
    @Autowired
    private PaymentGatewayService paymentGatewayService;

    @ApiOperation("统一支付接口")
    @PostMapping("/pay")
    public R pay(@RequestBody PaymentRequest request) {
        PaymentResponse response = paymentGatewayService.pay(request);
        return response.isSuccess() ? R.ok(response) : R.error(response.getErrorMsg());
    }

    @ApiOperation("微信支付回调")
    @PostMapping("/callback/wechat")
    public String wechatCallback(HttpServletRequest request) {
        try {
            String body = readRequestBody(request);
            paymentGatewayService.handleCallback(PaymentChannel.WECHAT_PAY, body);
            return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        } catch (Exception e) {
            return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[ERROR]]></return_msg></xml>";
        }
    }

    @ApiOperation("支付宝支付回调")
    @PostMapping("/callback/alipay")
    public String alipayCallback(HttpServletRequest request) {
        try {
            String body = readRequestBody(request);
            paymentGatewayService.handleCallback(PaymentChannel.ALIPAY, body);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

    @ApiOperation("银联支付回调")
    @PostMapping("/callback/unionpay")
    public String unionpayCallback(HttpServletRequest request) {
        try {
            String body = readRequestBody(request);
            paymentGatewayService.handleCallback(PaymentChannel.UNIONPAY, body);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

    @ApiOperation("查询支付状态")
    @GetMapping("/status")
    public R queryStatus(@RequestParam String paymentNo, @RequestParam PaymentChannel channel) {
        String status = paymentGatewayService.queryPaymentStatus(paymentNo, channel);
        return R.ok(status);
    }

    @ApiOperation("关闭支付")
    @PostMapping("/close")
    public R closePayment(@RequestParam String paymentNo) {
        boolean result = paymentGatewayService.closePayment(paymentNo);
        return result ? R.ok() : R.error("关闭支付失败");
    }
    
    private String readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
