package com.yasinyuan.testspring.reconciliation.config;

import com.yasinyuan.testspring.reconciliation.job.ReconciliationJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 对账定时任务配置
 * 配置每天凌晨3点执行对账任务
 */
@Configuration
public class ReconciliationQuartzConfig {
    
    /**
     * 创建对账任务的JobDetail
     */
    @Bean
    public JobDetail reconciliationJobDetail() {
        return JobBuilder.newJob(ReconciliationJob.class)
                .withIdentity("reconciliationJob", "reconciliationGroup")
                .storeDurably()
                .build();
    }
    
    /**
     * 创建对账任务的Trigger
     * 每天凌晨3点执行
     */
    @Bean
    public Trigger reconciliationTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 3 * * ?");
        
        return TriggerBuilder.newTrigger()
                .forJob(reconciliationJobDetail())
                .withIdentity("reconciliationTrigger", "reconciliationGroup")
                .withSchedule(scheduleBuilder)
                .build();
    }
}