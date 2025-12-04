package com.yasinyuan.testspring.configurer;

import com.yasinyuan.testspring.service.PointsService;
import com.yasinyuan.testspring.service.MemberService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * 定时任务配置类
 * @author yinyuan
 * @since 2024-01-01
 */
@Configuration
@EnableScheduling
public class ScheduledTaskConfigurer {
    @Resource
    private PointsService pointsService;
    
    @Resource
    private MemberService memberService;

    /**
     * 每天凌晨1点处理积分过期
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void processPointsExpiration() {
        pointsService.processPointsExpiration();
    }

    /**
     * 每天凌晨2点处理会员生日权益发放
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void processMemberBirthdayBenefits() {
        memberService.processMemberBirthdayBenefits();
    }

    /**
     * 每月1号凌晨3点更新会员等级
     */
    @Scheduled(cron = "0 0 3 1 * ?")
    public void updateMemberLevels() {
        // TODO: 实现每月会员等级更新逻辑
    }
}
