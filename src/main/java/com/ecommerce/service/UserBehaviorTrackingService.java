package com.ecommerce.service;

import com.ecommerce.entity.UserBehaviorLog;
import java.util.Date;
import java.util.List;

public interface UserBehaviorTrackingService {

    /**
     * 记录用户行为
     * @param userId 用户ID
     * @param sessionId 会话ID
     * @param behaviorType 行为类型（VIEW, CLICK, ADD_TO_CART, ORDER）
     * @param pageUrl 页面URL
     * @param productId 产品ID
     * @param categoryId 分类ID
     * @param referrerUrl 来源URL
     * @param ipAddress IP地址
     * @param userAgent 用户代理
     * @return 用户行为日志
     */
    UserBehaviorLog trackUserBehavior(Long userId, String sessionId, String behaviorType, 
                                        String pageUrl, Long productId, Long categoryId, 
                                        String referrerUrl, String ipAddress, String userAgent);

    /**
     * 根据用户ID和时间范围查询用户行为日志
     * @param userId 用户ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 用户行为日志列表
     */
    List<UserBehaviorLog> getUserBehaviorLogsByUserId(Long userId, Date startDate, Date endDate);

    /**
     * 根据会话ID查询用户行为路径
     * @param sessionId 会话ID
     * @return 用户行为日志列表（按时间排序）
     */
    List<UserBehaviorLog> getUserBehaviorPathBySessionId(String sessionId);

    /**
     * 统计指定时间范围内的行为次数
     * @param behaviorType 行为类型
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 行为次数
     */
    Long countBehaviorByTypeAndTimeRange(String behaviorType, Date startDate, Date endDate);

    /**
     * 统计指定时间范围内的活跃用户数
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 活跃用户数
     */
    Long countActiveUsersByTimeRange(Date startDate, Date endDate);

    /**
     * 统计指定时间范围内的新用户数
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 新用户数
     */
    Long countNewUsersByTimeRange(Date startDate, Date endDate);
}
