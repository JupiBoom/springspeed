package com.springspeed.userbehavior.repository;

import com.springspeed.userbehavior.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 用户会话数据访问接口
 * 用于操作MySQL中的用户会话数据
 */
@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    /**
     * 根据会话ID查询用户会话
     * @param sessionId 会话ID
     * @return 用户会话
     */
    Optional<UserSession> findBySessionId(String sessionId);

    /**
     * 根据用户ID查询用户会话
     * @param userId 用户ID
     * @return 用户会话列表
     */
    List<UserSession> findByUserId(String userId);

    /**
     * 根据会话状态查询用户会话
     * @param status 会话状态
     * @return 用户会话列表
     */
    List<UserSession> findByStatus(Integer status);

    /**
     * 根据会话开始时间范围查询用户会话
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 用户会话列表
     */
    List<UserSession> findByStartTimeBetween(Date startDate, Date endDate);

    /**
     * 根据用户ID和会话状态查询用户会话
     * @param userId 用户ID
     * @param status 会话状态
     * @return 用户会话列表
     */
    List<UserSession> findByUserIdAndStatus(String userId, Integer status);

    /**
     * 查询指定时间范围内的活跃会话数量
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 活跃会话数量
     */
    @Query("SELECT COUNT(*) FROM UserSession u WHERE u.startTime BETWEEN :startDate AND :endDate AND u.status = 0")
    Long countActiveSessionsByTimeRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 查询指定时间范围内的会话总数
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 会话总数
     */
    @Query("SELECT COUNT(*) FROM UserSession u WHERE u.startTime BETWEEN :startDate AND :endDate")
    Long countTotalSessionsByTimeRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 查询指定时间范围内的平均会话持续时间
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 平均会话持续时间（秒）
     */
    @Query("SELECT AVG(u.duration) FROM UserSession u WHERE u.startTime BETWEEN :startDate AND :endDate AND u.duration IS NOT NULL")
    Double findAverageSessionDurationByTimeRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}