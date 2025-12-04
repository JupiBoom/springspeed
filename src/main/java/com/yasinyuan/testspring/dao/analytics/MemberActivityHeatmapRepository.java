package com.yasinyuan.testspring.dao.analytics;

import com.yasinyuan.testspring.entity.analytics.MemberActivityHeatmap;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface MemberActivityHeatmapRepository {

    // 根据时间粒度查找活跃度数据
    List<MemberActivityHeatmap> findByTimeGranularity(Integer timeGranularity);

    // 根据时间粒度和开始时间查找活跃度数据
    List<MemberActivityHeatmap> findByTimeGranularityAndStartTime(Integer timeGranularity, Date startTime);

    // 查找在指定时间范围内的活跃度数据
    List<MemberActivityHeatmap> findByStartTimeBetween(Date startDate, Date endDate);

    // 根据时间粒度和时间范围查找活跃度数据
    List<MemberActivityHeatmap> findByTimeGranularityAndDateRange(Integer granularity, Date startDate, Date endDate);

    // 按开始时间降序排列
    List<MemberActivityHeatmap> findAllByOrderByStartTimeDesc();

    // 按开始时间升序排列
    List<MemberActivityHeatmap> findAllByOrderByStartTimeAsc();

    // 查找活跃度评分大于等于指定值的记录
    List<MemberActivityHeatmap> findByActivityScoreGreaterThanEqual(Double score);

    // 查找最近N个时间单位的活跃度数据（根据时间粒度）
    List<MemberActivityHeatmap> findRecentActivityData(Integer granularity, Date startTime);

    // 统计指定时间范围内的平均活跃度
    Object[] getAverageActivityByDateRange(Date startDate, Date endDate);
}
