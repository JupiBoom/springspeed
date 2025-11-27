package com.springspeed.service;

import com.springspeed.model.User;
import com.springspeed.model.UserBehavior;
import com.springspeed.model.Product;
import com.springspeed.repository.UserRepository;
import com.springspeed.repository.UserBehaviorRepository;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * User Profile Service
 * 用户画像服务，包括兴趣标签提取和RFM模型分析
 */
@Service
@Transactional
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBehaviorRepository userBehaviorRepository;

    /**
     * 提取用户兴趣标签
     * 根据用户的浏览、购买、收藏等行为计算兴趣标签
     */
    public String extractUserInterestTags(Long userId) {
        // 获取用户的所有行为数据
        List<UserBehavior> userBehaviors = userBehaviorRepository.findByUserId(userId);
        if (userBehaviors.isEmpty()) {
            return "";
        }

        // 计算商品标签权重
        Map<String, Integer> tagWeightMap = new HashMap<>();
        for (UserBehavior behavior : userBehaviors) {
            Product product = behavior.getProduct();
            if (product != null && product.getTags() != null) {
                String[] tags = product.getTags().split(",");
                for (String tag : tags) {
                    tag = tag.trim();
                    if (!tag.isEmpty()) {
                        int weight = behavior.getBehaviorWeight();
                        tagWeightMap.put(tag, tagWeightMap.getOrDefault(tag, 0) + weight);
                    }
                }
            }
        }

        // 按权重排序，取前10个标签
        List<String> topTags = tagWeightMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return String.join(",", topTags);
    }

    /**
     * 计算用户RFM模型
     * R: Recency（最近一次消费时间）
     * F: Frequency（消费频率）
     * M: Monetary（消费金额）
     */
    public void calculateUserRFM(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return;
        }

        // 获取用户的购买行为
        List<UserBehavior> purchaseBehaviors = userBehaviorRepository.findByUserIdAndBehaviorType(userId, UserBehavior.BEHAVIOR_PURCHASE);
        if (purchaseBehaviors.isEmpty()) {
            user.setRecency(999); // 很久没有消费
            user.setFrequency(0);
            user.setMonetary(0.0);
            user.setRfmScore(0);
            user.setUserSegment("潜在客户");
            userRepository.save(user);
            return;
        }

        // 计算Recency（最近一次消费距离现在的天数）
        Date lastPurchaseDate = purchaseBehaviors.stream()
                .max(Comparator.comparing(UserBehavior::getCreatedTime))
                .get()
                .getCreatedTime();
        long daysSinceLastPurchase = (new Date().getTime() - lastPurchaseDate.getTime()) / (1000 * 60 * 60 * 24);
        int recency = (int) daysSinceLastPurchase;

        // 计算Frequency（消费频率）
        int frequency = purchaseBehaviors.size();

        // 计算Monetary（消费金额）
        double monetary = purchaseBehaviors.stream()
                .mapToDouble(b -> b.getProduct() != null ? b.getProduct().getPrice() : 0.0)
                .sum();

        // 计算RFM分数（每个维度1-5分）
        int rScore = calculateRScore(recency);
        int fScore = calculateFScore(frequency);
        int mScore = calculateMScore(monetary);
        int rfmScore = rScore * 100 + fScore * 10 + mScore;

        // 用户细分
        String userSegment = segmentUser(rScore, fScore, mScore);

        // 更新用户信息
        user.setRecency(recency);
        user.setFrequency(frequency);
        user.setMonetary(monetary);
        user.setRfmScore(rfmScore);
        user.setUserSegment(userSegment);
        userRepository.save(user);
    }

    /**
     * 计算Recency分数（1-5分，分数越高表示越近）
     */
    private int calculateRScore(int recency) {
        if (recency <= 7) return 5;
        if (recency <= 14) return 4;
        if (recency <= 30) return 3;
        if (recency <= 90) return 2;
        return 1;
    }

    /**
     * 计算Frequency分数（1-5分，分数越高表示频率越高）
     */
    private int calculateFScore(int frequency) {
        if (frequency >= 10) return 5;
        if (frequency >= 5) return 4;
        if (frequency >= 3) return 3;
        if (frequency >= 1) return 2;
        return 1;
    }

    /**
     * 计算Monetary分数（1-5分，分数越高表示金额越高）
     */
    private int calculateMScore(double monetary) {
        if (monetary >= 1000) return 5;
        if (monetary >= 500) return 4;
        if (monetary >= 200) return 3;
        if (monetary >= 50) return 2;
        return 1;
    }

    /**
     * 用户细分
     */
    private String segmentUser(int rScore, int fScore, int mScore) {
        // 重要价值客户
        if (rScore >= 4 && fScore >= 4 && mScore >= 4) {
            return "重要价值客户";
        }
        // 重要发展客户
        if (rScore >= 4 && fScore <= 2 && mScore >= 4) {
            return "重要发展客户";
        }
        // 重要保持客户
        if (rScore <= 2 && fScore >= 4 && mScore >= 4) {
            return "重要保持客户";
        }
        // 重要挽留客户
        if (rScore <= 2 && fScore <= 2 && mScore >= 4) {
            return "重要挽留客户";
        }
        // 一般价值客户
        if (rScore >= 4 && fScore >= 4 && mScore <= 2) {
            return "一般价值客户";
        }
        // 一般发展客户
        if (rScore >= 4 && fScore <= 2 && mScore <= 2) {
            return "一般发展客户";
        }
        // 一般保持客户
        if (rScore <= 2 && fScore >= 4 && mScore <= 2) {
            return "一般保持客户";
        }
        // 一般挽留客户
        return "一般挽留客户";
    }

    /**
     * 更新用户画像
     */
    public void updateUserProfile(Long userId) {
        // 更新兴趣标签
        String interestTags = extractUserInterestTags(userId);
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setInterestTags(interestTags);
            user.setLastActiveTime(new Date());
            userRepository.save(user);
        }

        // 更新RFM模型
        calculateUserRFM(userId);
    }

    /**
     * 批量更新用户画像
     */
    public void batchUpdateUserProfiles() {
        List<User> users = userRepository.findActiveUsersOrderByLastActive();
        for (User user : users) {
            try {
                updateUserProfile(user.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取用户兴趣标签列表
     */
    public List<String> getUserInterestTags(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getInterestTags() == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(user.getInterestTags().split(","));
    }

    /**
     * 获取用户画像概览
     */
    public Map<String, Object> getUserProfileOverview(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> profile = new HashMap<>();
        profile.put("userId", user.getId());
        profile.put("username", user.getUsername());
        profile.put("gender", user.getGender());
        profile.put("age", user.getAge());
        profile.put("region", user.getRegion());
        profile.put("occupation", user.getOccupation());
        profile.put("incomeLevel", user.getIncomeLevel());
        profile.put("interestTags", getUserInterestTags(userId));
        profile.put("recency", user.getRecency());
        profile.put("frequency", user.getFrequency());
        profile.put("monetary", user.getMonetary());
        profile.put("rfmScore", user.getRfmScore());
        profile.put("userSegment", user.getUserSegment());

        return profile;
    }
}
