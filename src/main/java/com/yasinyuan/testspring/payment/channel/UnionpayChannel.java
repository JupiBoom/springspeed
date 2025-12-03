package com.yasinyuan.testspring.payment.channel;

import com.yasinyuan.testspring.payment.dto.PaymentRequest;
import com.yasinyuan.testspring.payment.dto.PaymentResponse;
import com.yasinyuan.testspring.payment.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 银联支付渠道实现
 */
@Component("unionpayChannel")
public class UnionpayChannel implements PaymentChannelInterface {

    @Override
    public PaymentResponse pay(PaymentRequest request) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentNo(generatePaymentNo());
        response.setOrderNo(request.getOrderNo());
        response.setStatus(PaymentStatus.PROCESSING);
        
        // 模拟银联支付请求处理
        Map<String, Object> channelResponse = new HashMap<>();
        channelResponse.put("respCode", "00");
        channelResponse.put("respMsg", "成功");
        channelResponse.put("tn", "815766521194761560600");
        response.setChannelResponse(channelResponse);
        
        return response;
    }

    @Override
    public PaymentResponse queryPaymentStatus(String paymentNo) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentNo(paymentNo);
        
        // 模拟查询支付状态
        // 实际应调用银联查询接口
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
        
        // 模拟银联回调处理
        String respCode = params.get("respCode");
        
        if ("00".equals(respCode)) {
            result.put("success", true);
            result.put("paymentNo", params.get("orderId"));
            result.put("channelTradeNo", params.get("queryId"));
            result.put("status", PaymentStatus.SUCCESS);
        } else {
            result.put("success", false);
            result.put("errorMessage", "支付失败: " + params.get("respMsg"));
        }
        
        return result;
    }

    @Override
    public String downloadBill(String date) {
        // 模拟下载银联对账单
        StringBuilder bill = new StringBuilder();
        bill.append("交易日期,交易时间,交易类型,交易状态,商户号,终端号,商户订单号,银联交易流水号,清算金额,手续费,发卡行,收单行\n");
        bill.append("20230101,100000,消费,成功,898110053110001,00000001,ORDER202301010001,20230101100000000001,100.00,0.60,工商银行,建设银行\n");
        
        return bill.toString();
    }
    
    private String generatePaymentNo() {
        return "UNIONPAY" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }
    
    private String generateChannelTradeNo() {
        return "UP" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }
}
