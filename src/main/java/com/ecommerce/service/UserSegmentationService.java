package com.ecommerce.service;

import com.ecommerce.entity.UserSegment;
import java.util.Date;
import java.util.List;

public interface UserSegmentationService {

    /**
     * 根据RFM模型进行用户分群
     * @param recencyThreshold 最近购买天数阈值
     * @param frequencyThreshold 购买频率阈值
     * @param monetaryThreshold 购买金额阈值
     * @return 用户分群列表
     */
    List<UserSegment> segmentUsersByRFM(Integer recencyThreshold, Integer frequencyThreshold, Double monetaryThreshold);

    /**
     * 根据用户生命周期进行用户分群
     * @param newUserDays 新客天数阈值
     * @param activeUserDays 活跃用户天数阈值
     * @param silentUserDays 沉默用户天数阈值
     * @return 用户分群列表
     */
    List<UserSegment> segmentUsersByLifecycle(Integer newUserDays, Integer activeUserDays, Integer silentUserDays);

    /**
     * 创建自定义标签分群
     * @param segmentName 分群名称
     * @param userIds 用户ID列表
     * @param createdBy 创建者
     * @return 用户分群列表
     */
    List<UserSegment> createCustomUserSegment(String segmentName, List<Long> userIds, String createdBy);

    /**
     * 根据用户ID查询用户分群信息
     * @param userId 用户ID
     * @return 用户分群列表
     */
    List<UserSegment> getUserSegmentsByUserId(Long userId);

    /**
     * 根据分群名称查询用户分群信息
     * @param segmentName 分群名称
     * @return 用户分群列表
     */
    List<UserSegment> getUserSegmentsBySegmentName(String segmentName);

    /**
     * 根据用户生命周期阶段查询用户
     * @param lifecycleStage 生命周期阶段（NEW, ACTIVE, SILENT, CHURNED）
     * @return 用户分群列表
     */
    List<UserSegment> getUserSegmentsByLifecycleStage(String lifecycleStage);

    /**
     * 更新用户分群信息
     * @param userSegment 用户分群信息
     * @return 用户分群信息
     */
    UserSegment updateUserSegment(UserSegment userSegment);

    /**
     * 删除用户分群
     * @param segmentId 分群ID
     */
    void deleteUserSegment(Long segmentId);

    /**
     * 统计各分群的用户数量
     * @return 分群用户数量统计（分群名称，用户数量）
     */
    List<Object[]> countUsersBySegment();

    /**
     * 统计各生命周期阶段的用户数量
     * @return 生命周期用户数量统计（生命周期阶段，用户数量）
     */
    List<Object[]> countUsersByLifecycleStage();
}
