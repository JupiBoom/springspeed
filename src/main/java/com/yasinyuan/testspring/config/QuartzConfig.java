package com.yasinyuan.testspring.config;

import com.yasinyuan.testspring.quartz.DailyReportJob;
import com.yasinyuan.testspring.quartz.UserSegmentUpdateJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    // 日报定时任务配置
    @Bean
    public JobDetail dailyReportJobDetail() {
        return JobBuilder.newJob(DailyReportJob.class)
                .withIdentity("dailyReportJob", "reportGroup")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger dailyReportTrigger() {
        // 每天凌晨0点执行
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 0 * * ?");

        return TriggerBuilder.newTrigger()
                .forJob(dailyReportJobDetail())
                .withIdentity("dailyReportTrigger", "reportGroup")
                .withSchedule(scheduleBuilder)
                .build();
    }

    // 用户分群更新定时任务配置
    @Bean
    public JobDetail userSegmentUpdateJobDetail() {
        return JobBuilder.newJob(UserSegmentUpdateJob.class)
                .withIdentity("userSegmentUpdateJob", "segmentGroup")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger userSegmentUpdateTrigger() {
        // 每天凌晨1点执行
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 1 * * ?");

        return TriggerBuilder.newTrigger()
                .forJob(userSegmentUpdateJobDetail())
                .withIdentity("userSegmentUpdateTrigger", "segmentGroup")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
