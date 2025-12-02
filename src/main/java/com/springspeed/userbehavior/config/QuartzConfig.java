package com.springspeed.userbehavior.config;

import com.springspeed.userbehavior.job.SessionCleanupJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz定时任务配置类
 * 配置用户行为分析系统的定时任务
 */
@Configuration
public class QuartzConfig {

    /**
     * 会话清理任务的JobDetail
     * @return JobDetail
     */
    @Bean
    public JobDetail sessionCleanupJobDetail() {
        return JobBuilder.newJob(SessionCleanupJob.class)
                .withIdentity("sessionCleanupJob", "userBehaviorJobGroup")
                .storeDurably()
                .build();
    }

    /**
     * 会话清理任务的Trigger
     * 配置为每5分钟执行一次
     * @param sessionCleanupJobDetail JobDetail
     * @return Trigger
     */
    @Bean
    public Trigger sessionCleanupTrigger(JobDetail sessionCleanupJobDetail) {
        // 配置每5分钟执行一次
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(5)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(sessionCleanupJobDetail)
                .withIdentity("sessionCleanupTrigger", "userBehaviorTriggerGroup")
                .withSchedule(scheduleBuilder)
                .build();
    }

    /**
     * 用户行为统计任务的JobDetail
     * 用于定期统计用户行为数据
     * @return JobDetail
     */
    @Bean
    public JobDetail userBehaviorStatisticsJobDetail() {
        // 这里可以配置用户行为统计任务的JobDetail
        // 由于目前还没有实现该任务，暂时返回null
        return null;
    }

    /**
     * 用户行为统计任务的Trigger
     * 配置为每天凌晨1点执行一次
     * @param userBehaviorStatisticsJobDetail JobDetail
     * @return Trigger
     */
    @Bean
    public Trigger userBehaviorStatisticsTrigger(JobDetail userBehaviorStatisticsJobDetail) {
        // 这里可以配置用户行为统计任务的Trigger
        // 由于目前还没有实现该任务，暂时返回null
        return null;
    }

    /**
     * 用户分群更新任务的JobDetail
     * 用于定期更新用户分群数据
     * @return JobDetail
     */
    @Bean
    public JobDetail userSegmentUpdateJobDetail() {
        // 这里可以配置用户分群更新任务的JobDetail
        // 由于目前还没有实现该任务，暂时返回null
        return null;
    }

    /**
     * 用户分群更新任务的Trigger
     * 配置为每天凌晨2点执行一次
     * @param userSegmentUpdateJobDetail JobDetail
     * @return Trigger
     */
    @Bean
    public Trigger userSegmentUpdateTrigger(JobDetail userSegmentUpdateJobDetail) {
        // 这里可以配置用户分群更新任务的Trigger
        // 由于目前还没有实现该任务，暂时返回null
        return null;
    }
}