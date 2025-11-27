package com.springspeed.repository;

import com.springspeed.model.RecommendationEffect;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Recommendation Effect Repository
 */
@Repository
public interface RecommendationEffectRepository extends JpaRepository<RecommendationEffect, Long> {

    List<RecommendationEffect> findByUserId(Long userId);

    List<RecommendationEffect> findByRecommendType(Integer recommendType);

    RecommendationEffect findByUserIdAndProductIdAndRecommendType(Long userId, Long productId, Integer recommendType);

    @Query("SELECT re FROM RecommendationEffect re WHERE re.exposureTime >= :startDate AND re.exposureTime <= :endDate")
    List<RecommendationEffect> findEffectsByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("SELECT re.recommendType, COUNT(re) as totalExposures, SUM(CASE WHEN re.isClicked THEN 1 ELSE 0 END) as totalClicks, SUM(CASE WHEN re.isPurchased THEN 1 ELSE 0 END) as totalPurchases FROM RecommendationEffect re WHERE re.exposureTime >= :startDate AND re.exposureTime <= :endDate GROUP BY re.recommendType")
    List<Object[]> getEffectStatisticsByType(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("SELECT AVG(CASE WHEN re.isClicked THEN 1.0 ELSE 0.0 END) as clickThroughRate FROM RecommendationEffect re WHERE re.recommendType = :recommendType AND re.exposureTime >= :startDate AND re.exposureTime <= :endDate")
    Double getClickThroughRate(@Param("recommendType") Integer recommendType, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("SELECT AVG(CASE WHEN re.isPurchased THEN 1.0 ELSE 0.0 END) as conversionRate FROM RecommendationEffect re WHERE re.recommendType = :recommendType AND re.exposureTime >= :startDate AND re.exposureTime <= :endDate")
    Double getConversionRate(@Param("recommendType") Integer recommendType, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("SELECT SUM(re.revenue) as totalRevenue FROM RecommendationEffect re WHERE re.recommendType = :recommendType AND re.exposureTime >= :startDate AND re.exposureTime <= :endDate")
    Double getTotalRevenue(@Param("recommendType") Integer recommendType, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("SELECT re.productId, COUNT(re) as exposures, SUM(CASE WHEN re.isClicked THEN 1 ELSE 0 END) as clicks, SUM(CASE WHEN re.isPurchased THEN 1 ELSE 0 END) as purchases FROM RecommendationEffect re WHERE re.recommendType = :recommendType GROUP BY re.productId ORDER BY exposures DESC")
    List<Object[]> getProductEffectStatistics(@Param("recommendType") Integer recommendType);

    // 按推荐类型和时间范围查询
    List<RecommendationEffect> findByRecommendTypeAndExposureTimeBetween(Integer recommendType, Date startDate, Date endDate);

    // 按用户ID和时间范围查询
    List<RecommendationEffect> findByUserIdAndExposureTimeBetween(Long userId, Date startDate, Date endDate);

    // 按商品ID和时间范围查询
    List<RecommendationEffect> findByProductIdAndExposureTimeBetween(Long productId, Date startDate, Date endDate);

    // 按时间范围查询
    List<RecommendationEffect> findByExposureTimeBetween(Date startDate, Date endDate);

    // 按时间范围删除
    void deleteByExposureTimeBefore(Date expireDate);

    // 获取商品推荐效果排名
    @Query("SELECT re.productId, COUNT(re) as exposures, SUM(CASE WHEN re.isClicked THEN 1 ELSE 0 END) as clicks, SUM(CASE WHEN re.isPurchased THEN 1 ELSE 0 END) as purchases FROM RecommendationEffect re WHERE re.exposureTime >= :startDate AND re.exposureTime <= :endDate GROUP BY re.productId ORDER BY exposures DESC LIMIT :limit")
    List<Object[]> findProductEffectRanking(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("limit") int limit);
}
