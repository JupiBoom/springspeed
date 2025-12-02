package com.springspeed.userbehavior.repository;

import com.springspeed.userbehavior.entity.UserSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户分群数据访问接口
 * 用于操作MySQL中的用户分群数据
 */
@Repository
public interface UserSegmentRepository extends JpaRepository<UserSegment, Long> {

    /**
     * 根据分群ID查询用户分群
     * @param segmentId 分群ID
     * @return 用户分群
     */
    Optional<UserSegment> findBySegmentId(String segmentId);

    /**
     * 根据分群名称查询用户分群
     * @param segmentName 分群名称
     * @return 用户分群列表
     */
    List<UserSegment> findBySegmentName(String segmentName);

    /**
     * 根据分群类型查询用户分群
     * @param segmentType 分群类型
     * @return 用户分群列表
     */
    List<UserSegment> findBySegmentType(String segmentType);

    /**
     * 根据分群状态查询用户分群
     * @param status 分群状态
     * @return 用户分群列表
     */
    List<UserSegment> findByStatus(Integer status);

    /**
     * 根据分群类型和分群状态查询用户分群
     * @param segmentType 分群类型
     * @param status 分群状态
     * @return 用户分群列表
     */
    List<UserSegment> findBySegmentTypeAndStatus(String segmentType, Integer status);

    /**
     * 查询用户数量大于等于指定值的分群
     * @param userCount 用户数量
     * @return 用户分群列表
     */
    List<UserSegment> findByUserCountGreaterThanEqual(Integer userCount);

    /**
     * 查询指定类型的活跃分群数量
     * @param segmentType 分群类型
     * @return 活跃分群数量
     */
    @Query("SELECT COUNT(*) FROM UserSegment u WHERE u.segmentType = :segmentType AND u.status = 1")
    Long countActiveSegmentsByType(@Param("segmentType") String segmentType);

    /**
     * 查询所有活跃分群的总用户数量
     * @return 总用户数量
     */
    @Query("SELECT SUM(u.userCount) FROM UserSegment u WHERE u.status = 1")
    Long sumUserCountOfActiveSegments();
}