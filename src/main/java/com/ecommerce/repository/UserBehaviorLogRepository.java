package com.ecommerce.repository;

import com.ecommerce.entity.UserBehaviorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserBehaviorLogRepository extends JpaRepository<UserBehaviorLog, Long> {

    /**
     * 根据用户ID和时间范围查询行为日志
     */
    List<UserBehaviorLog> findByUserIdAndCreatedTimeBetween(Long userId, Date startDate, Date endDate);

    /**
     * 根据会话ID查询行为日志
     */
    List<UserBehaviorLog> findBySessionIdOrderByCreatedTimeAsc(String sessionId);

    /**
     * 根据行为类型和时间范围查询行为日志
     */
    List<UserBehaviorLog> findByBehaviorTypeAndCreatedTimeBetween(String behaviorType, Date startDate, Date endDate);

    /**
     * 统计指定时间范围内的行为次数
     */
    @Query("SELECT COUNT(*) FROM UserBehaviorLog WHERE behaviorType = :behaviorType AND createdTime BETWEEN :startDate AND :endDate")
    Long countByBehaviorTypeAndTimeRange(@Param("behaviorType") String behaviorType, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 统计指定时间范围内的活跃用户数
     */
    @Query("SELECT COUNT(DISTINCT userId) FROM UserBehaviorLog WHERE createdTime BETWEEN :startDate AND :endDate")
    Long countActiveUsersByTimeRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 统计指定时间范围内的新用户数（首次出现的用户）
     */
    @Query("SELECT COUNT(DISTINCT userId) FROM UserBehaviorLog u1 WHERE u1.createdTime BETWEEN :startDate AND :endDate AND NOT EXISTS (SELECT 1 FROM UserBehaviorLog u2 WHERE u2.userId = u1.userId AND u2.createdTime < :startDate)")
    Long countNewUsersByTimeRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
