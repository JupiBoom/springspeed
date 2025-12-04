package com.yasinyuan.testspring.service.analytics;

import com.yasinyuan.testspring.entity.analytics.MemberLTV;
import com.yasinyuan.testspring.entity.analytics.PointsEfficiency;
import com.yasinyuan.testspring.entity.analytics.MemberActivityHeatmap;

import java.util.Date;
import java.util.List;

public interface AnalyticsService {

    // 计算会员生命周期价值
    MemberLTV calculateMemberLTV(Long memberId);

    // 批量计算会员生命周期价值
    List<MemberLTV> batchCalculateMemberLTV(List<Long> memberIds);

    // 定期更新所有会员的生命周期价值
    void updateAllMemberLTV();

    // 根据会员ID查找LTV记录
    MemberLTV findMemberLTVByMemberId(Long memberId);

    // 生成积分使用效率报告
    PointsEfficiency generatePointsEfficiencyReport(Date statisticsDate);

    // 生成指定日期范围内的积分使用效率报告
    List<PointsEfficiency> generatePointsEfficiencyReports(Date startDate, Date endDate);

    // 定期生成每日积分使用效率报告
    void generateDailyPointsEfficiencyReport();

    // 生成会员活跃度热力图数据
    MemberActivityHeatmap generateMemberActivityHeatmap(Integer timeGranularity, Date startTime, Date endTime);

    // 生成指定时间范围内的会员活跃度热力图数据
    List<MemberActivityHeatmap> generateMemberActivityHeatmaps(Integer timeGranularity, Date startDate, Date endDate);

    // 定期生成会员活跃度热力图数据
    void generatePeriodicMemberActivityHeatmap(Integer timeGranularity);

    // 查找指定时间范围内的积分效率数据
    List<PointsEfficiency> findPointsEfficiencyByDateRange(Date startDate, Date endDate);

    // 查找指定时间粒度和时间范围内的活跃度数据
    List<MemberActivityHeatmap> findMemberActivityHeatmapByGranularityAndDateRange(Integer timeGranularity, Date startDate, Date endDate);

    // 获取会员LTV统计数据
    Object[] getLTVStatistics();

    // 获取积分效率统计数据
    Object[] getPointsEfficiencyStatistics(Date startDate, Date endDate);

    // 获取会员活跃度统计数据
    Object[] getMemberActivityStatistics(Date startDate, Date endDate);
}
