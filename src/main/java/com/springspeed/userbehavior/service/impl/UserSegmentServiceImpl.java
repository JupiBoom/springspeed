package com.springspeed.userbehavior.service.impl;

import com.springspeed.userbehavior.entity.UserSegment;
import com.springspeed.userbehavior.entity.UserSegmentMember;
import com.springspeed.userbehavior.repository.UserSegmentRepository;
import com.springspeed.userbehavior.repository.UserSegmentMemberRepository;
import com.springspeed.userbehavior.service.UserSegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户分群服务实现类
 * 实现用户分群管理的业务逻辑
 */
@Service
@Transactional(readOnly = true)
public class UserSegmentServiceImpl implements UserSegmentService {

    @Autowired
    private UserSegmentRepository userSegmentRepository;

    @Autowired
    private UserSegmentMemberRepository userSegmentMemberRepository;

    /**
     * 创建用户分群
     * @param userSegment 用户分群实体
     * @return 创建的用户分群
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserSegment createUserSegment(UserSegment userSegment) {
        if (userSegment == null) {
            throw new IllegalArgumentException("用户分群实体不能为空");
        }

        // 设置默认值
        if (userSegment.getSegmentId() == null || userSegment.getSegmentId().isEmpty()) {
            userSegment.setSegmentId(UUID.randomUUID().toString());
        }

        if (userSegment.getSegmentType() == null || userSegment.getSegmentType().isEmpty()) {
            userSegment.setSegmentType("custom"); // 默认自定义分群
        }

        if (userSegment.getStatus() == null) {
            userSegment.setStatus(1); // 默认活跃状态
        }

        if (userSegment.getCreateTime() == null) {
            userSegment.setCreateTime(new Date());
        }

        if (userSegment.getUpdateTime() == null) {
            userSegment.setUpdateTime(new Date());
        }

        if (userSegment.getUserCount() == null) {
            userSegment.setUserCount(0); // 默认用户数量为0
        }

        // 保存用户分群
        return userSegmentRepository.save(userSegment);
    }

    /**
     * 更新用户分群
     * @param userSegment 用户分群实体
     * @return 更新的用户分群
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserSegment updateUserSegment(UserSegment userSegment) {
        if (userSegment == null) {
            throw new IllegalArgumentException("用户分群实体不能为空");
        }

        if (userSegment.getId() == null) {
            throw new IllegalArgumentException("用户分群ID不能为空");
        }

        // 更新更新时间
        userSegment.setUpdateTime(new Date());

        // 保存用户分群
        return userSegmentRepository.save(userSegment);
    }

    /**
     * 根据分群ID查询用户分群
     * @param segmentId 分群ID
     * @return 用户分群
     */
    @Override
    public Optional<UserSegment> getUserSegmentBySegmentId(String segmentId) {
        if (segmentId == null || segmentId.isEmpty()) {
            throw new IllegalArgumentException("分群ID不能为空");
        }

        return userSegmentRepository.findBySegmentId(segmentId);
    }

    /**
     * 根据分群名称查询用户分群
     * @param segmentName 分群名称
     * @return 用户分群列表
     */
    @Override
    public List<UserSegment> getUserSegmentsBySegmentName(String segmentName) {
        if (segmentName == null || segmentName.isEmpty()) {
            throw new IllegalArgumentException("分群名称不能为空");
        }

        return userSegmentRepository.findBySegmentName(segmentName);
    }

    /**
     * 根据分群类型查询用户分群
     * @param segmentType 分群类型
     * @return 用户分群列表
     */
    @Override
    public List<UserSegment> getUserSegmentsBySegmentType(String segmentType) {
        if (segmentType == null || segmentType.isEmpty()) {
            throw new IllegalArgumentException("分群类型不能为空");
        }

        return userSegmentRepository.findBySegmentType(segmentType);
    }

    /**
     * 查询活跃的用户分群
     * @return 活跃用户分群列表
     */
    @Override
    public List<UserSegment> getActiveUserSegments() {
        return userSegmentRepository.findByStatus(1);
    }

    /**
     * 删除用户分群
     * @param segmentId 分群ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserSegment(String segmentId) {
        if (segmentId == null || segmentId.isEmpty()) {
            throw new IllegalArgumentException("分群ID不能为空");
        }

        // 删除分群成员
        List<UserSegmentMember> members = userSegmentMemberRepository.findBySegmentId(segmentId);
        userSegmentMemberRepository.deleteAll(members);

        // 删除分群
        Optional<UserSegment> optionalSegment = userSegmentRepository.findBySegmentId(segmentId);
        optionalSegment.ifPresent(userSegmentRepository::delete);
    }

    /**
     * 批量删除用户分群
     * @param segmentIds 分群ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteUserSegments(List<String> segmentIds) {
        if (segmentIds == null || segmentIds.isEmpty()) {
            throw new IllegalArgumentException("分群ID列表不能为空");
        }

        // 批量删除分群成员
        for (String segmentId : segmentIds) {
            List<UserSegmentMember> members = userSegmentMemberRepository.findBySegmentId(segmentId);
            userSegmentMemberRepository.deleteAll(members);
        }

        // 批量删除分群
        List<UserSegment> segments = userSegmentRepository.findAll(Specification.where(
                (root, query, criteriaBuilder) -> root.get("segmentId").in(segmentIds)
        ));
        userSegmentRepository.deleteAll(segments);
    }

    /**
     * 根据条件查询用户分群
     * @param segmentName 分群名称（模糊查询）
     * @param segmentType 分群类型
     * @param status 分群状态
     * @param pageable 分页参数
     * @return 用户分群分页列表
     */
    @Override
    public Page<UserSegment> getUserSegmentsByCondition(String segmentName, String segmentType, Integer status, Pageable pageable) {
        Specification<UserSegment> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (segmentName != null && !segmentName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("segmentName"), "%" + segmentName + "%"));
            }

            if (segmentType != null && !segmentType.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("segmentType"), segmentType));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return userSegmentRepository.findAll(specification, pageable);
    }

    /**
     * 查询用户分群统计数据
     * @return 统计数据Map
     */
    @Override
    public Map<String, Object> getUserSegmentStatistics() {
        // 总分群数
        long totalSegmentCount = userSegmentRepository.count();

        // 活跃分群数
        long activeSegmentCount = userSegmentRepository.countActiveSegmentsByType("all");

        // 关闭分群数
        long closedSegmentCount = totalSegmentCount - activeSegmentCount;

        // 总用户数
        Long totalUserCount = userSegmentRepository.sumUserCountOfActiveSegments();
        if (totalUserCount == null) {
            totalUserCount = 0L;
        }

        // 平均每个分群的用户数
        double avgUserCountPerSegment = totalSegmentCount > 0 ? (double) totalUserCount / totalSegmentCount : 0;

        // 构建统计数据Map
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalSegmentCount", totalSegmentCount);
        statistics.put("activeSegmentCount", activeSegmentCount);
        statistics.put("closedSegmentCount", closedSegmentCount);
        statistics.put("totalUserCount", totalUserCount);
        statistics.put("avgUserCountPerSegment", avgUserCountPerSegment);

        return statistics;
    }

    /**
     * 查询分群类型统计数据
     * @return 分群类型统计数据
     */
    @Override
    public List<Map<String, Object>> getSegmentTypeStatistics() {
        // 查询所有分群
        List<UserSegment> segments = userSegmentRepository.findAll();

        // 统计各分群类型的数量和用户数
        Map<String, Map<String, Object>> typeStatisticsMap = new HashMap<>();

        for (UserSegment segment : segments) {
            String segmentType = segment.getSegmentType();

            typeStatisticsMap.computeIfAbsent(segmentType, k -> {
                Map<String, Object> statistics = new HashMap<>();
                statistics.put("segmentType", segmentType);
                statistics.put("segmentCount", 0L);
                statistics.put("userCount", 0L);
                return statistics;
            });

            // 更新分群数量
            typeStatisticsMap.get(segmentType).put("segmentCount", 
                    (long) typeStatisticsMap.get(segmentType).get("segmentCount") + 1);

            // 更新用户数量
            typeStatisticsMap.get(segmentType).put("userCount", 
                    (long) typeStatisticsMap.get(segmentType).get("userCount") + segment.getUserCount());
        }

        // 转换为结果列表并按分群数量降序排序
        List<Map<String, Object>> typeStatistics = typeStatisticsMap.values().stream()
                .sorted((a, b) -> Long.compare((long) b.get("segmentCount"), (long) a.get("segmentCount")))
                .collect(Collectors.toList());

        return typeStatistics;
    }

    /**
     * 添加用户到分群
     * @param segmentId 分群ID
     * @param userId 用户ID
     * @return 用户分群成员
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserSegmentMember addUserToSegment(String segmentId, String userId) {
        if (segmentId == null || segmentId.isEmpty()) {
            throw new IllegalArgumentException("分群ID不能为空");
        }

        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        // 检查分群是否存在
        Optional<UserSegment> optionalSegment = userSegmentRepository.findBySegmentId(segmentId);
        if (!optionalSegment.isPresent()) {
            throw new IllegalArgumentException("分群不存在");
        }

        // 检查用户是否已经在分群中
        Optional<UserSegmentMember> optionalMember = userSegmentMemberRepository.findBySegmentIdAndUserId(segmentId, userId);
        if (optionalMember.isPresent()) {
            UserSegmentMember member = optionalMember.get();
            if (member.getStatus() == 1) {
                // 用户已经在分群中且状态为活跃
                return member;
            } else {
                // 用户已经在分群中但状态为已退出，更新状态为活跃
                member.setStatus(1);
                member.setJoinTime(new Date());
                member.setExitTime(null);
                member.setUpdateTime(new Date());
                return userSegmentMemberRepository.save(member);
            }
        }

        // 创建新的分群成员
        UserSegmentMember member = new UserSegmentMember(segmentId, userId);
        UserSegmentMember savedMember = userSegmentMemberRepository.save(member);

        // 更新分群的用户数量
        UserSegment segment = optionalSegment.get();
        segment.setUserCount(segment.getUserCount() + 1);
        segment.setUpdateTime(new Date());
        userSegmentRepository.save(segment);

        return savedMember;
    }

    /**
     * 批量添加用户到分群
     * @param segmentId 分群ID
     * @param userIds 用户ID列表
     * @return 添加成功的用户数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchAddUsersToSegment(String segmentId, List<String> userIds) {
        if (segmentId == null || segmentId.isEmpty()) {
            throw new IllegalArgumentException("分群ID不能为空");
        }

        if (userIds == null || userIds.isEmpty()) {
            throw new IllegalArgumentException("用户ID列表不能为空");
        }

        // 检查分群是否存在
        Optional<UserSegment> optionalSegment = userSegmentRepository.findBySegmentId(segmentId);
        if (!optionalSegment.isPresent()) {
            throw new IllegalArgumentException("分群不存在");
        }

        // 检查哪些用户已经在分群中
        List<UserSegmentMember> existingMembers = userSegmentMemberRepository.findBySegmentId(segmentId);
        Set<String> existingUserIds = existingMembers.stream()
                .filter(member -> member.getStatus() == 1)
                .map(UserSegmentMember::getUserId)
                .collect(Collectors.toSet());

        // 过滤出不在分群中的用户
        List<String> newUserIds = userIds.stream()
                .filter(userId -> !existingUserIds.contains(userId))
                .collect(Collectors.toList());

        // 批量创建新的分群成员
        List<UserSegmentMember> newMembers = newUserIds.stream()
                .map(userId -> new UserSegmentMember(segmentId, userId))
                .collect(Collectors.toList());
        userSegmentMemberRepository.saveAll(newMembers);

        // 更新分群的用户数量
        UserSegment segment = optionalSegment.get();
        segment.setUserCount(segment.getUserCount() + newUserIds.size());
        segment.setUpdateTime(new Date());
        userSegmentRepository.save(segment);

        return newUserIds.size();
    }

    /**
     * 从分群中移除用户
     * @param segmentId 分群ID
     * @param userId 用户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUserFromSegment(String segmentId, String userId) {
        if (segmentId == null || segmentId.isEmpty()) {
            throw new IllegalArgumentException("分群ID不能为空");
        }

        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        // 检查分群是否存在
        Optional<UserSegment> optionalSegment = userSegmentRepository.findBySegmentId(segmentId);
        if (!optionalSegment.isPresent()) {
            throw new IllegalArgumentException("分群不存在");
        }

        // 检查用户是否在分群中
        Optional<UserSegmentMember> optionalMember = userSegmentMemberRepository.findBySegmentIdAndUserId(segmentId, userId);
        if (!optionalMember.isPresent()) {
            throw new IllegalArgumentException("用户不在分群中");
        }

        UserSegmentMember member = optionalMember.get();
        if (member.getStatus() == 0) {
            // 用户已经不在分群中
            return;
        }

        // 更新分群成员状态为已退出
        member.setStatus(0);
        member.setExitTime(new Date());
        member.setUpdateTime(new Date());
        userSegmentMemberRepository.save(member);

        // 更新分群的用户数量
        UserSegment segment = optionalSegment.get();
        segment.setUserCount(segment.getUserCount() - 1);
        segment.setUpdateTime(new Date());
        userSegmentRepository.save(segment);
    }

    /**
     * 批量从分群中移除用户
     * @param segmentId 分群ID
     * @param userIds 用户ID列表
     * @return 移除成功的用户数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchRemoveUsersFromSegment(String segmentId, List<String> userIds) {
        if (segmentId == null || segmentId.isEmpty()) {
            throw new IllegalArgumentException("分群ID不能为空");
        }

        if (userIds == null || userIds.isEmpty()) {
            throw new IllegalArgumentException("用户ID列表不能为空");
        }

        // 检查分群是否存在
        Optional<UserSegment> optionalSegment = userSegmentRepository.findBySegmentId(segmentId);
        if (!optionalSegment.isPresent()) {
            throw new IllegalArgumentException("分群不存在");
        }

        // 检查哪些用户在分群中且状态为活跃
        List<UserSegmentMember> existingMembers = userSegmentMemberRepository.findBySegmentId(segmentId);
        Map<String, UserSegmentMember> activeMemberMap = existingMembers.stream()
                .filter(member -> member.getStatus() == 1)
                .collect(Collectors.toMap(UserSegmentMember::getUserId, member -> member));

        // 过滤出在分群中且状态为活跃的用户
        List<String> removeUserIds = userIds.stream()
                .filter(userId -> activeMemberMap.containsKey(userId))
                .collect(Collectors.toList());

        // 批量更新分群成员状态为已退出
        Date now = new Date();
        List<UserSegmentMember> updateMembers = removeUserIds.stream()
                .map(userId -> {
                    UserSegmentMember member = activeMemberMap.get(userId);
                    member.setStatus(0);
                    member.setExitTime(now);
                    member.setUpdateTime(now);
                    return member;
                })
                .collect(Collectors.toList());
        userSegmentMemberRepository.saveAll(updateMembers);

        // 更新分群的用户数量
        UserSegment segment = optionalSegment.get();
        segment.setUserCount(segment.getUserCount() - removeUserIds.size());
        segment.setUpdateTime(now);
        userSegmentRepository.save(segment);

        return removeUserIds.size();
    }

    /**
     * 根据分群ID查询分群成员
     * @param segmentId 分群ID
     * @param pageable 分页参数
     * @return 分群成员分页列表
     */
    @Override
    public Page<UserSegmentMember> getSegmentMembersBySegmentId(String segmentId, Pageable pageable) {
        if (segmentId == null || segmentId.isEmpty()) {
            throw new IllegalArgumentException("分群ID不能为空");
        }

        // 检查分群是否存在
        Optional<UserSegment> optionalSegment = userSegmentRepository.findBySegmentId(segmentId);
        if (!optionalSegment.isPresent()) {
            throw new IllegalArgumentException("分群不存在");
        }

        // 查询分群成员
        return userSegmentMemberRepository.findBySegmentId(segmentId, pageable);
    }

    /**
     * 根据用户ID查询用户所属的分群
     * @param userId 用户ID
     * @return 用户所属的分群列表
     */
    @Override
    public List<UserSegment> getUserSegmentsByUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        // 查询用户所属的分群成员
        List<UserSegmentMember> members = userSegmentMemberRepository.findByUserIdAndStatus(userId, 1);

        // 查询对应的分群
        List<String> segmentIds = members.stream()
                .map(UserSegmentMember::getSegmentId)
                .collect(Collectors.toList());

        if (segmentIds.isEmpty()) {
            return Collections.emptyList();
        }

        return userSegmentRepository.findAll(Specification.where(
                (root, query, criteriaBuilder) -> root.get("segmentId").in(segmentIds)
        ));
    }

    /**
     * 检查用户是否属于某个分群
     * @param segmentId 分群ID
     * @param userId 用户ID
     * @return true：属于，false：不属于
     */
    @Override
    public boolean checkUserInSegment(String segmentId, String userId) {
        if (segmentId == null || segmentId.isEmpty()) {
            throw new IllegalArgumentException("分群ID不能为空");
        }

        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        Optional<UserSegmentMember> optionalMember = userSegmentMemberRepository.findBySegmentIdAndUserId(segmentId, userId);
        return optionalMember.isPresent() && optionalMember.get().getStatus() == 1;
    }

    /**
     * 根据RFM模型进行用户分群
     * @param recencyDays 最近购买天数阈值
     * @param frequencyThreshold 购买频率阈值
     * @param monetaryThreshold 购买金额阈值
     * @return RFM分群结果
     */
    @Override
    public Map<String, List<String>> segmentUsersByRFM(Integer recencyDays, Integer frequencyThreshold, Double monetaryThreshold) {
        // 设置默认阈值
        if (recencyDays == null || recencyDays <= 0) {
            recencyDays = 30;
        }

        if (frequencyThreshold == null || frequencyThreshold <= 0) {
            frequencyThreshold = 3;
        }

        if (monetaryThreshold == null || monetaryThreshold <= 0) {
            monetaryThreshold = 100.0;
        }

        // 模拟RFM分群结果
        // 实际项目中应该从数据库中查询用户的购买记录，计算RFM值，然后进行分群
        Map<String, List<String>> rfmSegmentation = new HashMap<>();

        // 重要价值客户（最近购买、购买频繁、金额高）
        rfmSegmentation.put("重要价值客户", Arrays.asList("user1", "user2", "user3"));

        // 重要发展客户（最近购买、购买不频繁、金额高）
        rfmSegmentation.put("重要发展客户", Arrays.asList("user4", "user5"));

        // 重要保持客户（很久没购买、购买频繁、金额高）
        rfmSegmentation.put("重要保持客户", Arrays.asList("user6", "user7"));

        // 重要挽留客户（很久没购买、购买不频繁、金额高）
        rfmSegmentation.put("重要挽留客户", Arrays.asList("user8"));

        // 一般价值客户（最近购买、购买频繁、金额低）
        rfmSegmentation.put("一般价值客户", Arrays.asList("user9", "user10"));

        // 一般发展客户（最近购买、购买不频繁、金额低）
        rfmSegmentation.put("一般发展客户", Arrays.asList("user11", "user12"));

        // 一般保持客户（很久没购买、购买频繁、金额低）
        rfmSegmentation.put("一般保持客户", Arrays.asList("user13"));

        // 一般挽留客户（很久没购买、购买不频繁、金额低）
        rfmSegmentation.put("一般挽留客户", Arrays.asList("user14", "user15"));

        return rfmSegmentation;
    }

    /**
     * 根据用户生命周期进行用户分群
     * @param newUserDays 新客天数阈值
     * @param activeUserDays 活跃用户天数阈值
     * @param churnUserDays 流失用户天数阈值
     * @return 生命周期分群结果
     */
    @Override
    public Map<String, List<String>> segmentUsersByLifecycle(Integer newUserDays, Integer activeUserDays, Integer churnUserDays) {
        // 设置默认阈值
        if (newUserDays == null || newUserDays <= 0) {
            newUserDays = 7;
        }

        if (activeUserDays == null || activeUserDays <= 0) {
            activeUserDays = 30;
        }

        if (churnUserDays == null || churnUserDays <= 0) {
            churnUserDays = 90;
        }

        // 模拟用户生命周期分群结果
        // 实际项目中应该从数据库中查询用户的注册时间、最近活跃时间等，然后进行分群
        Map<String, List<String>> lifecycleSegmentation = new HashMap<>();

        // 新客（注册时间<=新客天数阈值）
        lifecycleSegmentation.put("新客", Arrays.asList("user1", "user2", "user3"));

        // 活跃用户（最近活跃时间<=活跃用户天数阈值）
        lifecycleSegmentation.put("活跃用户", Arrays.asList("user4", "user5", "user6", "user7"));

        // 沉默用户（最近活跃时间>活跃用户天数阈值且<=流失用户天数阈值）
        lifecycleSegmentation.put("沉默用户", Arrays.asList("user8", "user9", "user10"));

        // 流失用户（最近活跃时间>流失用户天数阈值）
        lifecycleSegmentation.put("流失用户", Arrays.asList("user11", "user12", "user13", "user14", "user15"));

        return lifecycleSegmentation;
    }

    /**
     * 导出分群成员列表
     * @param segmentId 分群ID
     * @return 分群成员列表
     */
    @Override
    public List<Map<String, Object>> exportSegmentMembers(String segmentId) {
        if (segmentId == null || segmentId.isEmpty()) {
            throw new IllegalArgumentException("分群ID不能为空");
        }

        // 查询分群成员
        List<UserSegmentMember> members = userSegmentMemberRepository.findBySegmentIdAndStatus(segmentId, 1);

        // 转换为导出格式
        List<Map<String, Object>> exportMembers = members.stream()
                .map(member -> {
                    Map<String, Object> exportMember = new HashMap<>();
                    exportMember.put("segmentId", member.getSegmentId());
                    exportMember.put("userId", member.getUserId());
                    exportMember.put("joinTime", member.getJoinTime());
                    exportMember.put("createTime", member.getCreateTime());
                    exportMember.put("updateTime", member.getUpdateTime());
                    return exportMember;
                })
                .collect(Collectors.toList());

        return exportMembers;
    }
}