package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.UserBehavior;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Date;
import java.util.List;

/**
 * 用户行为Elasticsearch Repository
 * @author yasinyuan
 * @date 2025-05-02
 */
public interface UserBehaviorRepository extends ElasticsearchRepository<UserBehavior, String> {

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
     * 根据用户ID和行为类型查询行为日志
     * @param userId 用户ID
     * @param behaviorType 行为类型
     * @param pageable 分页参数
     * @return 行为日志列表
     */
    Page<UserBehavior> findByUserIdAndBehaviorType(Long userId, String behaviorType, Pageable pageable);
}
