package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.model.FunnelConfig;
import com.yasinyuan.testspring.model.FunnelConversionResult;
import com.yasinyuan.testspring.model.FunnelStep;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FunnelAnalysisService {
    // 漏斗配置管理
    FunnelConfig saveFunnelConfig(FunnelConfig funnelConfig, List<FunnelStep> funnelSteps);
    FunnelConfig updateFunnelConfig(FunnelConfig funnelConfig, List<FunnelStep> funnelSteps);
    void deleteFunnelConfig(Long funnelId);
    List<FunnelConfig> listEnabledFunnelConfigs();
    FunnelConfig getFunnelConfigById(Long funnelId);

    // 转化率计算
    List<FunnelConversionResult> calculateFunnelConversion(Long funnelId, Date startDate, Date endDate);
    List<FunnelConversionResult> getFunnelConversionResult(Long funnelId, Date date);

    // 流失分析
    Map<String, Object> getLostUsers(Long funnelId, Integer fromStep, Date startDate, Date endDate);
}
