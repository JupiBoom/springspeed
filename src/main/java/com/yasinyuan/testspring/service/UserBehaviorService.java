package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.model.UserBehavior;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户行为服务接口
 * @author yasinyuan
 * @date 2025-05-02
 */
public interface UserBehaviorService {

    /**
     * 保存用户行为日志
     * @param behavior 行为日志
     */
    void saveBehavior(UserBehavior behavior);

    /**
     * 批量保存用户行为日志
     * @param behaviors 行为日志列表
     */
    void saveBehaviors(List<UserBehavior> behaviors);

    /**
     * 记录用户行为
     * @param userId 用户ID
     * @param sessionId 会话ID
     * @param behaviorType 行为类型
     * @param pageUrl 页面URL
     * @param pageTitle 页面标题
     * @param productId 商品ID
     * @param productName 商品名称
     * @param ip IP地址
     * @param device 设备信息
     * @param extInfo 扩展信息
     */
    void trackBehavior(Long userId, String sessionId, String behaviorType, String pageUrl, String pageTitle, String productId, String productName, String ip, String device, Map<String, Object> extInfo);

    /**
     * 根据用户ID查询行为日志
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 行为日志列表
     */
    Page<UserBehavior> findByUserId(Long userId, Pageable pageable);

    /**
     * 根据会话ID查询行为日志
     * @param sessionId 会话ID
     * @return 行为日志列表
     */
    List<UserBehavior> findBySessionId(String sessionId);

    /**
     * 根据行为类型查询行为日志
     * @param behaviorType 行为类型
     * @param pageable 分页参数
     * @return 行为日志列表
     */
    Page<UserBehavior> findByBehaviorType(String behaviorType, Pageable pageable);

    /**
     * 根据时间范围查询行为日志
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageable 分页参数
     * @return 行为日志列表
     */
    Page<UserBehavior> findByCreateTimeBetween(Date startTime, Date endTime, Pageable pageable);

    /**
     * 获取用户路径
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 用户路径列表
     */
    List<String> getUserPath(Long userId, Date startTime, Date endTime);
}
