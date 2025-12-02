package com.springspeed.userbehavior.service.impl;

import com.springspeed.userbehavior.entity.UserSession;
import com.springspeed.userbehavior.repository.UserSessionRepository;
import com.springspeed.userbehavior.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;

/**
 * 用户会话服务实现类
 * 实现用户会话管理的业务逻辑
 */
@Service
@Transactional(readOnly = true)
public class UserSessionServiceImpl implements UserSessionService {

    @Autowired
    private UserSessionRepository userSessionRepository;

    /**
     * 创建用户会话
     * @param userSession 用户会话实体
     * @return 创建的用户会话
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserSession createUserSession(UserSession userSession) {
        if (userSession == null) {
            throw new IllegalArgumentException("用户会话实体不能为空");
        }

        // 设置默认值
        if (userSession.getSessionId() == null || userSession.getSessionId().isEmpty()) {
            userSession.setSessionId(UUID.randomUUID().toString());
        }

        if (userSession.getStartTime() == null) {
            userSession.setStartTime(new Date());
        }

        if (userSession.getSourceChannel() == null) {
            userSession.setSourceChannel("unknown");
        }

        if (userSession.getDeviceType() == null) {
            userSession.setDeviceType("unknown");
        }

        if (userSession.getStatus() == null) {
            userSession.setStatus(0); // 默认活跃状态
        }

        if (userSession.getCreateTime() == null) {
            userSession.setCreateTime(new Date());
        }

        if (userSession.getUpdateTime() == null) {
            userSession.setUpdateTime(new Date());
        }

        // 保存用户会话
        return userSessionRepository.save(userSession);
    }

    /**
     * 更新用户会话
     * @param userSession 用户会话实体
     * @return 更新的用户会话
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserSession updateUserSession(UserSession userSession) {
        if (userSession == null) {
            throw new IllegalArgumentException("用户会话实体不能为空");
        }

        if (userSession.getId() == null) {
            throw new IllegalArgumentException("用户会话ID不能为空");
        }

        // 更新更新时间
        userSession.setUpdateTime(new Date());

        // 保存用户会话
        return userSessionRepository.save(userSession);
    }

    /**
     * 根据会话ID查询用户会话
     * @param sessionId 会话ID
     * @return 用户会话
     */
    @Override
    public Optional<UserSession> getUserSessionBySessionId(String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            throw new IllegalArgumentException("会话ID不能为空");
        }

        return userSessionRepository.findBySessionId(sessionId);
    }

    /**
     * 根据用户ID查询用户会话
     * @param userId 用户ID
     * @return 用户会话列表
     */
    @Override
    public List<UserSession> getUserSessionsByUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        return userSessionRepository.findByUserId(userId);
    }

    /**
     * 查询活跃的用户会话
     * @return 活跃用户会话列表
     */
    @Override
    public List<UserSession> getActiveUserSessions() {
        return userSessionRepository.findByStatus(0);
    }

    /**
     * 查询超时的用户会话
     * @param timeoutMinutes 超时分钟数
     * @return 超时用户会话列表
     */
    @Override
    public List<UserSession> getTimeoutUserSessions(Integer timeoutMinutes) {
        if (timeoutMinutes == null || timeoutMinutes <= 0) {
            timeoutMinutes = 30; // 默认30分钟超时
        }

        Date now = new Date();
        Date timeoutDate = new Date(now.getTime() - timeoutMinutes * 60 * 1000);

        // 查询活跃状态且最后访问时间早于超时时间的会话
        return userSessionRepository.findByStatus(0)
                .stream()
                .filter(session -> {
                    Date lastAccessTime = session.getLastAccessTime() != null ? session.getLastAccessTime() : session.getStartTime();
                    return lastAccessTime.before(timeoutDate);
                })
                .collect(Collectors.toList());
    }

    /**
     * 关闭用户会话
     * @param sessionId 会话ID
     * @return 关闭的用户会话
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserSession closeUserSession(String sessionId) {
        Optional<UserSession> optionalSession = getUserSessionBySessionId(sessionId);
        if (!optionalSession.isPresent()) {
            throw new IllegalArgumentException("会话不存在");
        }

        UserSession session = optionalSession.get();
        session.setStatus(1); // 关闭状态
        session.setEndTime(new Date());

        // 计算会话持续时间（秒）
        long duration = (session.getEndTime().getTime() - session.getStartTime().getTime()) / 1000;
        session.setDuration((int) duration);

        return userSessionRepository.save(session);
    }

    /**
     * 批量关闭超时的用户会话
     * @param timeoutMinutes 超时分钟数
     * @return 关闭的用户会话数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchCloseTimeoutUserSessions(Integer timeoutMinutes) {
        List<UserSession> timeoutSessions = getTimeoutUserSessions(timeoutMinutes);

        Date now = new Date();
        for (UserSession session : timeoutSessions) {
            session.setStatus(1); // 关闭状态
            session.setEndTime(now);

            // 计算会话持续时间（秒）
            long duration = (now.getTime() - session.getStartTime().getTime()) / 1000;
            session.setDuration((int) duration);
        }

        userSessionRepository.saveAll(timeoutSessions);
        return timeoutSessions.size();
    }

    /**
     * 根据时间范围查询用户会话
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param pageable 分页参数
     * @return 用户会话分页列表
     */
    @Override
    public Page<UserSession> getUserSessionsByTimeRange(Date startDate, Date endDate, Pageable pageable) {
        if (startDate == null) {
            throw new IllegalArgumentException("开始时间不能为空");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("结束时间不能为空");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        // 创建分页查询
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        // 使用自定义查询方法或Specification来实现分页
        // 这里我们使用Spring Data JPA的分页功能
        List<UserSession> sessions = userSessionRepository.findByStartTimeBetween(startDate, endDate);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), sessions.size());

        Page<UserSession> page = new PageImpl<>(sessions.subList(start, end), pageRequest, sessions.size());
        return page;
    }

    /**
     * 查询用户会话统计数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 统计数据Map
     */
    @Override
    public Map<String, Object> getUserSessionStatistics(Date startDate, Date endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("开始时间不能为空");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("结束时间不能为空");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        // 总会话数
        long totalSessionCount = userSessionRepository.countTotalSessionsByTimeRange(startDate, endDate);

        // 活跃会话数
        long activeSessionCount = userSessionRepository.countActiveSessionsByTimeRange(startDate, endDate);

        // 关闭会话数
        long closedSessionCount = totalSessionCount - activeSessionCount;

        // 平均会话持续时间（秒）
        Double averageSessionDuration = userSessionRepository.findAverageSessionDurationByTimeRange(startDate, endDate);
        if (averageSessionDuration == null) {
            averageSessionDuration = 0.0;
        }

        // 会话转化率（关闭会话数/总会话数）
        double sessionConversionRate = totalSessionCount > 0 ? (double) closedSessionCount / totalSessionCount * 100 : 0;

        // 构建统计数据Map
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalSessionCount", totalSessionCount);
        statistics.put("activeSessionCount", activeSessionCount);
        statistics.put("closedSessionCount", closedSessionCount);
        statistics.put("averageSessionDuration", averageSessionDuration);
        statistics.put("sessionConversionRate", sessionConversionRate);

        return statistics;
    }

    /**
     * 查询会话来源渠道统计数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param topN 前N条数据
     * @return 来源渠道统计数据
     */
    @Override
    public List<Map<String, Object>> getSessionSourceChannelStatistics(Date startDate, Date endDate, Integer topN) {
        if (startDate == null) {
            throw new IllegalArgumentException("开始时间不能为空");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("结束时间不能为空");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        if (topN == null || topN < 1) {
            topN = 10;
        }

        // 查询指定时间范围内的所有会话
        List<UserSession> sessions = userSessionRepository.findByStartTimeBetween(startDate, endDate);

        // 统计各来源渠道的会话数量
        Map<String, Long> sourceChannelCountMap = sessions.stream()
                .collect(Collectors.groupingBy(UserSession::getSourceChannel, Collectors.counting()));

        // 转换为结果列表并按数量降序排序
        List<Map<String, Object>> sourceChannelStatistics = sourceChannelCountMap.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(topN)
                .map(entry -> {
                    Map<String, Object> statistic = new HashMap<>();
                    statistic.put("sourceChannel", entry.getKey());
                    statistic.put("sessionCount", entry.getValue());
                    statistic.put("percentage", (double) entry.getValue() / sessions.size() * 100);
                    return statistic;
                })
                .collect(Collectors.toList());

        return sourceChannelStatistics;
    }

    /**
     * 查询会话设备类型统计数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 设备类型统计数据
     */
    @Override
    public List<Map<String, Object>> getSessionDeviceTypeStatistics(Date startDate, Date endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("开始时间不能为空");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("结束时间不能为空");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        // 查询指定时间范围内的所有会话
        List<UserSession> sessions = userSessionRepository.findByStartTimeBetween(startDate, endDate);

        // 统计各设备类型的会话数量
        Map<String, Long> deviceTypeCountMap = sessions.stream()
                .collect(Collectors.groupingBy(UserSession::getDeviceType, Collectors.counting()));

        // 转换为结果列表并按数量降序排序
        List<Map<String, Object>> deviceTypeStatistics = deviceTypeCountMap.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .map(entry -> {
                    Map<String, Object> statistic = new HashMap<>();
                    statistic.put("deviceType", entry.getKey());
                    statistic.put("sessionCount", entry.getValue());
                    statistic.put("percentage", (double) entry.getValue() / sessions.size() * 100);
                    return statistic;
                })
                .collect(Collectors.toList());

        return deviceTypeStatistics;
    }

    /**
     * 查询会话持续时间分布数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param durationIntervals 持续时间区间（单位：秒）
     * @return 持续时间分布数据
     */
    @Override
    public List<Map<String, Object>> getSessionDurationDistribution(Date startDate, Date endDate, List<Integer> durationIntervals) {
        if (startDate == null) {
            throw new IllegalArgumentException("开始时间不能为空");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("结束时间不能为空");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        if (durationIntervals == null || durationIntervals.isEmpty()) {
            // 默认持续时间区间：0-30秒、30-60秒、60-180秒、180-300秒、300秒以上
            durationIntervals = Arrays.asList(30, 60, 180, 300);
        }

        // 查询指定时间范围内的所有已关闭会话（有持续时间）
        List<UserSession> sessions = userSessionRepository.findByStartTimeBetween(startDate, endDate);
        List<UserSession> closedSessions = sessions.stream()
                .filter(session -> session.getStatus() == 1 && session.getDuration() != null)
                .collect(Collectors.toList());

        // 统计各持续时间区间的会话数量
        Map<String, Long> durationDistributionMap = new TreeMap<>();

        // 初始化区间
        for (int i = 0; i < durationIntervals.size(); i++) {
            int start = i == 0 ? 0 : durationIntervals.get(i - 1);
            int end = durationIntervals.get(i);
            durationDistributionMap.put(start + "-" + end + "秒", 0L);
        }

        // 最后一个区间：大于最大的区间值
        int maxInterval = durationIntervals.get(durationIntervals.size() - 1);
        durationDistributionMap.put(">" + maxInterval + "秒", 0L);

        // 统计各区间的会话数量
        for (UserSession session : closedSessions) {
            long duration = session.getDuration();

            // 找到对应的区间
            boolean found = false;
            for (int i = 0; i < durationIntervals.size(); i++) {
                int start = i == 0 ? 0 : durationIntervals.get(i - 1);
                int end = durationIntervals.get(i);

                if (duration >= start && duration < end) {
                    String intervalKey = start + "-" + end + "秒";
                    durationDistributionMap.put(intervalKey, durationDistributionMap.get(intervalKey) + 1);
                    found = true;
                    break;
                }
            }

            // 如果没有找到对应的区间，说明持续时间大于最大的区间值
            if (!found) {
                String intervalKey = ">" + maxInterval + "秒";
                durationDistributionMap.put(intervalKey, durationDistributionMap.get(intervalKey) + 1);
            }
        }

        // 转换为结果列表
        List<Map<String, Object>> durationDistribution = durationDistributionMap.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> distribution = new HashMap<>();
                    distribution.put("durationInterval", entry.getKey());
                    distribution.put("sessionCount", entry.getValue());
                    distribution.put("percentage", closedSessions.size() > 0 ? (double) entry.getValue() / closedSessions.size() * 100 : 0);
                    return distribution;
                })
                .collect(Collectors.toList());

        return durationDistribution;
    }
}