package com.springspeed.userbehavior.service;

import com.springspeed.userbehavior.entity.UserSegment;
import com.springspeed.userbehavior.entity.UserSegmentMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 用户分群服务接口
 * 定义用户分群管理的业务方法
 */
public interface UserSegmentService {

    /**
     * 创建用户分群
     * @param userSegment 用户分群实体
     * @return 创建的用户分群
     */
    UserSegment createUserSegment(UserSegment userSegment);

    /**
     * 更新用户分群
     * @param userSegment 用户分群实体
     * @return 更新的用户分群
     */
    UserSegment updateUserSegment(UserSegment userSegment);

    /**
     * 根据分群ID查询用户分群
     * @param segmentId 分群ID
     * @return 用户分群
     */
    Optional<UserSegment> getUserSegmentBySegmentId(String segmentId);

    /**
     * 根据分群名称查询用户分群
     * @param segmentName 分群名称
     * @return 用户分群列表
     */
    List<UserSegment> getUserSegmentsBySegmentName(String segmentName);

    /**
     * 根据分群类型查询用户分群
     * @param segmentType 分群类型
     * @return 用户分群列表
     */
    List<UserSegment> getUserSegmentsBySegmentType(String segmentType);

    /**
     * 查询活跃的用户分群
     * @return 活跃用户分群列表
     */
    List<UserSegment> getActiveUserSegments();

    /**
     * 删除用户分群
     * @param segmentId 分群ID
     */
    void deleteUserSegment(String segmentId);

    /**
     * 批量删除用户分群
     * @param segmentIds 分群ID列表
     */
    void batchDeleteUserSegments(List<String> segmentIds);

    /**
     * 根据条件查询用户分群
     * @param segmentName 分群名称（模糊查询）
     * @param segmentType 分群类型
     * @param status 分群状态
     * @param pageable 分页参数
     * @return 用户分群分页列表
     */
    Page<UserSegment> getUserSegmentsByCondition(String segmentName, String segmentType, Integer status, Pageable pageable);

    /**
     * 查询用户分群统计数据
     * @return 统计数据Map
     */
    Map<String, Object> getUserSegmentStatistics();

    /**
     * 查询分群类型统计数据
     * @return 分群类型统计数据
     */
    List<Map<String, Object>> getSegmentTypeStatistics();

    /**
     * 添加用户到分群
     * @param segmentId 分群ID
     * @param userId 用户ID
     * @return 用户分群成员
     */
    UserSegmentMember addUserToSegment(String segmentId, String userId);

    /**
     * 批量添加用户到分群
     * @param segmentId 分群ID
     * @param userIds 用户ID列表
     * @return 添加成功的用户数量
     */
    int batchAddUsersToSegment(String segmentId, List<String> userIds);

    /**
     * 从分群中移除用户
     * @param segmentId 分群ID
     * @param userId 用户ID
     */
    void removeUserFromSegment(String segmentId, String userId);

    /**
     * 批量从分群中移除用户
     * @param segmentId 分群ID
     * @param userIds 用户ID列表
     * @return 移除成功的用户数量
     */
    int batchRemoveUsersFromSegment(String segmentId, List<String> userIds);

    /**
     * 根据分群ID查询分群成员
     * @param segmentId 分群ID
     * @param pageable 分页参数
     * @return 分群成员分页列表
     */
    Page<UserSegmentMember> getSegmentMembersBySegmentId(String segmentId, Pageable pageable);

    /**
     * 根据用户ID查询用户所属的分群
     * @param userId 用户ID
     * @return 用户所属的分群列表
     */
    List<UserSegment> getUserSegmentsByUserId(String userId);

    /**
     * 检查用户是否属于某个分群
     * @param segmentId 分群ID
     * @param userId 用户ID
     * @return true：属于，false：不属于
     */
    boolean checkUserInSegment(String segmentId, String userId);

    /**
     * 根据RFM模型进行用户分群
     * @param recencyDays 最近购买天数阈值
     * @param frequencyThreshold 购买频率阈值
     * @param monetaryThreshold 购买金额阈值
     * @return RFM分群结果
     */
    Map<String, List<String>> segmentUsersByRFM(Integer recencyDays, Integer frequencyThreshold, Double monetaryThreshold);

    /**
     * 根据用户生命周期进行用户分群
     * @param newUserDays 新客天数阈值
     * @param activeUserDays 活跃用户天数阈值
     * @param churnUserDays 流失用户天数阈值
     * @return 生命周期分群结果
     */
    Map<String, List<String>> segmentUsersByLifecycle(Integer newUserDays, Integer activeUserDays, Integer churnUserDays);

    /**
     * 导出分群成员列表
     * @param segmentId 分群ID
     * @return 分群成员列表
     */
    List<Map<String, Object>> exportSegmentMembers(String segmentId);
}