package com.yasinyuan.testspring.reconciliation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yasinyuan.testspring.reconciliation.dao.ReconciliationRecordMapper;
import com.yasinyuan.testspring.reconciliation.dao.ReconciliationDiscrepancyMapper;
import com.yasinyuan.testspring.reconciliation.model.ReconciliationRecord;
import com.yasinyuan.testspring.reconciliation.model.ReconciliationDiscrepancy;
import com.yasinyuan.testspring.reconciliation.service.ReconciliationService;
import com.yasinyuan.testspring.reconciliation.util.ReconciliationNoGenerator;
import com.yasinyuan.testspring.transaction.model.TransactionRecord;
import com.yasinyuan.testspring.transaction.dao.TransactionRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对账服务实现类
 */
@Service
public class ReconciliationServiceImpl implements ReconciliationService {
    
    private static final Logger logger = LoggerFactory.getLogger(ReconciliationServiceImpl.class);
    
    @Autowired
    private ReconciliationRecordMapper reconciliationRecordMapper;
    
    @Autowired
    private ReconciliationDiscrepancyMapper reconciliationDiscrepancyMapper;
    
    @Autowired
    private TransactionRecordMapper transactionRecordMapper;
    
    @Autowired
    private ReconciliationNoGenerator reconciliationNoGenerator;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReconciliationRecord createReconciliationTask(String reconciliationDate, String reconciliationType, String paymentChannel) {
        ReconciliationRecord record = new ReconciliationRecord();
        record.setReconciliationNo(reconciliationNoGenerator.generateReconciliationNo());
        record.setReconciliationDate(reconciliationDate);
        record.setReconciliationType(reconciliationType);
        record.setPaymentChannel(paymentChannel);
        record.setStatus("PENDING");
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        
        reconciliationRecordMapper.insert(record);
        logger.info("创建对账任务成功: {}", record.getReconciliationNo());
        return record;
    }
    
    @Override
    public ReconciliationRecord getReconciliationRecordByNo(String reconciliationNo) {
        return reconciliationRecordMapper.selectByReconciliationNo(reconciliationNo);
    }
    
    @Override
    public List<ReconciliationRecord> getReconciliationRecordsByDateAndType(String reconciliationDate, String reconciliationType, String paymentChannel) {
        return reconciliationRecordMapper.selectByDateAndType(reconciliationDate, reconciliationType, paymentChannel);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReconciliationRecord initiateReconciliation(String reconciliationNo) {
        ReconciliationRecord record = reconciliationRecordMapper.selectByReconciliationNo(reconciliationNo);
        if (record == null) {
            throw new RuntimeException("对账记录不存在: " + reconciliationNo);
        }
        
        if (!"PENDING".equals(record.getStatus())) {
            throw new RuntimeException("对账任务状态错误，当前状态: " + record.getStatus());
        }
        
        try {
            // 更新对账状态为处理中
            reconciliationRecordMapper.updateStatus(record.getId(), "PROCESSING", null, null);
            record.setStatus("PROCESSING");
            
            // 执行对账逻辑
            performReconciliation(record);
            
            // 更新对账状态为完成
            reconciliationRecordMapper.updateStatus(record.getId(), "COMPLETED", LocalDateTime.now(), null);
            record.setStatus("COMPLETED");
            record.setEndTime(LocalDateTime.now());
            
            logger.info("对账完成: {}", reconciliationNo);
            return record;
        } catch (Exception e) {
            logger.error("对账失败: {}", reconciliationNo, e);
            reconciliationRecordMapper.updateStatus(record.getId(), "FAILED", LocalDateTime.now(), e.getMessage());
            record.setStatus("FAILED");
            record.setEndTime(LocalDateTime.now());
            record.setFailReason(e.getMessage());
            throw e;
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ReconciliationRecord> batchInitiateReconciliation(List<String> reconciliationNos) {
        List<ReconciliationRecord> results = new ArrayList<>();
        for (String reconciliationNo : reconciliationNos) {
            try {
                results.add(initiateReconciliation(reconciliationNo));
            } catch (Exception e) {
                logger.error("批量对账失败: {}", reconciliationNo, e);
            }
        }
        return results;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ReconciliationRecord> autoReconciliation(String reconciliationDate) {
        if (reconciliationDate == null) {
            reconciliationDate = LocalDate.now().minusDays(1).format(DATE_FORMATTER);
        }
        
        // 创建对账任务
        ReconciliationRecord record = createReconciliationTask(reconciliationDate, "AUTO", "ALL");
        
        // 执行对账
        initiateReconciliation(record.getReconciliationNo());
        
        List<ReconciliationRecord> results = new ArrayList<>();
        results.add(record);
        return results;
    }
    
    @Override
    public List<ReconciliationRecord> getPendingReconciliations() {
        return reconciliationRecordMapper.selectPendingReconciliations();
    }
    
    @Override
    public List<ReconciliationDiscrepancy> getDiscrepanciesByReconciliationNo(String reconciliationNo) {
        return reconciliationDiscrepancyMapper.selectByReconciliationNo(reconciliationNo);
    }
    
    @Override
    public List<ReconciliationDiscrepancy> getDiscrepanciesByStatus(String status) {
        return reconciliationDiscrepancyMapper.selectByStatus(status);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReconciliationDiscrepancy handleDiscrepancy(Long discrepancyId, String status, String handler, String handleRemark) {
        ReconciliationDiscrepancy discrepancy = reconciliationDiscrepancyMapper.selectById(discrepancyId);
        if (discrepancy == null) {
            throw new RuntimeException("差异记录不存在: " + discrepancyId);
        }
        
        reconciliationDiscrepancyMapper.updateHandleStatus(discrepancyId, status, handler, handleRemark);
        discrepancy.setStatus(status);
        discrepancy.setHandler(handler);
        discrepancy.setHandleRemark(handleRemark);
        discrepancy.setHandleTime(LocalDateTime.now());
        
        logger.info("处理差异记录成功: {}", discrepancyId);
        return discrepancy;
    }
    
    /**
     * 执行对账逻辑
     * @param record 对账记录
     */
    private void performReconciliation(ReconciliationRecord record) {
        String reconciliationDate = record.getReconciliationDate();
        String paymentChannel = record.getPaymentChannel();
        
        // 查询系统交易数据
        QueryWrapper<TransactionRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("DATE(create_time) = {0}", reconciliationDate);
        if (!"ALL".equals(paymentChannel)) {
            queryWrapper.eq("payment_channel", paymentChannel);
        }
        queryWrapper.eq("status", "SUCCESS");
        
        List<TransactionRecord> systemTransactions = transactionRecordMapper.selectList(queryWrapper);
        
        // 模拟渠道交易数据（实际应调用渠道API获取）
        List<Map<String, Object>> channelTransactions = mockChannelTransactions(reconciliationDate, paymentChannel);
        
        // 统计系统交易数据
        int systemTotalCount = systemTransactions.size();
        BigDecimal systemTotalAmount = systemTransactions.stream()
                .map(TransactionRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // 统计渠道交易数据
        int channelTotalCount = channelTransactions.size();
        BigDecimal channelTotalAmount = channelTransactions.stream()
                .map(t -> (BigDecimal) t.get("amount"))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // 比较交易数据
        String reconciliationResult;
        int discrepancyCount = 0;
        BigDecimal discrepancyAmount = BigDecimal.ZERO;
        
        if (systemTotalCount == channelTotalCount && systemTotalAmount.compareTo(channelTotalAmount) == 0) {
            reconciliationResult = "SUCCESS";
        } else {
            reconciliationResult = "FAILED";
            discrepancyCount = Math.abs(systemTotalCount - channelTotalCount);
            discrepancyAmount = systemTotalAmount.subtract(channelTotalAmount).abs();
            
            // 记录差异详情
            recordDiscrepancies(record, systemTransactions, channelTransactions);
        }
        
        // 更新对账结果
        reconciliationRecordMapper.updateReconciliationResult(record.getId(), reconciliationResult, discrepancyCount, discrepancyAmount);
        record.setReconciliationResult(reconciliationResult);
        record.setDiscrepancyCount(discrepancyCount);
        record.setDiscrepancyAmount(discrepancyAmount);
    }
    
    /**
     * 模拟渠道交易数据
     * @param reconciliationDate 对账日期
     * @param paymentChannel 支付渠道
     * @return 渠道交易数据
     */
    private List<Map<String, Object>> mockChannelTransactions(String reconciliationDate, String paymentChannel) {
        // 实际应调用渠道API获取交易数据
        // 这里模拟返回一些数据
        List<Map<String, Object>> transactions = new ArrayList<>();
        
        Map<String, Object> tx1 = new HashMap<>();
        tx1.put("transaction_no", "CH202401010001");
        tx1.put("amount", new BigDecimal("100.00"));
        tx1.put("transaction_type", "PAYMENT");
        transactions.add(tx1);
        
        Map<String, Object> tx2 = new HashMap<>();
        tx2.put("transaction_no", "CH202401010002");
        tx2.put("amount", new BigDecimal("200.00"));
        tx2.put("transaction_type", "PAYMENT");
        transactions.add(tx2);
        
        return transactions;
    }
    
    /**
     * 记录差异详情
     * @param record 对账记录
     * @param systemTransactions 系统交易数据
     * @param channelTransactions 渠道交易数据
     */
    private void recordDiscrepancies(ReconciliationRecord record, List<TransactionRecord> systemTransactions, List<Map<String, Object>> channelTransactions) {
        // 这里简化处理，实际应逐条比对交易记录
        // 记录系统有但渠道没有的交易
        for (TransactionRecord systemTx : systemTransactions) {
            boolean found = false;
            for (Map<String, Object> channelTx : channelTransactions) {
                if (systemTx.getTransactionNo().equals(channelTx.get("transaction_no"))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                ReconciliationDiscrepancy discrepancy = new ReconciliationDiscrepancy();
                discrepancy.setReconciliationNo(record.getReconciliationNo());
                discrepancy.setSystemTransactionNo(systemTx.getTransactionNo());
                discrepancy.setChannelTransactionNo(null);
                discrepancy.setTransactionType(systemTx.getTransactionType());
                discrepancy.setSystemAmount(systemTx.getAmount());
                discrepancy.setChannelAmount(BigDecimal.ZERO);
                discrepancy.setDiscrepancyType("MISSING_IN_CHANNEL");
                discrepancy.setDiscrepancyAmount(systemTx.getAmount());
                discrepancy.setStatus("PENDING");
                discrepancy.setCreateTime(LocalDateTime.now());
                discrepancy.setUpdateTime(LocalDateTime.now());
                
                reconciliationDiscrepancyMapper.insert(discrepancy);
            }
        }
        
        // 记录渠道有但系统没有的交易
        for (Map<String, Object> channelTx : channelTransactions) {
            boolean found = false;
            for (TransactionRecord systemTx : systemTransactions) {
                if (channelTx.get("transaction_no").equals(systemTx.getTransactionNo())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                ReconciliationDiscrepancy discrepancy = new ReconciliationDiscrepancy();
                discrepancy.setReconciliationNo(record.getReconciliationNo());
                discrepancy.setSystemTransactionNo(null);
                discrepancy.setChannelTransactionNo((String) channelTx.get("transaction_no"));
                discrepancy.setTransactionType((String) channelTx.get("transaction_type"));
                discrepancy.setSystemAmount(BigDecimal.ZERO);
                discrepancy.setChannelAmount((BigDecimal) channelTx.get("amount"));
                discrepancy.setDiscrepancyType("MISSING_IN_SYSTEM");
                discrepancy.setDiscrepancyAmount((BigDecimal) channelTx.get("amount"));
                discrepancy.setStatus("PENDING");
                discrepancy.setCreateTime(LocalDateTime.now());
                discrepancy.setUpdateTime(LocalDateTime.now());
                
                reconciliationDiscrepancyMapper.insert(discrepancy);
            }
        }
    }
}