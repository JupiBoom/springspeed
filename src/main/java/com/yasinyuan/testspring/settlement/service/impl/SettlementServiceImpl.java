package com.yasinyuan.testspring.settlement.service.impl;

import com.yasinyuan.testspring.settlement.dao.SettlementRecordMapper;
import com.yasinyuan.testspring.settlement.dao.SettlementAccountMapper;
import com.yasinyuan.testspring.settlement.model.SettlementRecord;
import com.yasinyuan.testspring.settlement.model.SettlementAccount;
import com.yasinyuan.testspring.settlement.service.SettlementService;
import com.yasinyuan.testspring.settlement.service.AccountService;
import com.yasinyuan.testspring.settlement.util.SettlementNoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 结算服务实现类
 */
@Service
public class SettlementServiceImpl implements SettlementService {
    
    @Autowired
    private SettlementRecordMapper settlementRecordMapper;
    
    @Autowired
    private SettlementAccountMapper settlementAccountMapper;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private SettlementNoGenerator settlementNoGenerator;
    
    @Override
    @Transactional
    public SettlementRecord createSettlementRecord(Long ownerId, Long accountId, String settlementType, 
                                                   String settlementPeriod, BigDecimal totalAmount, 
                                                   BigDecimal feeAmount, BigDecimal actualAmount) {
        SettlementRecord record = new SettlementRecord();
        record.setSettlementNo(settlementNoGenerator.generateSettlementNo());
        record.setOwnerId(ownerId);
        record.setAccountId(accountId);
        record.setSettlementType(settlementType);
        record.setSettlementPeriod(settlementPeriod);
        record.setTotalAmount(totalAmount);
        record.setFeeAmount(feeAmount);
        record.setActualAmount(actualAmount);
        record.setStatus("PENDING");
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        
        settlementRecordMapper.insert(record);
        return record;
    }
    
    @Override
    public SettlementRecord getSettlementRecordByNo(String settlementNo) {
        return settlementRecordMapper.selectBySettlementNo(settlementNo);
    }
    
    @Override
    public List<SettlementRecord> getSettlementRecordsByOwnerId(Long ownerId, String status, 
                                                                 LocalDateTime startDate, LocalDateTime endDate) {
        return settlementRecordMapper.selectByOwnerId(ownerId, status, startDate, endDate);
    }
    
    @Override
    @Transactional
    public SettlementRecord updateSettlementStatus(String settlementNo, String status, 
                                                   String bankFlowNo, String failReason) {
        SettlementRecord record = getSettlementRecordByNo(settlementNo);
        if (record == null) {
            throw new RuntimeException("结算记录不存在: " + settlementNo);
        }
        
        int rows = settlementRecordMapper.updateStatus(record.getId(), status, bankFlowNo, failReason);
        if (rows == 0) {
            throw new RuntimeException("更新结算状态失败: " + settlementNo);
        }
        
        return getSettlementRecordByNo(settlementNo);
    }
    
    @Override
    @Transactional
    public SettlementRecord initiateSettlement(String settlementNo) {
        SettlementRecord record = getSettlementRecordByNo(settlementNo);
        if (record == null) {
            throw new RuntimeException("结算记录不存在: " + settlementNo);
        }
        
        if (!"PENDING".equals(record.getStatus())) {
            throw new RuntimeException("结算记录状态不允许发起结算: " + settlementNo + ", 当前状态: " + record.getStatus());
        }
        
        // 更新结算状态为处理中
        updateSettlementStatus(settlementNo, "PROCESSING", null, null);
        
        try {
            // 调用银行接口进行结算（此处为模拟，实际需要调用真实的银行接口）
            String bankFlowNo = simulateBankSettlement(record);
            
            // 更新结算状态为成功
            record = updateSettlementStatus(settlementNo, "SUCCESS", bankFlowNo, null);
            
            // 减少结算账户的待结算金额
            accountService.decreaseSettlementAccountPendingAmount(record.getAccountId(), record.getActualAmount());
            
            return record;
        } catch (Exception e) {
            // 更新结算状态为失败
            updateSettlementStatus(settlementNo, "FAIL", null, e.getMessage());
            throw new RuntimeException("结算失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    @Transactional
    public List<SettlementRecord> batchInitiateSettlement(List<String> settlementNos) {
        List<SettlementRecord> results = new ArrayList<>();
        for (String settlementNo : settlementNos) {
            try {
                SettlementRecord record = initiateSettlement(settlementNo);
                results.add(record);
            } catch (Exception e) {
                // 记录失败的结算，但继续处理其他结算
                e.printStackTrace();
            }
        }
        return results;
    }
    
    @Override
    @Transactional
    public List<SettlementRecord> autoSettlement() {
        // 查询所有符合结算条件的结算账户
        List<SettlementAccount> accounts = accountService.getSettlementAccountsReadyForSettlement();
        List<SettlementRecord> results = new ArrayList<>();
        
        for (SettlementAccount account : accounts) {
            try {
                // 检查是否达到结算阈值
                if (account.getPendingAmount().compareTo(account.getSettlementThreshold()) >= 0) {
                    // 创建结算记录
                    String settlementPeriod = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    SettlementRecord record = createSettlementRecord(
                            account.getOwnerId(),
                            account.getId(),
                            account.getAccountType(),
                            settlementPeriod,
                            account.getPendingAmount(),
                            BigDecimal.ZERO, // 手续费暂时设为0，实际需要根据规则计算
                            account.getPendingAmount()
                    );
                    
                    // 发起结算
                    record = initiateSettlement(record.getSettlementNo());
                    results.add(record);
                }
            } catch (Exception e) {
                // 记录失败的结算，但继续处理其他账户
                e.printStackTrace();
            }
        }
        
        return results;
    }
    
    @Override
    public List<SettlementRecord> getPendingSettlements() {
        return settlementRecordMapper.selectPendingSettlements();
    }
    
    /**
     * 模拟银行结算接口
     * @param record 结算记录
     * @return 银行流水号
     */
    private String simulateBankSettlement(SettlementRecord record) {
        // 模拟银行结算处理时间
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // 生成模拟的银行流水号
        return "BANK" + System.currentTimeMillis();
    }
}