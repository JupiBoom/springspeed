package com.ecommerce.config;

import com.ecommerce.job.DailyReportJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

@Configuration
public class SchedulerConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        
        // 配置Quartz属性
        Properties properties = new Properties();
        properties.setProperty("org.quartz.scheduler.instanceName", "EcommerceScheduler");
        properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
        properties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        properties.setProperty("org.quartz.threadPool.threadCount", "5");
        
        schedulerFactoryBean.setQuartzProperties(properties);
        schedulerFactoryBean.setJobFactory(new QuartzConfig());
        
        // 配置定时任务
        schedulerFactoryBean.setTriggers(dailyReportTrigger());
        
        return schedulerFactoryBean;
    }

    @Bean
    public JobDetail dailyReportJobDetail() {
        return JobBuilder.newJob(DailyReportJob.class)
                .withIdentity("dailyReportJob", "reportGroup")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger dailyReportTrigger() {
        // 每天凌晨1点执行
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 1 * * ?");
        
        return TriggerBuilder.newTrigger()
                .forJob(dailyReportJobDetail())
                .withIdentity("dailyReportTrigger", "reportGroup")
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}
