package com.springspeed.userbehavior.repository;

import com.springspeed.userbehavior.entity.UserSegmentMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 用户分群成员数据访问接口
 * 用于操作MySQL中的用户分群成员数据
 */
@Repository
public interface UserSegmentMemberRepository extends JpaRepository<UserSegmentMember, Long> {

    /**
     * 根据分群ID查询用户分群成员
     * @param segmentId 分群ID
     * @return 用户分群成员列表
     */
    List<UserSegmentMember> findBySegmentId(String segmentId);

    /**
     * 根据用户ID查询用户分群成员
     * @param userId 用户ID
     * @return 用户分群成员列表
     */
    List<UserSegmentMember> findByUserId(String userId);

    /**
     * 根据分群ID和用户ID查询用户分群成员
     * @param segmentId 分群ID
     * @param userId 用户ID
     * @return 用户分群成员
     */
    Optional<UserSegmentMember> findBySegmentIdAndUserId(String segmentId, String userId);

    /**
     * 根据分群ID和成员状态查询用户分群成员
     * @param segmentId 分群ID
     * @param status 成员状态
     * @return 用户分群成员列表
     */
    List<UserSegmentMember> findBySegmentIdAndStatus(String segmentId, Integer status);

    /**
     * 根据用户ID和成员状态查询用户分群成员
     * @param userId 用户ID
     * @param status 成员状态
     * @return 用户分群成员列表
     */
    List<UserSegmentMember> findByUserIdAndStatus(String userId, Integer status);

    /**
     * 查询指定分群的活跃成员数量
     * @param segmentId 分群ID
     * @return 活跃成员数量
     */
    @Query("SELECT COUNT(*) FROM UserSegmentMember u WHERE u.segmentId = :segmentId AND u.status = 1")
    Long countActiveMembersBySegmentId(@Param("segmentId") String segmentId);

    /**
     * 查询指定用户加入的活跃分群数量
     * @param userId 用户ID
     * @return 活跃分群数量
     */
    @Query("SELECT COUNT(*) FROM UserSegmentMember u WHERE u.userId = :userId AND u.status = 1")
    Long countActiveSegmentsByUserId(@Param("userId") String userId);

    /**
     * 查询指定时间范围内加入分群的成员数量
     * @param segmentId 分群ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 成员数量
     */
    @Query("SELECT COUNT(*) FROM UserSegmentMember u WHERE u.segmentId = :segmentId AND u.joinTime BETWEEN :startDate AND :endDate")
    Long countMembersJoinedByTimeRange(@Param("segmentId") String segmentId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 查询指定分群的成员ID列表
     * @param segmentId 分群ID
     * @return 成员ID列表
     */
    @Query("SELECT u.userId FROM UserSegmentMember u WHERE u.segmentId = :segmentId AND u.status = 1")
    List<String> findUserIdsBySegmentId(@Param("segmentId") String segmentId);
}