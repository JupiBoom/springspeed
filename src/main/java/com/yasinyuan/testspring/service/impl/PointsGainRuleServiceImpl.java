package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.dao.PointsGainRuleMapper;
import com.yasinyuan.testspring.model.PointsGainRule;
import com.yasinyuan.testspring.service.PointsGainRuleService;
import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.dao.PointsRecordMapper;
import com.yasinyuan.testspring.tools.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 积分获取规则服务实现类
 * @author yinyuan
 * @since 2024-01-01
 */
@Service
public class PointsGainRuleServiceImpl extends AbstractService<PointsGainRule> implements PointsGainRuleService {
    @Resource
    private PointsGainRuleMapper pointsGainRuleMapper;
    
    @Resource
    private PointsRecordMapper pointsRecordMapper;

    @Override
    public PointsGainRule findEnabledRuleByBusinessType(Integer businessType) {
        return pointsGainRuleMapper.findEnabledRuleByBusinessType(businessType);
    }

    @Override
    @Transactional
    public void initPointsGainRules() {
        // 检查是否已初始化
        if (pointsGainRuleMapper.selectCount(null) > 0) {
            return;
        }
        
        Date now = new Date();
        
        // 消费获取积分规则
        PointsGainRule consumeRule = new PointsGainRule();
        consumeRule.setBusinessType(1);
        consumeRule.setBusinessTypeName("消费");
        consumeRule.setPointsRatio(1.0); // 1元=1积分
        consumeRule.setFixedPoints(null);
        consumeRule.setDailyLimit(1000); // 每日上限1000积分
        consumeRule.setMonthlyLimit(5000); // 每月上限5000积分
        consumeRule.setValidityDays(365); // 有效期1年
        consumeRule.setStatus(1);
        consumeRule.setCreateTime(now);
        consumeRule.setUpdateTime(now);
        pointsGainRuleMapper.insertSelective(consumeRule);
        
        // 签到获取积分规则
        PointsGainRule signInRule = new PointsGainRule();
        signInRule.setBusinessType(2);
        signInRule.setBusinessTypeName("签到");
        signInRule.setPointsRatio(null);
        signInRule.setFixedPoints(10); // 每日签到获取10积分
        signInRule.setDailyLimit(10); // 每日上限10积分
        signInRule.setMonthlyLimit(300); // 每月上限300积分
        signInRule.setValidityDays(90); // 有效期3个月
        signInRule.setStatus(1);
        signInRule.setCreateTime(now);
        signInRule.setUpdateTime(now);
        pointsGainRuleMapper.insertSelective(signInRule);
        
        // 活动获取积分规则
        PointsGainRule activityRule = new PointsGainRule();
        activityRule.setBusinessType(3);
        activityRule.setBusinessTypeName("活动");
        activityRule.setPointsRatio(null);
        activityRule.setFixedPoints(null); // 活动积分不固定，由活动配置
        activityRule.setDailyLimit(500); // 每日上限500积分
        activityRule.setMonthlyLimit(2000); // 每月上限2000积分
        activityRule.setValidityDays(180); // 有效期6个月
        activityRule.setStatus(1);
        activityRule.setCreateTime(now);
        activityRule.setUpdateTime(now);
        pointsGainRuleMapper.insertSelective(activityRule);
    }

    @Override
    public boolean checkDailyLimit(Long memberId, Integer businessType, Integer pointsToAdd) {
        PointsGainRule rule = pointsGainRuleMapper.findEnabledRuleByBusinessType(businessType);
        if (rule == null || rule.getDailyLimit() == null) {
            return true; // 无规则或无上限限制
        }
        
        // 查询今日已获取的积分
        Date today = new Date();
        Date startOfDay = DateUtils.getStartOfDay(today);
        Date endOfDay = DateUtils.getEndOfDay(today);
        
        // TODO: 实现今日积分获取总量查询
        Integer todayPoints = 0;
        
        return todayPoints + pointsToAdd <= rule.getDailyLimit();
    }

    @Override
    public boolean checkMonthlyLimit(Long memberId, Integer businessType, Integer pointsToAdd) {
        PointsGainRule rule = pointsGainRuleMapper.findEnabledRuleByBusinessType(businessType);
        if (rule == null || rule.getMonthlyLimit() == null) {
            return true; // 无规则或无上限限制
        }
        
        // 查询本月已获取的积分
        Date today = new Date();
        Date startOfMonth = DateUtils.getStartOfMonth(today);
        Date endOfMonth = DateUtils.getEndOfMonth(today);
        
        // TODO: 实现本月积分获取总量查询
        Integer monthlyPoints = 0;
        
        return monthlyPoints + pointsToAdd <= rule.getMonthlyLimit();
    }
}