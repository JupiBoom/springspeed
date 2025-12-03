package com.yasinyuan.testspring.transaction.job;

import com.yasinyuan.testspring.payment.enums.PaymentStatus;
import com.yasinyuan.testspring.transaction.model.TransactionRecord;
import com.yasinyuan.testspring.transaction.service.TransactionRecordService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 支付超时处理定时任务
 * @author yinyuan
 */
@Component
public class PaymentTimeoutJob implements Job {
    
    @Autowired
    private TransactionRecordService transactionRecordService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("开始执行支付超时处理任务...");
        
        List<TransactionRecord> timeoutTransactions = transactionRecordService.findTimeoutTransactions();
        
        for (TransactionRecord record : timeoutTransactions) {
            try {
                // 更新支付状态为已关闭
                transactionRecordService.updatePaymentStatus(record.getTransactionNo(), PaymentStatus.CLOSED.name());
                System.out.println("处理超时交易: " + record.getTransactionNo() + ", 订单号: " + record.getOrderNo());
            } catch (Exception e) {
                System.err.println("处理超时交易失败: " + record.getTransactionNo() + ", 错误: " + e.getMessage());
            }
        }
        
        System.out.println("支付超时处理任务完成，共处理 " + timeoutTransactions.size() + " 条记录");
    }
}
