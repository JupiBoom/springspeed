package com.yasinyuan.testspring.payment.service.impl;

import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.payment.channel.PaymentChannelFactory;
import com.yasinyuan.testspring.payment.channel.PaymentChannelInterface;
import com.yasinyuan.testspring.payment.dao.PaymentTransactionMapper;
import com.yasinyuan.testspring.payment.dto.PaymentRequest;
import com.yasinyuan.testspring.payment.dto.PaymentResponse;
import com.yasinyuan.testspring.payment.enums.PaymentChannel;
import com.yasinyuan.testspring.payment.enums.PaymentEvent;
import com.yasinyuan.testspring.payment.enums.PaymentStatus;
import com.yasinyuan.testspring.payment.model.PaymentTransaction;
import com.yasinyuan.testspring.payment.service.PaymentService;
import com.yasinyuan.testspring.tools.DateUtils;
import com.yasinyuan.testspring.tools.exception.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 支付服务实现类
 */
@Service
public class PaymentServiceImpl extends AbstractService<PaymentTransaction> implements PaymentService {
    
    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;
    
    @Autowired
    private PaymentChannelFactory paymentChannelFactory;
    
    @Autowired
    private StateMachine<PaymentStatus, PaymentEvent> paymentStateMachine;
    
    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        // 检查订单是否已存在
        PaymentTransaction existingTransaction = paymentTransactionMapper.selectByOrderNo(request.getOrderNo());
        if (existingTransaction != null) {
            throw new RRException("订单已存在");
        }
        
        // 创建支付交易记录
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setPaymentNo(generatePaymentNo());
        transaction.setOrderNo(request.getOrderNo());
        transaction.setAmount(request.getAmount());
        transaction.setChannel(request.getChannel());
        transaction.setStatus(PaymentStatus.PENDING);
        transaction.setDescription(request.getDescription());
        transaction.setExtraParams(request.getExtraParams() != null ? request.getExtraParams().toString() : null);
        
        // 设置超时时间
        if (request.getTimeoutMinutes() != null && request.getTimeoutMinutes() > 0) {
            transaction.setTimeoutTime(DateUtils.addDateMinutes(new Date(), request.getTimeoutMinutes()));
        } else {
            // 默认30分钟超时
            transaction.setTimeoutTime(DateUtils.addDateMinutes(new Date(), 30));
        }
        
        transaction.setCreateTime(new Date());
        transaction.setUpdateTime(new Date());
        
        paymentTransactionMapper.insert(transaction);
        
        PaymentResponse response = new PaymentResponse();
        response.setPaymentNo(transaction.getPaymentNo());
        response.setOrderNo(transaction.getOrderNo());
        response.setStatus(transaction.getStatus());
        
        return response;
    }
    
    @Override
    @Transactional
    public PaymentResponse executePayment(String paymentNo) {
        PaymentTransaction transaction = paymentTransactionMapper.selectByPaymentNo(paymentNo);
        if (transaction == null) {
            throw new RRException("支付订单不存在");
        }
        
        if (transaction.getStatus() != PaymentStatus.PENDING) {
            throw new RRException("订单状态不正确");
        }
        
        // 使用状态机处理状态转换
        paymentStateMachine.getExtendedState().getVariables().put("paymentNo", paymentNo);
        paymentStateMachine.sendEvent(PaymentEvent.START_PAYMENT);
        
        // 调用支付渠道
        PaymentChannelInterface paymentChannel = paymentChannelFactory.getPaymentChannel(transaction.getChannel());
        PaymentRequest request = new PaymentRequest();
        request.setOrderNo(transaction.getOrderNo());
        request.setAmount(transaction.getAmount());
        request.setChannel(transaction.getChannel());
        request.setDescription(transaction.getDescription());
        
        PaymentResponse response = paymentChannel.pay(request);
        
        // 更新交易记录
        transaction.setStatus(response.getStatus());
        transaction.setChannelTradeNo(response.getChannelTradeNo());
        transaction.setUpdateTime(new Date());
        paymentTransactionMapper.updateByPrimaryKey(transaction);
        
        return response;
    }
    
    @Override
    public PaymentTransaction queryPaymentStatus(String paymentNo) {
        PaymentTransaction transaction = paymentTransactionMapper.selectByPaymentNo(paymentNo);
        if (transaction == null) {
            throw new RRException("支付订单不存在");
        }
        
        // 如果订单状态不是最终状态，查询渠道状态
        if (transaction.getStatus() != PaymentStatus.SUCCESS && transaction.getStatus() != PaymentStatus.FAILED && transaction.getStatus() != PaymentStatus.CLOSED) {
            PaymentChannelInterface paymentChannel = paymentChannelFactory.getPaymentChannel(transaction.getChannel());
            PaymentResponse response = paymentChannel.queryPaymentStatus(paymentNo);
            
            if (response.getStatus() != transaction.getStatus()) {
                transaction.setStatus(response.getStatus());
                transaction.setChannelTradeNo(response.getChannelTradeNo());
                transaction.setUpdateTime(new Date());
                paymentTransactionMapper.updateByPrimaryKey(transaction);
            }
        }
        
        return transaction;
    }
    
    @Override
    @Transactional
    public boolean closePayment(String paymentNo) {
        PaymentTransaction transaction = paymentTransactionMapper.selectByPaymentNo(paymentNo);
        if (transaction == null) {
            throw new RRException("支付订单不存在");
        }
        
        if (transaction.getStatus() == PaymentStatus.SUCCESS || transaction.getStatus() == PaymentStatus.CLOSED) {
            return true;
        }
        
        // 使用状态机处理状态转换
        paymentStateMachine.getExtendedState().getVariables().put("paymentNo", paymentNo);
        paymentStateMachine.sendEvent(PaymentEvent.CLOSE_PAYMENT);
        
        // 调用支付渠道关闭订单
        PaymentChannelInterface paymentChannel = paymentChannelFactory.getPaymentChannel(transaction.getChannel());
        paymentChannel.closePayment(paymentNo);
        
        // 更新交易记录
        transaction.setStatus(PaymentStatus.CLOSED);
        transaction.setUpdateTime(new Date());
        int result = paymentTransactionMapper.updateByPrimaryKey(transaction);
        
        return result > 0;
    }
    
    @Override
    @Transactional
    public Map<String, Object> handlePaymentCallback(String channel, Map<String, String> params) {
        PaymentChannel paymentChannelEnum = PaymentChannel.valueOf(channel.toUpperCase());
        PaymentChannelInterface paymentChannel = paymentChannelFactory.getPaymentChannel(paymentChannelEnum);
        
        Map<String, Object> callbackResult = paymentChannel.handleCallback(params);
        
        if ((Boolean) callbackResult.get("success")) {
            String paymentNo = (String) callbackResult.get("paymentNo");
            String channelTradeNo = (String) callbackResult.get("channelTradeNo");
            PaymentStatus status = (PaymentStatus) callbackResult.get("status");
            
            // 更新交易记录
            PaymentTransaction transaction = paymentTransactionMapper.selectByPaymentNo(paymentNo);
            if (transaction != null) {
                transaction.setStatus(status);
                transaction.setChannelTradeNo(channelTradeNo);
                transaction.setPayTime(new Date());
                transaction.setUpdateTime(new Date());
                paymentTransactionMapper.updateByPrimaryKey(transaction);
                
                // 使用状态机处理状态转换
                paymentStateMachine.getExtendedState().getVariables().put("paymentNo", paymentNo);
                paymentStateMachine.sendEvent(PaymentEvent.PAY_SUCCESS);
            }
        }
        
        return callbackResult;
    }
    
    @Override
    @Transactional
    public int processTimeoutOrders() {
        Date currentTime = new Date();
        List<PaymentTransaction> timeoutOrders = paymentTransactionMapper.selectTimeoutOrders(currentTime, PaymentStatus.PENDING);
        
        int processedCount = 0;
        for (PaymentTransaction order : timeoutOrders) {
            if (closePayment(order.getPaymentNo())) {
                processedCount++;
            }
        }
        
        return processedCount;
    }
    
    @Override
    public List<PaymentTransaction> queryPayments(String orderNo, PaymentStatus status, String startTime, String endTime) {
        // 这里可以实现复杂的查询逻辑，目前简化处理
        return paymentTransactionMapper.selectAll();
    }
    
    private String generatePaymentNo() {
        return "PAY" + DateUtils.format(new Date(), "yyyyMMddHHmmss") + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
