package com.yasinyuan.testspring.settlement.config;

import com.yasinyuan.testspring.settlement.job.SettlementJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 结算定时任务配置
 */
@Configuration
public class SettlementQuartzConfig {
    
    /**
     * 创建结算任务的JobDetail
     */
    @Bean
    public JobDetail settlementJobDetail() {
        return JobBuilder.newJob(SettlementJob.class)
                .withIdentity("settlementJob", "settlementGroup")
                .storeDurably()
                .build();
    }
    
    /**
     * 创建结算任务的Trigger
     * 每天凌晨2点执行一次
     */
    @Bean
    public Trigger settlementJobTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 2 * * ?");
        
        return TriggerBuilder.newTrigger()
                .forJob(settlementJobDetail())
                .withIdentity("settlementTrigger", "settlementGroup")
                .withSchedule(scheduleBuilder)
                .build();
    }
}