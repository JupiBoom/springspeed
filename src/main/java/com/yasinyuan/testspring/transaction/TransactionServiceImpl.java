package com.yasinyuan.testspring.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 交易服务实现类
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 保存交易记录实现
     */
    @Override
    public boolean saveTransactionRecord(TransactionRecord transactionRecord) {
        try {
            // 这里简化实现，实际项目中应该将交易记录保存到数据库中
            // 为了演示，我们将交易记录的关键信息保存到Redis中
            String key = "transaction:" + transactionRecord.getPayNo();
            redisTemplate.opsForValue().set(key, transactionRecord.toString(), 24, TimeUnit.HOURS);

            return true;
        } catch (Exception e) {
            // 处理异常，返回保存失败
            return false;
        }
    }

    /**
     * 根据订单ID查询交易记录实现
     */
    @Override
    public List<TransactionRecord> queryByOrderId(String orderId) {
        try {
            // 从Redis中查询交易记录的关键信息
            String key = "transaction_order:" + orderId;
            String transactionStr = redisTemplate.opsForValue().get(key);

            List<TransactionRecord> records = new ArrayList<>();

            if (transactionStr != null) {
                // 解析交易记录字符串，获取支付流水号
                String payNo = transactionStr.split(",")[0];
                // 根据支付流水号查询完整的交易记录
                TransactionRecord record = queryByPayNo(payNo);
                if (record != null) {
                    records.add(record);
                }
            } else {
                // 模拟从数据库中查询交易记录
                TransactionRecord record = new TransactionRecord();
                record.setId(1L);
                record.setOrderId(orderId);
                record.setPayNo("SP123456");
                record.setAmount(new BigDecimal("100.00"));
                record.setPayChannel("WECHAT_PAY");
                record.setStatus("SUCCESS");
                record.setCreateTime(new Date());
                record.setUpdateTime(new Date());
                records.add(record);
            }

            return records;
        } catch (Exception e) {
            // 处理异常，返回查询失败
            return null;
        }
    }

    /**
     * 根据支付流水号查询交易记录实现
     */
    @Override
    public TransactionRecord queryByPayNo(String payNo) {
        try {
            // 这里简化实现，实际项目中应该从数据库中查询交易记录
            // 为了演示，我们从Redis中获取交易记录的关键信息
            String key = "transaction:" + payNo;
            String transactionStr = redisTemplate.opsForValue().get(key);

            if (transactionStr != null) {
                // 这里简化实现，实际项目中应该将字符串解析为TransactionRecord对象
                TransactionRecord transactionRecord = new TransactionRecord();
                transactionRecord.setPayNo(payNo);
                return transactionRecord;
            } else {
                return null;
            }
        } catch (Exception e) {
            // 处理异常，返回查询失败
            return null;
        }
    }

    /**
     * 根据时间范围查询交易记录实现
     */
    @Override
    public List<TransactionRecord> queryByTimeRange(Date startTime, Date endTime) {
        try {
            // 模拟从数据库中查询指定时间范围内的交易记录
            List<TransactionRecord> records = new ArrayList<>();

            TransactionRecord record1 = new TransactionRecord();
            record1.setId(1L);
            record1.setOrderId("ORDER123456");
            record1.setPayNo("SP123456");
            record1.setAmount(new BigDecimal("100.00"));
            record1.setPayChannel("WECHAT_PAY");
            record1.setStatus("SUCCESS");
            record1.setCreateTime(startTime);
            record1.setUpdateTime(startTime);

            TransactionRecord record2 = new TransactionRecord();
            record2.setId(2L);
            record2.setOrderId("ORDER123457");
            record2.setPayNo("SP123457");
            record2.setAmount(new BigDecimal("200.00"));
            record2.setPayChannel("ALIPAY");
            record2.setStatus("SUCCESS");
            record2.setCreateTime(endTime);
            record2.setUpdateTime(endTime);

            records.add(record1);
            records.add(record2);

            return records;
        } catch (Exception e) {
            // 处理异常，返回查询失败
            return null;
        }
    }

    /**
     * 更新交易记录状态实现
     */
    @Override
    public boolean updateTransactionStatus(String payNo, String status) {
        try {
            // 这里简化实现，实际项目中应该更新数据库中的交易记录状态
            // 为了演示，我们从Redis中获取交易记录，更新状态后再保存到Redis中
            TransactionRecord transactionRecord = queryByPayNo(payNo);
            if (transactionRecord != null) {
                transactionRecord.setStatus(status);
                transactionRecord.setUpdateTime(new Date());
                return saveTransactionRecord(transactionRecord);
            } else {
                return false;
            }
        } catch (Exception e) {
            // 处理异常，返回更新失败
            return false;
        }
    }

    /**
     * 生成全局唯一的交易流水号实现
     */
    @Override
    public String generateTransactionNo() {
        // 生成规则：前缀 + 时间戳 + 自增序列
        // 这里使用Redis的INCR命令生成自增序列，确保全局唯一
        String prefix = "TXN";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String sequence = redisTemplate.opsForValue().increment("transaction:sequence", 1).toString();

        // 将自增序列补足6位
        sequence = String.format("%06d", Integer.parseInt(sequence));

        // 拼接成交易流水号
        return prefix + timestamp + sequence;
    }
}
