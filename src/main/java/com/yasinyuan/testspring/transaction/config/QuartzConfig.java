package com.yasinyuan.testspring.transaction.config;

import com.yasinyuan.testspring.transaction.job.PaymentTimeoutJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz定时任务配置
 * @author yinyuan
 */
@Configuration
public class QuartzConfig {
    
    /**
     * 支付超时处理任务触发器（每5分钟执行一次）
     */
    @Bean
    public Trigger paymentTimeoutTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/5 * * * ?");
        
        return TriggerBuilder.newTrigger()
                .forJob(paymentTimeoutJobDetail())
                .withIdentity("paymentTimeoutTrigger", "paymentGroup")
                .withSchedule(scheduleBuilder)
                .build();
    }
    
    @Bean
    public JobDetail paymentTimeoutJobDetail() {
        return JobBuilder.newJob(PaymentTimeoutJob.class)
                .withIdentity("paymentTimeoutJob", "paymentGroup")
                .storeDurably()
                .build();
    }
}
