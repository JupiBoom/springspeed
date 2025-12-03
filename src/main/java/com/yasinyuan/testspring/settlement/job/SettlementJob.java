package com.yasinyuan.testspring.settlement.job;

import com.yasinyuan.testspring.settlement.service.SettlementService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 结算定时任务
 * 自动处理符合条件的结算账户
 */
@Component
public class SettlementJob implements Job {
    
    private static final Logger logger = LoggerFactory.getLogger(SettlementJob.class);
    
    @Autowired
    private SettlementService settlementService;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("开始执行自动结算任务...");
        
        try {
            // 执行自动结算
            settlementService.autoSettlement();
            logger.info("自动结算任务执行完成");
        } catch (Exception e) {
            logger.error("自动结算任务执行失败", e);
            throw new JobExecutionException(e);
        }
    }
}