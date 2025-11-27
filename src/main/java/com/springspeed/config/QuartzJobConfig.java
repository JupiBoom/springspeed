package com.springspeed.config;

import com.springspeed.job.RecommendationJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz Job Configuration
 * 定时任务配置类
 */
@Configuration
public class QuartzJobConfig {

    /**
     * 用户画像更新任务
     * 每天凌晨2点执行
     */
    @Bean
    public JobDetail updateUserProfilesJobDetail() {
        return JobBuilder.newJob(RecommendationJob.class)
                .withIdentity("updateUserProfilesJob", "recommendationGroup")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger updateUserProfilesTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 2 * * ?");

        return TriggerBuilder.newTrigger()
                .forJob(updateUserProfilesJobDetail())
                .withIdentity("updateUserProfilesTrigger", "recommendationGroup")
                .withSchedule(scheduleBuilder)
                .build();
    }

    /**
     * 热门商品更新任务
     * 每小时执行一次
     */
    @Bean
    public JobDetail updatePopularProductsJobDetail() {
        return JobBuilder.newJob(RecommendationJob.class)
                .withIdentity("updatePopularProductsJob", "recommendationGroup")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger updatePopularProductsTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 * * * ?");

        return TriggerBuilder.newTrigger()
                .forJob(updatePopularProductsJobDetail())
                .withIdentity("updatePopularProductsTrigger", "recommendationGroup")
                .withSchedule(scheduleBuilder)
                .build();
    }

    /**
     * 清除过期推荐结果任务
     * 每天凌晨3点执行
     */
    @Bean
    public JobDetail cleanExpiredRecommendationsJobDetail() {
        return JobBuilder.newJob(RecommendationJob.class)
                .withIdentity("cleanExpiredRecommendationsJob", "recommendationGroup")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger cleanExpiredRecommendationsTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 3 * * ?");

        return TriggerBuilder.newTrigger()
                .forJob(cleanExpiredRecommendationsJobDetail())
                .withIdentity("cleanExpiredRecommendationsTrigger", "recommendationGroup")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
