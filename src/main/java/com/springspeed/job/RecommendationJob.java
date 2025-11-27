package com.springspeed.job;

import com.springspeed.service.RecommendationService;
import com.springspeed.service.UserProfileService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Recommendation Job
 * 推荐系统定时任务
 */
@Component
public class RecommendationJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(RecommendationJob.class);

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private RecommendationService recommendationService;

    /**
     * 执行定时任务
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String jobName = context.getJobDetail().getKey().getName();
        logger.info("开始执行定时任务: {}", jobName);

        try {
            if ("updateUserProfilesJob".equals(jobName)) {
                // 更新用户画像
                updateUserProfiles();
            } else if ("updatePopularProductsJob".equals(jobName)) {
                // 更新热门商品
                updatePopularProducts();
            } else if ("cleanExpiredRecommendationsJob".equals(jobName)) {
                // 清除过期推荐结果
                cleanExpiredRecommendations();
            }
        } catch (Exception e) {
            logger.error("定时任务执行失败: {}", jobName, e);
            throw new JobExecutionException(e);
        }

        logger.info("定时任务执行完成: {}", jobName);
    }

    /**
     * 更新用户画像
     */
    private void updateUserProfiles() {
        logger.info("开始批量更新用户画像...");
        userProfileService.batchUpdateUserProfiles();
        logger.info("用户画像更新完成");
    }

    /**
     * 更新热门商品
     */
    private void updatePopularProducts() {
        logger.info("开始更新热门商品...");
        recommendationService.popularProductsRecommendation(100); // 更新热门商品缓存
        logger.info("热门商品更新完成");
    }

    /**
     * 清除过期推荐结果
     */
    private void cleanExpiredRecommendations() {
        logger.info("开始清除过期推荐结果...");
        recommendationService.cleanExpiredRecommendations();
        logger.info("过期推荐结果清除完成");
    }
}
