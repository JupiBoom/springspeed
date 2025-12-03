package com.yasinyuan.testspring.payment.channel;

import com.yasinyuan.testspring.payment.dto.PaymentRequest;
import com.yasinyuan.testspring.payment.dto.PaymentResponse;
import com.yasinyuan.testspring.payment.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付渠道实现
 */
@Component("wechatPayChannel")
public class WechatPayChannel implements PaymentChannelInterface {

    @Override
    public PaymentResponse pay(PaymentRequest request) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentNo(generatePaymentNo());
        response.setOrderNo(request.getOrderNo());
        response.setStatus(PaymentStatus.PROCESSING);
        
        // 模拟微信支付请求处理
        Map<String, Object> channelResponse = new HashMap<>();
        channelResponse.put("return_code", "SUCCESS");
        channelResponse.put("result_code", "SUCCESS");
        channelResponse.put("prepay_id", "wx201410272009395522657a690389285100");
        channelResponse.put("code_url", "weixin://wxpay/bizpayurl?pr=xxxxxxxxxx");
        response.setChannelResponse(channelResponse);
        
        return response;
    }

    @Override
    public PaymentResponse queryPaymentStatus(String paymentNo) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentNo(paymentNo);
        
        // 模拟查询支付状态
        // 实际应调用微信查询接口
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
        
        // 模拟微信回调处理
        String returnCode = params.get("return_code");
        String resultCode = params.get("result_code");
        
        if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
            result.put("success", true);
            result.put("paymentNo", params.get("out_trade_no"));
            result.put("channelTradeNo", params.get("transaction_id"));
            result.put("status", PaymentStatus.SUCCESS);
        } else {
            result.put("success", false);
            result.put("errorMessage", "支付失败: " + params.get("err_code_des"));
        }
        
        return result;
    }

    @Override
    public String downloadBill(String date) {
        // 模拟下载微信对账单
        StringBuilder bill = new StringBuilder();
        bill.append("交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,企业红包金额,微信退款单号,商户退款单号,退款金额,企业红包退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率\n");
        bill.append("2023-01-01 10:00:00,wxc0f1b3a49f35350e,1234567890,0,0,1217752501201407033233368018,ORDER202301010001,oUpF8uMuAJO_M2pxb1Q9zNjWeS6o,MWEB,SUCCESS,CMB_CREDIT,CNY,100.00,0.00,,,-,,-,,0.60,0.6%\n");
        
        return bill.toString();
    }
    
    private String generatePaymentNo() {
        return "WXPAY" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }
    
    private String generateChannelTradeNo() {
        return "WX" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }
}
