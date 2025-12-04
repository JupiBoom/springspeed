package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.PointsGainRule;
import com.yasinyuan.testspring.core.Mapper;

public interface PointsGainRuleMapper extends Mapper<PointsGainRule> {
    // 根据业务类型查询启用的积分获取规则
    PointsGainRule findEnabledRuleByBusinessType(Integer businessType);
}