package com.ecommerce.repository;

import com.ecommerce.entity.FunnelAnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FunnelAnalysisResultRepository extends JpaRepository<FunnelAnalysisResult, Long> {

    /**
     * 根据漏斗ID和分析日期查询漏斗分析结果
     */
    List<FunnelAnalysisResult> findByFunnelIdAndAnalysisDate(Long funnelId, Date analysisDate);

    /**
     * 根据漏斗ID查询指定时间范围内的漏斗分析结果
     */
    List<FunnelAnalysisResult> findByFunnelIdAndAnalysisDateBetween(Long funnelId, Date startDate, Date endDate);

    /**
     * 查询指定日期的所有漏斗分析结果
     */
    List<FunnelAnalysisResult> findByAnalysisDate(Date analysisDate);

    /**
     * 获取指定漏斗的最新分析结果
     */
    @Query("SELECT r FROM FunnelAnalysisResult r WHERE r.funnelId = :funnelId ORDER BY r.analysisDate DESC LIMIT 1")
    FunnelAnalysisResult findLatestByFunnelId(@Param("funnelId") Long funnelId);

    /**
     * 统计指定漏斗在指定时间范围内的平均转化率
     */
    @Query("SELECT AVG(r.conversionRate) FROM FunnelAnalysisResult r WHERE r.funnelId = :funnelId AND r.analysisDate BETWEEN :startDate AND :endDate")
    Double findAverageConversionRate(@Param("funnelId") Long funnelId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
