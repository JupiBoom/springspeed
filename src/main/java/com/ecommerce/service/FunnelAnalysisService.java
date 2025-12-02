package com.ecommerce.service;

import com.ecommerce.entity.Funnel;
import com.ecommerce.entity.FunnelAnalysisResult;
import java.util.Date;
import java.util.List;

public interface FunnelAnalysisService {

    /**
     * 创建漏斗
     * @param funnelName 漏斗名称
     * @param description 漏斗描述
     * @param funnelSteps 漏斗步骤（逗号分隔）
     * @param createdBy 创建者
     * @return 漏斗
     */
    Funnel createFunnel(String funnelName, String description, String funnelSteps, String createdBy);

    /**
     * 更新漏斗
     * @param funnelId 漏斗ID
     * @param funnelName 漏斗名称
     * @param description 漏斗描述
     * @param funnelSteps 漏斗步骤（逗号分隔）
     * @param isActive 是否激活
     * @return 漏斗
     */
    Funnel updateFunnel(Long funnelId, String funnelName, String description, String funnelSteps, Boolean isActive);

    /**
     * 删除漏斗
     * @param funnelId 漏斗ID
     */
    void deleteFunnel(Long funnelId);

    /**
     * 查询所有漏斗
     * @return 漏斗列表
     */
    List<Funnel> getAllFunnels();

    /**
     * 查询所有激活的漏斗
     * @return 激活的漏斗列表
     */
    List<Funnel> getActiveFunnels();

    /**
     * 根据漏斗ID查询漏斗
     * @param funnelId 漏斗ID
     * @return 漏斗
     */
    Funnel getFunnelById(Long funnelId);

    /**
     * 分析漏斗转化情况
     * @param funnelId 漏斗ID
     * @param analysisDate 分析日期
     * @return 漏斗分析结果列表
     */
    List<FunnelAnalysisResult> analyzeFunnelConversion(Long funnelId, Date analysisDate);

    /**
     * 获取漏斗分析结果
     * @param funnelId 漏斗ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 漏斗分析结果列表
     */
    List<FunnelAnalysisResult> getFunnelAnalysisResults(Long funnelId, Date startDate, Date endDate);

    /**
     * 获取漏斗的最新分析结果
     * @param funnelId 漏斗ID
     * @return 漏斗分析结果列表
     */
    List<FunnelAnalysisResult> getLatestFunnelAnalysisResults(Long funnelId);

    /**
     * 定位漏斗中的流失用户
     * @param funnelId 漏斗ID
     * @param stepIndex 步骤索引
     * @param analysisDate 分析日期
     * @return 流失用户ID列表
     */
    List<Long> findChurnedUsersInFunnel(Long funnelId, Integer stepIndex, Date analysisDate);
}
