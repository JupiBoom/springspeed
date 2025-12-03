package com.yasinyuan.testspring.payment.channel;

import com.yasinyuan.testspring.payment.dto.PaymentRequest;
import com.yasinyuan.testspring.payment.dto.PaymentResponse;
import com.yasinyuan.testspring.payment.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝支付渠道实现
 */
@Component("alipayChannel")
public class AlipayChannel implements PaymentChannelInterface {

    @Override
    public PaymentResponse pay(PaymentRequest request) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentNo(generatePaymentNo());
        response.setOrderNo(request.getOrderNo());
        response.setStatus(PaymentStatus.PROCESSING);
        
        // 模拟支付宝支付请求处理
        Map<String, Object> channelResponse = new HashMap<>();
        channelResponse.put("code", "200");
        channelResponse.put("msg", "success");
        channelResponse.put("tradeNo", generateChannelTradeNo());
        channelResponse.put("qrCode", "https://qr.alipay.com/bax000000000000000000000000000");
        response.setChannelResponse(channelResponse);
        
        return response;
    }

    @Override
    public PaymentResponse queryPaymentStatus(String paymentNo) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentNo(paymentNo);
        
        // 模拟查询支付状态
        // 实际应调用支付宝查询接口
        response.setStatus(PaymentStatus.SUCCESS);
        response.setChannelTradeNo(generateChannelTradeNo());
        
        return response;
    }

    @Override
    public PaymentResponse closePayment(String paymentNo) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentNo(paymentNo);
        response.setStatus(PaymentStatus.CLOSED);
        
        return response;
    }

    @Override
    public Map<String, Object> handleCallback(Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        
        // 模拟支付宝回调处理
        String tradeStatus = params.get("trade_status");
        if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
            result.put("success", true);
            result.put("paymentNo", params.get("out_trade_no"));
            result.put("channelTradeNo", params.get("trade_no"));
            result.put("status", PaymentStatus.SUCCESS);
        } else {
            result.put("success", false);
            result.put("errorMessage", "支付失败: " + tradeStatus);
        }
        
        return result;
    }

    @Override
    public String downloadBill(String date) {
        // 模拟下载支付宝对账单
        StringBuilder bill = new StringBuilder();
        bill.append("交易时间,交易流水号,订单号,交易金额,交易状态\n");
        bill.append("2023-01-01 10:00:00,20230101234567890,ORDER202301010001,100.00,SUCCESS\n");
        bill.append("2023-01-01 11:00:00,20230101234567891,ORDER202301010002,200.00,SUCCESS\n");
        
        return bill.toString();
    }
    
    private String generatePaymentNo() {
        return "PAY" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }
    
    private String generateChannelTradeNo() {
        return "ALIPAY" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }
}
