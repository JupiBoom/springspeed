package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.dao.*;
import com.yasinyuan.testspring.model.*;
import com.yasinyuan.testspring.service.UserSegmentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserSegmentationServiceImpl implements UserSegmentationService {

    @Autowired
    private UserRfmRepository userRfmRepository;

    @Autowired
    private UserLifecycleRepository userLifecycleRepository;

    @Autowired
    private UserTagRepository userTagRepository;

    @Autowired
    private UserUserTagRepository userUserTagRepository;

    @Autowired
    private UserBehaviorRepository userBehaviorRepository;

    // RFM配置参数
    private static final int RECENCY_THRESHOLD_1 = 7;    // 最近7天
    private static final int RECENCY_THRESHOLD_2 = 30;   // 最近30天
    private static final int RECENCY_THRESHOLD_3 = 90;   // 最近90天

    private static final int FREQUENCY_THRESHOLD_1 = 5;  // 5次以上
    private static final int FREQUENCY_THRESHOLD_2 = 2;  // 2-4次

    private static final double MONETARY_THRESHOLD_1 = 1000; // 1000元以上
    private static final double MONETARY_THRESHOLD_2 = 200;  // 200-999元

    @Override
    @Transactional
    public List<UserRfm> calculateRfmModel(Date startDate, Date endDate) {
        // 获取所有下单行为
        List<UserBehavior> orderBehaviors = userBehaviorRepository.findByEventTypeAndCreateTimeBetween(
                "order", startDate, endDate);

        // 按用户分组统计
        Map<String, List<UserBehavior>> userOrderMap = orderBehaviors.stream()
                .collect(Collectors.groupingBy(UserBehavior::getUserId));

        List<UserRfm> rfmList = new ArrayList<>();
        Date now = new Date();

        for (Map.Entry<String, List<UserBehavior>> entry : userOrderMap.entrySet()) {
            String userId = entry.getKey();
            List<UserBehavior> behaviors = entry.getValue();

            // 计算Recency：距离最后一次购买的天数
            Date lastPurchaseTime = behaviors.stream()
                    .max(Comparator.comparing(UserBehavior::getCreateTime))
                    .map(UserBehavior::getCreateTime)
                    .orElse(now);
            long recencyDays = (now.getTime() - lastPurchaseTime.getTime()) / (1000 * 60 * 60 * 24);

            // 计算Frequency：购买次数
            long frequency = behaviors.size();

            // 计算Monetary：总购买金额
            double monetary = behaviors.stream()
                    .mapToDouble(b -> Optional.ofNullable(b.getAmount()).orElse(0.0))
                    .sum();

            // 计算RFM分数
            int rScore = calculateRScore(recencyDays);
            int fScore = calculateFScore(frequency);
            int mScore = calculateMScore(monetary);

            // 确定用户群体
            String segment = determineRfmSegment(rScore, fScore, mScore);

            // 保存或更新RFM信息
            UserRfm userRfm = userRfmRepository.findByUserId(userId).orElse(new UserRfm());
            userRfm.setUserId(userId);
            userRfm.setRecencyDays((int) recencyDays);
            userRfm.setFrequency((int) frequency);
            userRfm.setMonetary(monetary);
            userRfm.setrScore(rScore);
            userRfm.setfScore(fScore);
            userRfm.setmScore(mScore);
            userRfm.setSegment(segment);
            userRfm.setUpdateTime(now);

            rfmList.add(userRfmRepository.save(userRfm));
        }

        return rfmList;
    }

    private int calculateRScore(long recencyDays) {
        if (recencyDays <= RECENCY_THRESHOLD_1) return 3;
        if (recencyDays <= RECENCY_THRESHOLD_2) return 2;
        if (recencyDays <= RECENCY_THRESHOLD_3) return 1;
        return 0;
    }

    private int calculateFScore(long frequency) {
        if (frequency >= FREQUENCY_THRESHOLD_1) return 3;
        if (frequency >= FREQUENCY_THRESHOLD_2) return 2;
        if (frequency >= 1) return 1;
        return 0;
    }

    private int calculateMScore(double monetary) {
        if (monetary >= MONETARY_THRESHOLD_1) return 3;
        if (monetary >= MONETARY_THRESHOLD_2) return 2;
        if (monetary > 0) return 1;
        return 0;
    }

    private String determineRfmSegment(int rScore, int fScore, int mScore) {
        int totalScore = rScore + fScore + mScore;

        if (totalScore >= 7) return "重要价值用户";
        if (rScore >= 2 && fScore >= 1 && mScore >= 1) return "重要发展用户";
        if (rScore <= 1 && fScore >= 2 && mScore >= 2) return "重要保持用户";
        if (rScore <= 1 && fScore <= 1 && mScore >= 2) return "重要挽留用户";
        if (totalScore >= 4) return "潜力用户";
        if (totalScore >= 2) return "一般用户";
        return "流失用户";
    }

    @Override
    public UserRfm getUserRfm(String userId) {
        return userRfmRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public List<UserRfm> getUsersByRfmSegment(String segment) {
        return userRfmRepository.findBySegment(segment);
    }

    @Override
    public Map<String, Long> countUsersByRfmSegment() {
        return userRfmRepository.findAll().stream()
                .collect(Collectors.groupingBy(UserRfm::getSegment, Collectors.counting()));
    }

    @Override
    @Transactional
    public List<UserLifecycle> calculateUserLifecycle() {
        // 获取所有用户行为
        List<UserBehavior> allBehaviors = userBehaviorRepository.findAll();

        // 按用户分组
        Map<String, List<UserBehavior>> userBehaviorMap = allBehaviors.stream()
                .collect(Collectors.groupingBy(UserBehavior::getUserId));

        List<UserLifecycle> lifecycleList = new ArrayList<>();
        Date now = new Date();

        for (Map.Entry<String, List<UserBehavior>> entry : userBehaviorMap.entrySet()) {
            String userId = entry.getKey();
            List<UserBehavior> behaviors = entry.getValue();

            // 获取首次和最后活跃时间
            Date firstActiveTime = behaviors.stream()
                    .min(Comparator.comparing(UserBehavior::getCreateTime))
                    .map(UserBehavior::getCreateTime)
                    .orElse(now);
            Date lastActiveTime = behaviors.stream()
                    .max(Comparator.comparing(UserBehavior::getCreateTime))
                    .map(UserBehavior::getCreateTime)
                    .orElse(now);

            // 计算沉默天数
            long silentDays = (now.getTime() - lastActiveTime.getTime()) / (1000 * 60 * 60 * 24);

            // 计算活跃天数（去重）
            long activeDays = behaviors.stream()
                    .map(b -> b.getCreateTime().toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalDate())
                    .distinct()
                    .count();

            // 确定生命周期阶段
            String stage = determineLifecycleStage(firstActiveTime, lastActiveTime, silentDays);

            // 保存或更新生命周期信息
            UserLifecycle lifecycle = userLifecycleRepository.findByUserId(userId).orElse(new UserLifecycle());
            lifecycle.setUserId(userId);
            lifecycle.setStage(stage);
            lifecycle.setFirstActiveTime(firstActiveTime);
            lifecycle.setLastActiveTime(lastActiveTime);
            lifecycle.setSilentDays((int) silentDays);
            lifecycle.setActiveDays((int) activeDays);
            lifecycle.setUpdateTime(now);

            lifecycleList.add(userLifecycleRepository.save(lifecycle));
        }

        return lifecycleList;
    }

    private String determineLifecycleStage(Date firstActiveTime, Date lastActiveTime, long silentDays) {
        long daysSinceFirst = (new Date().getTime() - firstActiveTime.getTime()) / (1000 * 60 * 60 * 24);

        // 新客：首次访问7天内
        if (daysSinceFirst <= 7) return "新客";

        // 活跃：最近7天有活动
        if (silentDays <= 7) return "活跃";

        // 沉默：8-30天无活动
        if (silentDays <= 30) return "沉默";

        // 流失：30天以上无活动
        return "流失";
    }

    @Override
    public UserLifecycle getUserLifecycle(String userId) {
        return userLifecycleRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public List<UserLifecycle> getUsersByLifecycleStage(String stage) {
        return userLifecycleRepository.findByStage(stage);
    }

    @Override
    public Map<String, Long> countUsersByLifecycleStage() {
        return userLifecycleRepository.findAll().stream()
                .collect(Collectors.groupingBy(UserLifecycle::getStage, Collectors.counting()));
    }

    @Override
    @Transactional
    public UserTag saveUserTag(UserTag userTag) {
        return userTagRepository.save(userTag);
    }

    @Override
    @Transactional
    public UserTag updateUserTag(UserTag userTag) {
        userTag.setUpdateTime(new Date());
        return userTagRepository.save(userTag);
    }

    @Override
    @Transactional
    public void deleteUserTag(Long tagId) {
        userUserTagRepository.deleteByTagId(tagId);
        userTagRepository.deleteById(tagId);
    }

    @Override
    public List<UserTag> listUserTags() {
        return userTagRepository.findAll();
    }

    @Override
    @Transactional
    public void assignTagsToUser(String userId, List<Long> tagIds, String assignBy) {
        // 先删除已有标签
        userUserTagRepository.deleteByUserId(userId);

        // 添加新标签
        List<UserUserTag> userUserTags = tagIds.stream().map(tagId -> {
            UserUserTag userUserTag = new UserUserTag();
            userUserTag.setUserId(userId);
            userUserTag.setTagId(tagId);
            userUserTag.setAssignBy(assignBy);
            return userUserTag;
        }).collect(Collectors.toList());

        userUserTagRepository.saveAll(userUserTags);
    }

    @Override
    @Transactional
    public void removeTagsFromUser(String userId, List<Long> tagIds) {
        List<UserUserTag> userUserTags = userUserTagRepository.findByUserId(userId);
        userUserTags.stream()
                .filter(uut -> tagIds.contains(uut.getTagId()))
                .forEach(userUserTagRepository::delete);
    }

    @Override
    public Set<UserTag> getUserTags(String userId) {
        List<UserUserTag> userUserTags = userUserTagRepository.findByUserId(userId);
        List<Long> tagIds = userUserTags.stream().map(UserUserTag::getTagId).collect(Collectors.toList());
        return new HashSet<>(userTagRepository.findAllById(tagIds));
    }

    @Override
    public List<String> getUsersByTag(Long tagId) {
        return userUserTagRepository.findByTagId(tagId).stream()
                .map(UserUserTag::getUserId)
                .collect(Collectors.toList());
    }
}
