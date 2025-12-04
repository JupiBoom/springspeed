package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.model.PointsGainRule;
import com.yasinyuan.testspring.core.Service;

public interface PointsGainRuleService extends Service<PointsGainRule> {
    // 根据业务类型查询启用的积分获取规则
    PointsGainRule findEnabledRuleByBusinessType(Integer businessType);
    
    // 初始化积分获取规则
    void initPointsGainRules();
    
    // 检查今日积分获取是否已达上限
    boolean checkDailyLimit(Long memberId, Integer businessType, Integer pointsToAdd);
    
    // 检查本月积分获取是否已达上限
    boolean checkMonthlyLimit(Long memberId, Integer businessType, Integer pointsToAdd);
}