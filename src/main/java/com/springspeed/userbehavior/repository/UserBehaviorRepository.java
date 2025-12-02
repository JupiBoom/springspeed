package com.springspeed.userbehavior.repository;

import com.springspeed.userbehavior.entity.UserBehavior;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户行为数据访问接口
 * 用于操作Elasticsearch中的用户行为数据
 */
@Repository
public interface UserBehaviorRepository extends ElasticsearchRepository<UserBehavior, String> {

    /**
     * 根据用户ID和行为类型查询用户行为
     * @param userId 用户ID
     * @param behaviorType 行为类型
     * @return 用户行为列表
     */
    List<UserBehavior> findByUserIdAndBehaviorType(String userId, String behaviorType);

    /**
     * 根据会话ID查询用户行为
     * @param sessionId 会话ID
     * @return 用户行为列表
     */
    List<UserBehavior> findBySessionId(String sessionId);

    /**
     * 根据行为类型和行为时间范围查询用户行为
     * @param behaviorType 行为类型
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 用户行为列表
     */
    List<UserBehavior> findByBehaviorTypeAndBehaviorTimeBetween(String behaviorType, Date startDate, Date endDate);

    /**
     * 根据页面和行为时间范围查询用户行为
     * @param page 页面
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 用户行为列表
     */
    List<UserBehavior> findByPageAndBehaviorTimeBetween(String page, Date startDate, Date endDate);

    /**
     * 根据商品ID和行为类型查询用户行为
     * @param productId 商品ID
     * @param behaviorType 行为类型
     * @return 用户行为列表
     */
    List<UserBehavior> findByProductIdAndBehaviorType(String productId, String behaviorType);

    /**
     * 根据用户ID和行为时间范围查询用户行为
     * @param userId 用户ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 用户行为列表
     */
    List<UserBehavior> findByUserIdAndBehaviorTimeBetween(String userId, Date startDate, Date endDate);

    /**
     * 根据会话ID和行为时间范围查询用户行为
     * @param sessionId 会话ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 用户行为列表
     */
    List<UserBehavior> findBySessionIdAndBehaviorTimeBetween(String sessionId, Date startDate, Date endDate);

    /**
     * 根据用户ID查询用户行为（分页）
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 用户行为分页列表
     */
    Page<UserBehavior> findByUserId(String userId, Pageable pageable);

    /**
     * 根据行为类型查询用户行为（分页）
     * @param behaviorType 行为类型
     * @param pageable 分页参数
     * @return 用户行为分页列表
     */
    Page<UserBehavior> findByBehaviorType(String behaviorType, Pageable pageable);
}