package com.ecommerce.repository;

import com.ecommerce.entity.UserBehaviorDailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserBehaviorDailyReportRepository extends JpaRepository<UserBehaviorDailyReport, Long> {

    /**
     * 根据报告日期查询日报
     */
    UserBehaviorDailyReport findByReportDate(Date reportDate);

    /**
     * 查询指定时间范围内的日报
     */
    List<UserBehaviorDailyReport> findByReportDateBetween(Date startDate, Date endDate);

    /**
     * 获取最新的日报
     */
    @Query("SELECT r FROM UserBehaviorDailyReport r ORDER BY r.reportDate DESC LIMIT 1")
    UserBehaviorDailyReport findLatestReport();

    /**
     * 统计指定时间范围内的平均活跃用户数
     */
    @Query("SELECT AVG(r.activeUsers) FROM UserBehaviorDailyReport r WHERE r.reportDate BETWEEN :startDate AND :endDate")
    Double findAverageActiveUsers(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 统计指定时间范围内的平均转化率
     */
    @Query("SELECT AVG(r.conversionRate) FROM UserBehaviorDailyReport r WHERE r.reportDate BETWEEN :startDate AND :endDate")
    Double findAverageConversionRate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
