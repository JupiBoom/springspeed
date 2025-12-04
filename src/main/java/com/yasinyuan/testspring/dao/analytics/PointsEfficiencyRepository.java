package com.yasinyuan.testspring.dao.analytics;

import com.yasinyuan.testspring.entity.analytics.PointsEfficiency;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface PointsEfficiencyRepository {

    // 根据统计日期查找积分效率数据
    PointsEfficiency findByStatisticsDate(Date statisticsDate);

    // 查找在指定日期范围内的积分效率数据
    List<PointsEfficiency> findByStatisticsDateBetween(Date startDate, Date endDate);

    // 按统计日期降序排列
    List<PointsEfficiency> findAllByOrderByStatisticsDateDesc();

    // 按统计日期升序排列
    List<PointsEfficiency> findAllByOrderByStatisticsDateAsc();

    // 查找积分使用率大于等于指定值的记录
    List<PointsEfficiency> findByPointsUtilizationRateGreaterThanEqual(Double rate);

    // 统计指定时间段内的积分效率平均值
    Object[] getAverageEfficiencyByDateRange(Date startDate, Date endDate);

    // 查找最近N天的积分效率数据
    List<PointsEfficiency> findLastNDaysEfficiency(Integer days);
}
