package com.springspeed.repository;

import com.springspeed.model.RecommendationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Recommendation Result Repository
 */
@Repository
public interface RecommendationResultRepository extends JpaRepository<RecommendationResult, Long> {

    List<RecommendationResult> findByUserIdAndRecommendType(Long userId, Integer recommendType);

    List<RecommendationResult> findByRecommendType(Integer recommendType);

    @Query("SELECT rr FROM RecommendationResult rr WHERE rr.userId = :userId AND rr.recommendType = :recommendType AND rr.expireTime > CURRENT_TIMESTAMP ORDER BY rr.rankOrder ASC")
    List<RecommendationResult> findValidRecommendations(@Param("userId") Long userId, @Param("recommendType") Integer recommendType);

    @Query("SELECT rr FROM RecommendationResult rr WHERE rr.recommendType = :recommendType AND rr.expireTime > CURRENT_TIMESTAMP ORDER BY rr.rankOrder ASC")
    List<RecommendationResult> findValidRecommendationsByType(@Param("recommendType") Integer recommendType);

    @Query("DELETE FROM RecommendationResult rr WHERE rr.userId = :userId AND rr.recommendType = :recommendType")
    void deleteByUserIdAndRecommendType(@Param("userId") Long userId, @Param("recommendType") Integer recommendType);

    @Query("DELETE FROM RecommendationResult rr WHERE rr.expireTime <= CURRENT_TIMESTAMP")
    void deleteExpiredRecommendations();

    @Query("SELECT COUNT(rr) FROM RecommendationResult rr WHERE rr.userId = :userId AND rr.recommendType = :recommendType")
    Long countRecommendationsByUserAndType(@Param("userId") Long userId, @Param("recommendType") Integer recommendType);

    void deleteByCreatedTimeBefore(Date date);
}
