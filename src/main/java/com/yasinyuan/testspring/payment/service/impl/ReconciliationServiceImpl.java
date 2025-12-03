package com.yasinyuan.testspring.payment.service.impl;

import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.payment.channel.PaymentChannelFactory;
import com.yasinyuan.testspring.payment.channel.PaymentChannelInterface;
import com.yasinyuan.testspring.payment.dao.ReconciliationRecordMapper;
import com.yasinyuan.testspring.payment.dao.PaymentTransactionMapper;
import com.yasinyuan.testspring.payment.enums.PaymentChannel;
import com.yasinyuan.testspring.payment.enums.PaymentStatus;
import com.yasinyuan.testspring.payment.model.PaymentTransaction;
import com.yasinyuan.testspring.payment.model.ReconciliationRecord;
import com.yasinyuan.testspring.payment.service.ReconciliationService;
import com.yasinyuan.testspring.tools.DateUtils;
import com.yasinyuan.testspring.tools.exception.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 对账服务实现类
 */
@Service
public class ReconciliationServiceImpl extends AbstractService<ReconciliationRecord> implements ReconciliationService {
    
    @Autowired
    private ReconciliationRecordMapper reconciliationRecordMapper;
    
    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;
    
    @Autowired
    private PaymentChannelFactory paymentChannelFactory;
    
    @Override
    public String downloadChannelBill(PaymentChannel channel, Date date) {
        PaymentChannelInterface paymentChannel = paymentChannelFactory.getPaymentChannel(channel);
        String dateStr = DateUtils.format(date, "yyyyMMdd");
        String billContent = paymentChannel.downloadBill(dateStr);
        
        // 保存对账单到文件
        String filePath = "bill/" + channel.name().toLowerCase() + "_" + dateStr + ".csv";
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(billContent);
        } catch (IOException e) {
            throw new RRException("保存对账单失败: " + e.getMessage());
        }
        
        return filePath;
    }
    
    @Override
    public String generateSystemBill(PaymentChannel channel, Date date) {
        Date startTime = DateUtils.getStartOfDay(date);
        Date endTime = DateUtils.getEndOfDay(date);
        
        // 查询系统交易记录
        List<PaymentTransaction> transactions = paymentTransactionMapper.selectStatistics(
            null, startTime, endTime, PaymentStatus.SUCCESS);
        
        // 生成系统对账单
        StringBuilder billContent = new StringBuilder();
        billContent.append("交易时间,支付流水号,订单号,交易金额,支付渠道\n");
        
        for (PaymentTransaction transaction : transactions) {
            billContent.append(DateUtils.format(transaction.getPayTime(), "yyyy-MM-dd HH:mm:ss"))
                .append(",").append(transaction.getPaymentNo())
                .append(",").append(transaction.getOrderNo())
                .append(",").append(transaction.getAmount())
                .append(",").append(transaction.getChannel().name())
                .append("\n");
        }
        
        // 保存对账单到文件
        String dateStr = DateUtils.format(date, "yyyyMMdd");
        String filePath = "bill/system_" + channel.name().toLowerCase() + "_" + dateStr + ".csv";
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(billContent.toString());
        } catch (IOException e) {
            throw new RRException("生成系统对账单失败: " + e.getMessage());
        }
        
        return filePath;
    }
    
    @Override
    @Transactional
    public ReconciliationRecord executeReconciliation(PaymentChannel channel, Date date) {
        // 检查是否已对账
        ReconciliationRecord existingRecord = reconciliationRecordMapper.selectByDateAndChannel(date, channel);
        if (existingRecord != null) {
            throw new RRException("该日期的对账记录已存在");
        }
        
        // 创建对账记录
        ReconciliationRecord reconciliationRecord = new ReconciliationRecord();
        reconciliationRecord.setReconciliationDate(date);
        reconciliationRecord.setChannel(channel);
        reconciliationRecord.setStatus(1); // 对账中
        reconciliationRecord.setCreateTime(new Date());
        reconciliationRecord.setUpdateTime(new Date());
        
        reconciliationRecordMapper.insert(reconciliationRecord);
        
        try {
            // 下载渠道对账单
            String channelBillPath = downloadChannelBill(channel, date);
            
            // 生成系统对账单
            String systemBillPath = generateSystemBill(channel, date);
            
            // 执行对账逻辑
            // 这里简化处理，实际应对比两个对账单的交易记录
            Date startTime = DateUtils.getStartOfDay(date);
            Date endTime = DateUtils.getEndOfDay(date);
            
            // 统计渠道对账单数据（模拟）
            int channelTotalCount = 100;
            BigDecimal channelTotalAmount = new BigDecimal("10000.00");
            
            // 统计系统对账单数据
            List<PaymentTransaction> transactions = paymentTransactionMapper.selectStatistics(
                null, startTime, endTime, PaymentStatus.SUCCESS);
            
            int systemTotalCount = transactions.size();
            BigDecimal systemTotalAmount = BigDecimal.ZERO;
            for (PaymentTransaction transaction : transactions) {
                systemTotalAmount = systemTotalAmount.add(transaction.getAmount());
            }
            
            // 计算差异
            int diffCount = Math.abs(channelTotalCount - systemTotalCount);
            BigDecimal diffAmount = channelTotalAmount.subtract(systemTotalAmount).abs();
            
            // 更新对账记录
            reconciliationRecord.setChannelTotalCount(channelTotalCount);
            reconciliationRecord.setChannelTotalAmount(channelTotalAmount);
            reconciliationRecord.setSystemTotalCount(systemTotalCount);
            reconciliationRecord.setSystemTotalAmount(systemTotalAmount);
            reconciliationRecord.setDiffCount(diffCount);
            reconciliationRecord.setDiffAmount(diffAmount);
            reconciliationRecord.setBillFilePath(channelBillPath + "," + systemBillPath);
            
            // 设置对账结果状态
            if (diffCount == 0 && diffAmount.compareTo(BigDecimal.ZERO) == 0) {
                reconciliationRecord.setStatus(2); // 对账成功
                reconciliationRecord.setResultDesc("对账成功，无差异");
            } else {
                reconciliationRecord.setStatus(3); // 对账失败
                reconciliationRecord.setResultDesc("对账失败，存在差异");
            }
            
            reconciliationRecord.setUpdateTime(new Date());
            reconciliationRecordMapper.updateByPrimaryKey(reconciliationRecord);
            
            return reconciliationRecord;
        } catch (Exception e) {
            // 更新对账记录为失败状态
            reconciliationRecord.setStatus(3);
            reconciliationRecord.setResultDesc("对账失败: " + e.getMessage());
            reconciliationRecord.setUpdateTime(new Date());
            reconciliationRecordMapper.updateByPrimaryKey(reconciliationRecord);
            
            throw new RRException("对账失败: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public List<ReconciliationRecord> batchExecuteReconciliation(Date date) {
        List<ReconciliationRecord> results = new ArrayList<>();
        
        // 对所有支付渠道执行对账
        for (PaymentChannel channel : PaymentChannel.values()) {
            try {
                ReconciliationRecord record = executeReconciliation(channel, date);
                results.add(record);
            } catch (Exception e) {
                // 记录失败日志，继续处理下一个渠道
                e.printStackTrace();
            }
        }
        
        return results;
    }
    
    @Override
    public List<ReconciliationRecord> queryReconciliations(PaymentChannel channel, Date date, Integer status, int page, int size) {
        // 这里可以实现复杂的查询逻辑，目前简化处理
        return reconciliationRecordMapper.selectAll();
    }
    
    @Override
    public List<String> getReconciliationDiff(Long reconciliationId) {
        List<String> diffDetails = new ArrayList<>();
        
        // 这里简化处理，实际应返回具体的差异记录
        diffDetails.add("交易流水号: PAY202301010001, 渠道金额: 100.00, 系统金额: 99.00");
        diffDetails.add("交易流水号: PAY202301010002, 渠道存在, 系统不存在");
        
        return diffDetails;
    }
}
