package com.springspeed.userbehavior.service;

import com.springspeed.userbehavior.entity.UserBehavior;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户行为服务接口
 * 定义用户行为相关的业务方法
 */
public interface UserBehaviorService {

    /**
     * 记录用户行为
     * @param userBehavior 用户行为实体
     * @return 记录的用户行为
     */
    UserBehavior recordUserBehavior(UserBehavior userBehavior);

    /**
     * 批量记录用户行为
     * @param userBehaviors 用户行为实体列表
     * @return 记录的用户行为列表
     */
    List<UserBehavior> batchRecordUserBehaviors(List<UserBehavior> userBehaviors);

    /**
     * 根据行为ID查询用户行为
     * @param behaviorId 行为ID
     * @return 用户行为
     */
    UserBehavior getUserBehaviorById(String behaviorId);

    /**
     * 根据用户ID和行为类型查询用户行为
     * @param userId 用户ID
     * @param behaviorType 行为类型
     * @return 用户行为列表
     */
    List<UserBehavior> getUserBehaviorsByUserIdAndType(String userId, String behaviorType);

    /**
     * 根据会话ID查询用户行为
     * @param sessionId 会话ID
     * @return 用户行为列表
     */
    List<UserBehavior> getUserBehaviorsBySessionId(String sessionId);

    /**
     * 根据会话ID查询用户行为（分页）
     * @param sessionId 会话ID
     * @param pageable 分页参数
     * @return 用户行为分页列表
     */
    Page<UserBehavior> getUserBehaviorsBySessionId(String sessionId, Pageable pageable);

    /**
     * 根据用户ID查询用户行为（分页）
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 用户行为分页列表
     */
    Page<UserBehavior> getUserBehaviorsByUserId(String userId, Pageable pageable);

    /**
     * 根据行为类型查询用户行为（分页）
     * @param behaviorType 行为类型
     * @param pageable 分页参数
     * @return 用户行为分页列表
     */
    Page<UserBehavior> getUserBehaviorsByBehaviorType(String behaviorType, Pageable pageable);

    /**
     * 根据行为类型和时间范围查询用户行为
     * @param behaviorType 行为类型
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param pageable 分页参数
     * @return 用户行为分页列表
     */
    Page<UserBehavior> getUserBehaviorsByTypeAndTimeRange(String behaviorType, Date startDate, Date endDate, Pageable pageable);

    /**
     * 根据时间范围查询用户行为
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param pageable 分页参数
     * @return 用户行为分页列表
     */
    Page<UserBehavior> getUserBehaviorsByTimeRange(Date startDate, Date endDate, Pageable pageable);

    /**
     * 查询用户行为统计数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 统计数据Map
     */
    Map<String, Object> getUserBehaviorStatistics(Date startDate, Date endDate);

    /**
     * 查询行为类型统计数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 行为类型统计数据
     */
    List<Map<String, Object>> getBehaviorTypeStatistics(Date startDate, Date endDate);

    /**
     * 查询用户路径分析数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param minCount 最小访问次数
     * @return 用户路径分析数据
     */
    List<Map<String, Object>> getUserPathAnalysis(Date startDate, Date endDate, Integer minCount);

    /**
     * 查询页面访问统计数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param topN 前N条数据
     * @return 页面访问统计数据
     */
    List<Map<String, Object>> getPageVisitStatistics(Date startDate, Date endDate, Integer topN);

    /**
     * 查询商品行为统计数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param topN 前N条数据
     * @return 商品行为统计数据
     */
    List<Map<String, Object>> getProductBehaviorStatistics(Date startDate, Date endDate, Integer topN);

    /**
     * 查询用户行为漏斗数据
     * @param funnelSteps 漏斗步骤
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 漏斗数据
     */
    List<Map<String, Object>> getUserBehaviorFunnel(List<String> funnelSteps, Date startDate, Date endDate);

    /**
     * 分析用户行为漏斗
     * @param funnelSteps 漏斗步骤
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 漏斗分析数据
     */
    Map<String, Object> analyzeFunnel(List<String> funnelSteps, Date startDate, Date endDate);

    /**
     * 获取用户行为日报
     * @param reportDate 报告日期
     * @return 日报数据
     */
    Map<String, Object> getUserBehaviorDailyReport(Date reportDate);

    /**
     * 获取用户行为周报
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 周报数据
     */
    Map<String, Object> getUserBehaviorWeeklyReport(Date startDate, Date endDate);

    /**
     * 获取用户行为月报
     * @param reportMonth 报告月份（格式：yyyy-MM）
     * @return 月报数据
     */
    Map<String, Object> getUserBehaviorMonthlyReport(String reportMonth);
}