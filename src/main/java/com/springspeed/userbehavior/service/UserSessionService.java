package com.springspeed.userbehavior.service;

import com.springspeed.userbehavior.entity.UserSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 用户会话服务接口
 * 定义用户会话管理的业务方法
 */
public interface UserSessionService {

    /**
     * 创建用户会话
     * @param userSession 用户会话实体
     * @return 创建的用户会话
     */
    UserSession createUserSession(UserSession userSession);

    /**
     * 更新用户会话
     * @param userSession 用户会话实体
     * @return 更新的用户会话
     */
    UserSession updateUserSession(UserSession userSession);

    /**
     * 根据会话ID查询用户会话
     * @param sessionId 会话ID
     * @return 用户会话
     */
    Optional<UserSession> getUserSessionBySessionId(String sessionId);

    /**
     * 根据用户ID查询用户会话
     * @param userId 用户ID
     * @return 用户会话列表
     */
    List<UserSession> getUserSessionsByUserId(String userId);

    /**
     * 查询活跃的用户会话
     * @return 活跃用户会话列表
     */
    List<UserSession> getActiveUserSessions();

    /**
     * 查询超时的用户会话
     * @param timeoutMinutes 超时分钟数
     * @return 超时用户会话列表
     */
    List<UserSession> getTimeoutUserSessions(Integer timeoutMinutes);

    /**
     * 关闭用户会话
     * @param sessionId 会话ID
     * @return 关闭的用户会话
     */
    UserSession closeUserSession(String sessionId);

    /**
     * 批量关闭超时的用户会话
     * @param timeoutMinutes 超时分钟数
     * @return 关闭的用户会话数量
     */
    int batchCloseTimeoutUserSessions(Integer timeoutMinutes);

    /**
     * 根据时间范围查询用户会话
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param pageable 分页参数
     * @return 用户会话分页列表
     */
    Page<UserSession> getUserSessionsByTimeRange(Date startDate, Date endDate, Pageable pageable);

    /**
     * 查询用户会话统计数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 统计数据Map
     */
    Map<String, Object> getUserSessionStatistics(Date startDate, Date endDate);

    /**
     * 查询会话来源渠道统计数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param topN 前N条数据
     * @return 来源渠道统计数据
     */
    List<Map<String, Object>> getSessionSourceChannelStatistics(Date startDate, Date endDate, Integer topN);

    /**
     * 查询会话设备类型统计数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 设备类型统计数据
     */
    List<Map<String, Object>> getSessionDeviceTypeStatistics(Date startDate, Date endDate);

    /**
     * 查询会话持续时间分布数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param durationIntervals 持续时间区间（单位：秒）
     * @return 持续时间分布数据
     */
    List<Map<String, Object>> getSessionDurationDistribution(Date startDate, Date endDate, List<Integer> durationIntervals);
}