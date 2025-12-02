package com.ecommerce.repository;

import com.ecommerce.entity.UserSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSegmentRepository extends JpaRepository<UserSegment, Long> {

    /**
     * 根据用户ID查询用户分群信息
     */
    List<UserSegment> findByUserId(Long userId);

    /**
     * 根据分群类型查询用户分群信息
     */
    List<UserSegment> findBySegmentType(String segmentType);

    /**
     * 根据分群名称查询用户分群信息
     */
    List<UserSegment> findBySegmentName(String segmentName);

    /**
     * 根据用户生命周期阶段查询用户
     */
    List<UserSegment> findByLifecycleStage(String lifecycleStage);

    /**
     * 查询指定分群类型和分群值的用户
     */
    List<UserSegment> findBySegmentTypeAndSegmentValue(String segmentType, String segmentValue);

    /**
     * 统计各分群的用户数量
     */
    @Query("SELECT s.segmentName, COUNT(DISTINCT s.userId) FROM UserSegment s GROUP BY s.segmentName")
    List<Object[]> countUsersBySegment();

    /**
     * 统计各生命周期阶段的用户数量
     */
    @Query("SELECT s.lifecycleStage, COUNT(DISTINCT s.userId) FROM UserSegment s WHERE s.lifecycleStage IS NOT NULL GROUP BY s.lifecycleStage")
    List<Object[]> countUsersByLifecycleStage();

    /**
     * 根据RFM模型查询用户（最近购买天数、购买频率、购买金额）
     */
    @Query("SELECT s FROM UserSegment s WHERE s.segmentType = 'RFM' AND (:recency IS NULL OR s.recency <= :recency) AND (:frequency IS NULL OR s.frequency >= :frequency) AND (:monetary IS NULL OR s.monetary >= :monetary)")
    List<UserSegment> findUsersByRFM(@Param("recency") Integer recency, @Param("frequency") Integer frequency, @Param("monetary") Double monetary);
}
