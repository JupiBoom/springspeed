package com.yasinyuan.testspring.payment.service.impl;

import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.payment.dao.PaymentTransactionMapper;
import com.yasinyuan.testspring.payment.dao.SettlementRecordMapper;
import com.yasinyuan.testspring.payment.enums.PaymentStatus;
import com.yasinyuan.testspring.payment.model.PaymentTransaction;
import com.yasinyuan.testspring.payment.model.SettlementRecord;
import com.yasinyuan.testspring.payment.service.MerchantAccountService;
import com.yasinyuan.testspring.payment.service.SettlementService;
import com.yasinyuan.testspring.tools.DateUtils;
import com.yasinyuan.testspring.tools.exception.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 结算服务实现类
 */
@Service
public class SettlementServiceImpl extends AbstractService<SettlementRecord> implements SettlementService {
    
    @Autowired
    private SettlementRecordMapper settlementRecordMapper;
    
    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;
    
    @Autowired
    private MerchantAccountService merchantAccountService;
    
    @Override
    @Transactional
    public SettlementRecord createSettlement(String merchantId, Date cycleStartTime, Date cycleEndTime) {
        // 检查是否已存在相同周期的结算单
        List<SettlementRecord> existingRecords = settlementRecordMapper.selectByMerchantIdAndStatus(merchantId, 0);
        for (SettlementRecord record : existingRecords) {
            if (record.getCycleStartTime().equals(cycleStartTime) && record.getCycleEndTime().equals(cycleEndTime)) {
                throw new RRException("该周期的结算单已存在");
            }
        }
        
        // 统计交易数据
        List<PaymentTransaction> transactions = paymentTransactionMapper.selectStatistics(
            merchantId, cycleStartTime, cycleEndTime, PaymentStatus.SUCCESS);
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (PaymentTransaction transaction : transactions) {
            totalAmount = totalAmount.add(transaction.getAmount());
        }
        
        // 计算手续费
        BigDecimal feeRate = merchantAccountService.getByMerchantId(merchantId).getFeeRate();
        BigDecimal totalFee = calculateFee(totalAmount, feeRate);
        BigDecimal settlementAmount = totalAmount.subtract(totalFee);
        
        // 创建结算记录
        SettlementRecord settlementRecord = new SettlementRecord();
        settlementRecord.setSettlementNo(generateSettlementNo());
        settlementRecord.setMerchantId(merchantId);
        settlementRecord.setCycleStartTime(cycleStartTime);
        settlementRecord.setCycleEndTime(cycleEndTime);
        settlementRecord.setTotalAmount(totalAmount);
        settlementRecord.setTotalFee(totalFee);
        settlementRecord.setSettlementAmount(settlementAmount);
        settlementRecord.setTransactionCount(transactions.size());
        settlementRecord.setStatus(0); // 待结算
        settlementRecord.setCreateTime(new Date());
        settlementRecord.setUpdateTime(new Date());
        
        settlementRecordMapper.insert(settlementRecord);
        
        return settlementRecord;
    }
    
    @Override
    @Transactional
    public boolean executeSettlement(String settlementNo) {
        SettlementRecord settlementRecord = settlementRecordMapper.selectBySettlementNo(settlementNo);
        if (settlementRecord == null) {
            throw new RRException("结算单不存在");
        }
        
        if (settlementRecord.getStatus() != 0) {
            throw new RRException("结算单状态不正确");
        }
        
        // 更新结算单状态为结算中
        settlementRecord.setStatus(1);
        settlementRecord.setUpdateTime(new Date());
        settlementRecordMapper.updateByPrimaryKey(settlementRecord);
        
        try {
            // 执行实际的结算操作（调用银行接口等）
            // 这里简化处理，直接模拟结算成功
            Thread.sleep(1000); // 模拟银行接口调用耗时
            
            // 更新结算单状态为结算成功
            settlementRecord.setStatus(2);
            settlementRecord.setSettlementTime(new Date());
            settlementRecord.setBankTradeNo(generateBankTradeNo());
            settlementRecord.setUpdateTime(new Date());
            settlementRecordMapper.updateByPrimaryKey(settlementRecord);
            
            // 增加商户账户余额
            merchantAccountService.addBalance(settlementRecord.getMerchantId(), settlementRecord.getSettlementAmount());
            
            return true;
        } catch (Exception e) {
            // 更新结算单状态为结算失败
            settlementRecord.setStatus(3);
            settlementRecord.setFailReason(e.getMessage());
            settlementRecord.setUpdateTime(new Date());
            settlementRecordMapper.updateByPrimaryKey(settlementRecord);
            
            throw new RRException("结算失败: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public int batchCreateSettlements(Date date) {
        // 这里简化处理，实际应查询所有需要结算的商户并为每个商户创建结算单
        // 目前返回模拟值
        return 0;
    }
    
    @Override
    @Transactional
    public int batchExecuteSettlements() {
        Date currentTime = new Date();
        List<SettlementRecord> pendingSettlements = settlementRecordMapper.selectPendingSettlements(currentTime);
        
        int executedCount = 0;
        for (SettlementRecord settlement : pendingSettlements) {
            if (executeSettlement(settlement.getSettlementNo())) {
                executedCount++;
            }
        }
        
        return executedCount;
    }
    
    @Override
    public List<SettlementRecord> querySettlementsByMerchant(String merchantId, Integer status, int page, int size) {
        // 这里可以实现分页查询逻辑，目前简化处理
        return settlementRecordMapper.selectAll();
    }
    
    @Override
    public BigDecimal calculateFee(BigDecimal amount, BigDecimal feeRate) {
        if (amount == null || feeRate == null) {
            return BigDecimal.ZERO;
        }
        
        // 手续费 = 交易金额 * 手续费率，保留两位小数
        return amount.multiply(feeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    private String generateSettlementNo() {
        return "SETT" + DateUtils.format(new Date(), "yyyyMMddHHmmss") + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private String generateBankTradeNo() {
        return "BANK" + DateUtils.format(new Date(), "yyyyMMddHHmmss") + (int)(Math.random() * 10000);
    }
}
