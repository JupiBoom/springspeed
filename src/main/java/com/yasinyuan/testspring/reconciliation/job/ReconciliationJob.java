package com.yasinyuan.testspring.reconciliation.job;

import com.yasinyuan.testspring.reconciliation.service.ReconciliationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 对账定时任务
 * 每天凌晨3点执行前一天的对账任务
 */
@Component
public class ReconciliationJob implements Job {
    
    private static final Logger logger = LoggerFactory.getLogger(ReconciliationJob.class);
    
    @Autowired
    private ReconciliationService reconciliationService;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("开始执行自动对账任务");
        
        try {
            // 对账日期为昨天
            String reconciliationDate = LocalDate.now().minusDays(1).format(DATE_FORMATTER);
            
            // 执行自动对账
            reconciliationService.autoReconciliation(reconciliationDate);
            
            logger.info("自动对账任务执行完成: {}", reconciliationDate);
        } catch (Exception e) {
            logger.error("自动对账任务执行失败", e);
            throw new JobExecutionException(e);
        }
    }
}