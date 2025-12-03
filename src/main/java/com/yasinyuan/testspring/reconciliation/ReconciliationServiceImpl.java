package com.yasinyuan.testspring.reconciliation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对账服务实现类
 */
@Service
public class ReconciliationServiceImpl implements ReconciliationService {

    private static final Logger logger = LoggerFactory.getLogger(ReconciliationServiceImpl.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String downloadChannelBill(String payChannel, Date billDate) {
        // 模拟下载渠道对账单
        logger.info("下载渠道对账单: payChannel={}, billDate={}", payChannel, billDate);
        
        // 实际应该调用渠道API下载对账单
        String billContent = "模拟渠道对账单内容\n" +
                "流水号,金额,时间,状态\n" +
                "CH123456,100.00,2023-01-01 10:00:00,SUCCESS\n" +
                "CH123457,200.00,2023-01-01 11:00:00,SUCCESS\n" +
                "CH123458,300.00,2023-01-01 12:00:00,FAILED";
        
        return billContent;
    }

    @Override
    public List<ChannelTransactionRecord> parseChannelBill(String payChannel, String billContent) {
        // 模拟解析渠道对账单
        logger.info("解析渠道对账单: payChannel={}", payChannel);
        
        List<ChannelTransactionRecord> channelRecords = new ArrayList<>();
        
        // 实际应该根据不同渠道的对账单格式进行解析
        String[] lines = billContent.split("\\n");
        for (int i = 2; i < lines.length; i++) { // 跳过标题行和列名行
            String line = lines[i];
            String[] fields = line.split(",");
            
            ChannelTransactionRecord record = new ChannelTransactionRecord();
            record.setChannelPayNo(fields[0]);
            record.setAmount(new java.math.BigDecimal(fields[1]));
            // 实际应该解析时间字符串为Date类型
            record.setTransactionTime(new Date());
            record.setStatus(fields[3]);
            record.setServiceFee(new java.math.BigDecimal("0.00")); // 模拟手续费
            
            channelRecords.add(record);
        }
        
        return channelRecords;
    }

    @Override
    public List<SystemTransactionRecord> getSystemTransactionRecords(String payChannel, Date billDate) {
        // 模拟获取系统交易记录
        logger.info("获取系统交易记录: payChannel={}, billDate={}", payChannel, billDate);
        
        List<SystemTransactionRecord> systemRecords = new ArrayList<>();
        
        // 实际应该从数据库或Redis中查询系统交易记录
        SystemTransactionRecord record1 = new SystemTransactionRecord();
        record1.setSystemPayNo("SP123456");
        record1.setChannelPayNo("CH123456");
        record1.setAmount(new java.math.BigDecimal("100.00"));
        record1.setTransactionTime(new Date());
        record1.setStatus("SUCCESS");
        record1.setServiceFee(new java.math.BigDecimal("1.00"));
        
        SystemTransactionRecord record2 = new SystemTransactionRecord();
        record2.setSystemPayNo("SP123457");
        record2.setChannelPayNo("CH123457");
        record2.setAmount(new java.math.BigDecimal("200.00"));
        record2.setTransactionTime(new Date());
        record2.setStatus("SUCCESS");
        record2.setServiceFee(new java.math.BigDecimal("2.00"));
        
        SystemTransactionRecord record3 = new SystemTransactionRecord();
        record3.setSystemPayNo("SP123458");
        record3.setChannelPayNo("CH123458");
        record3.setAmount(new java.math.BigDecimal("300.00"));
        record3.setTransactionTime(new Date());
        record3.setStatus("FAILED");
        record3.setServiceFee(new java.math.BigDecimal("0.00"));
        
        systemRecords.add(record1);
        systemRecords.add(record2);
        systemRecords.add(record3);
        
        return systemRecords;
    }

    @Override
    public ReconciliationResult autoReconcile(String payChannel, Date billDate) {
        // 自动对账逻辑
        logger.info("开始自动对账: payChannel={}, billDate={}", payChannel, billDate);
        
        ReconciliationResult result = new ReconciliationResult();
        List<ReconciliationRecord> reconciliationRecords = new ArrayList<>();
        
        // 1. 下载并解析渠道对账单
        String billContent = downloadChannelBill(payChannel, billDate);
        List<ChannelTransactionRecord> channelRecords = parseChannelBill(payChannel, billContent);
        
        // 2. 获取系统交易记录
        List<SystemTransactionRecord> systemRecords = getSystemTransactionRecords(payChannel, billDate);
        
        // 3. 进行对账匹配
        Map<String, SystemTransactionRecord> systemRecordMap = new ConcurrentHashMap<>();
        for (SystemTransactionRecord systemRecord : systemRecords) {
            systemRecordMap.put(systemRecord.getChannelPayNo(), systemRecord);
        }
        
        int matchedCount = 0;
        double matchedAmount = 0.0;
        
        for (ChannelTransactionRecord channelRecord : channelRecords) {
            String channelPayNo = channelRecord.getChannelPayNo();
            SystemTransactionRecord systemRecord = systemRecordMap.get(channelPayNo);
            
            ReconciliationRecord reconciliationRecord = new ReconciliationRecord();
            reconciliationRecord.setReconciliationDate(billDate);
            reconciliationRecord.setPayChannel(payChannel);
            reconciliationRecord.setChannelPayNo(channelPayNo);
            reconciliationRecord.setChannelAmount(channelRecord.getAmount());
            
            if (systemRecord != null) {
                reconciliationRecord.setSystemPayNo(systemRecord.getSystemPayNo());
                reconciliationRecord.setSystemAmount(systemRecord.getAmount());
                
                // 匹配金额是否一致
                if (channelRecord.getAmount().compareTo(systemRecord.getAmount()) == 0) {
                    reconciliationRecord.setStatus("MATCHED");
                    matchedCount++;
                    matchedAmount += channelRecord.getAmount().doubleValue();
                } else {
                    reconciliationRecord.setStatus("AMOUNT_MISMATCH");
                    reconciliationRecord.setDifferenceDescription("金额不匹配: 渠道金额=" + channelRecord.getAmount() + ", 系统金额=" + systemRecord.getAmount());
                }
            } else {
                reconciliationRecord.setStatus("CHANNEL_UNILATERAL");
                reconciliationRecord.setDifferenceDescription("渠道单边账: 系统中不存在该交易记录");
            }
            
            reconciliationRecord.setCreateTime(new Date());
            reconciliationRecord.setUpdateTime(new Date());
            
            reconciliationRecords.add(reconciliationRecord);
        }
        
        // 检查系统单边账
        int systemUnilateralCount = 0;
        double systemUnilateralAmount = 0.0;
        
        for (SystemTransactionRecord systemRecord : systemRecords) {
            boolean found = false;
            for (ChannelTransactionRecord channelRecord : channelRecords) {
                if (systemRecord.getChannelPayNo().equals(channelRecord.getChannelPayNo())) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                ReconciliationRecord reconciliationRecord = new ReconciliationRecord();
                reconciliationRecord.setReconciliationDate(billDate);
                reconciliationRecord.setPayChannel(payChannel);
                reconciliationRecord.setSystemPayNo(systemRecord.getSystemPayNo());
                reconciliationRecord.setSystemAmount(systemRecord.getAmount());
                reconciliationRecord.setStatus("SYSTEM_UNILATERAL");
                reconciliationRecord.setDifferenceDescription("系统单边账: 渠道中不存在该交易记录");
                reconciliationRecord.setCreateTime(new Date());
                reconciliationRecord.setUpdateTime(new Date());
                
                reconciliationRecords.add(reconciliationRecord);
                
                systemUnilateralCount++;
                systemUnilateralAmount += systemRecord.getAmount().doubleValue();
            }
        }
        
        // 计算金额不匹配笔数
        int amountMismatchCount = 0;
        for (ReconciliationRecord record : reconciliationRecords) {
            if ("AMOUNT_MISMATCH".equals(record.getStatus())) {
                amountMismatchCount++;
            }
        }
        
        // 填充对账结果
        result.setSuccess(systemUnilateralCount == 0 && amountMismatchCount == 0);
        result.setSystemTotalCount(systemRecords.size());
        result.setSystemTotalAmount(getTotalAmount(systemRecords));
        result.setChannelTotalCount(channelRecords.size());
        result.setChannelTotalAmount(getTotalAmount(channelRecords));
        result.setMatchedCount(matchedCount);
        result.setMatchedAmount(matchedAmount);
        result.setSystemUnilateralCount(systemUnilateralCount);
        result.setSystemUnilateralAmount(systemUnilateralAmount);
        result.setChannelUnilateralCount(getChannelUnilateralCount(reconciliationRecords));
        result.setChannelUnilateralAmount(getChannelUnilateralAmount(reconciliationRecords));
        result.setAmountMismatchCount(amountMismatchCount);
        result.setReconciliationRecords(reconciliationRecords);
        
        logger.info("自动对账完成: 结果={}", result.isSuccess());
        
        // 实际应该将对账记录保存到数据库
        return result;
    }

    @Override
    public boolean manualReconcile(ReconciliationRecord reconciliationRecord) {
        // 手动对账逻辑
        logger.info("开始手动对账: 对账记录ID={}", reconciliationRecord.getId());
        
        // 实际应该根据具体情况修改对账状态
        reconciliationRecord.setStatus("MANUAL_MATCHED");
        reconciliationRecord.setUpdateTime(new Date());
        
        // 实际应该将修改后的对账记录保存到数据库
        logger.info("手动对账完成: 对账记录ID={}", reconciliationRecord.getId());
        
        return true;
    }

    @Override
    public List<ReconciliationRecord> queryReconciliationRecords(String payChannel, Date billDate, String status) {
        // 模拟查询对账记录
        logger.info("查询对账记录: payChannel={}, billDate={}, status={}", payChannel, billDate, status);
        
        List<ReconciliationRecord> reconciliationRecords = new ArrayList<>();
        
        // 实际应该从数据库中查询对账记录
        ReconciliationRecord record1 = new ReconciliationRecord();
        record1.setId(1L);
        record1.setReconciliationDate(billDate);
        record1.setPayChannel(payChannel);
        record1.setSystemPayNo("SP123456");
        record1.setChannelPayNo("CH123456");
        record1.setSystemAmount(new java.math.BigDecimal("100.00"));
        record1.setChannelAmount(new java.math.BigDecimal("100.00"));
        record1.setStatus("MATCHED");
        record1.setCreateTime(new Date());
        record1.setUpdateTime(new Date());
        
        ReconciliationRecord record2 = new ReconciliationRecord();
        record2.setId(2L);
        record2.setReconciliationDate(billDate);
        record2.setPayChannel(payChannel);
        record2.setSystemPayNo("SP123457");
        record2.setChannelPayNo("CH123457");
        record2.setSystemAmount(new java.math.BigDecimal("200.00"));
        record2.setChannelAmount(new java.math.BigDecimal("200.00"));
        record2.setStatus("MATCHED");
        record2.setCreateTime(new Date());
        record2.setUpdateTime(new Date());
        
        reconciliationRecords.add(record1);
        reconciliationRecords.add(record2);
        
        return reconciliationRecords;
    }

    @Override
    public String generateReconciliationReport(String payChannel, Date billDate) {
        // 模拟生成对账报告
        logger.info("生成对账报告: payChannel={}, billDate={}", payChannel, billDate);
        
        // 实际应该根据对账记录生成详细的对账报告
        String report = "对账报告\n" +
                "------------------------------------------------------\n" +
                "支付渠道: " + payChannel + "\n" +
                "对账日期: " + billDate + "\n" +
                "------------------------------------------------------\n" +
                "系统交易总笔数: 3\n" +
                "系统交易总金额: 600.00\n" +
                "渠道交易总笔数: 3\n" +
                "渠道交易总金额: 600.00\n" +
                "匹配成功笔数: 3\n" +
                "匹配成功金额: 600.00\n" +
                "系统单边账笔数: 0\n" +
                "渠道单边账笔数: 0\n" +
                "金额不匹配笔数: 0\n" +
                "------------------------------------------------------\n" +
                "对账结果: 成功\n";
        
        return report;
    }

    // 辅助方法：计算交易记录列表的总金额
    private double getTotalAmount(List<? extends Object> records) {
        double total = 0.0;
        for (Object record : records) {
            if (record instanceof SystemTransactionRecord) {
                total += ((SystemTransactionRecord) record).getAmount().doubleValue();
            } else if (record instanceof ChannelTransactionRecord) {
                total += ((ChannelTransactionRecord) record).getAmount().doubleValue();
            }
        }
        return total;
    }

    // 辅助方法：获取渠道单边账笔数
    private int getChannelUnilateralCount(List<ReconciliationRecord> reconciliationRecords) {
        int count = 0;
        for (ReconciliationRecord record : reconciliationRecords) {
            if ("CHANNEL_UNILATERAL".equals(record.getStatus())) {
                count++;
            }
        }
        return count;
    }

    // 辅助方法：获取渠道单边账金额
    private double getChannelUnilateralAmount(List<ReconciliationRecord> reconciliationRecords) {
        double amount = 0.0;
        for (ReconciliationRecord record : reconciliationRecords) {
            if ("CHANNEL_UNILATERAL".equals(record.getStatus())) {
                amount += record.getChannelAmount().doubleValue();
            }
        }
        return amount;
    }
}
