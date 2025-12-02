package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.dao.UserBehaviorRepository;
import com.yasinyuan.testspring.model.UserBehavior;
import com.yasinyuan.testspring.service.UserBehaviorService;
import com.yasinyuan.testspring.tools.RedisSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户行为服务实现类
 * @author yasinyuan
 * @date 2025-05-02
 */
@Service("userBehaviorService")
public class UserBehaviorServiceImpl implements UserBehaviorService {

    @Autowired
    private UserBehaviorRepository userBehaviorRepository;

    @Autowired
    private RedisSessionManager redisSessionManager;

    @Override
    public void saveBehavior(UserBehavior behavior) {
        if (behavior.getCreateTime() == null) {
            behavior.setCreateTime(new Date());
        }
        userBehaviorRepository.save(behavior);
    }

    @Override
    public void saveBehaviors(List<UserBehavior> behaviors) {
        behaviors.forEach(behavior -> {
            if (behavior.getCreateTime() == null) {
                behavior.setCreateTime(new Date());
            }
        });
        userBehaviorRepository.saveAll(behaviors);
    }

    @Override
    public void trackBehavior(Long userId, String sessionId, String behaviorType, String pageUrl, String pageTitle, String productId, String productName, String ip, String device, Map<String, Object> extInfo) {
        UserBehavior behavior = new UserBehavior();
        behavior.setUserId(userId);
        behavior.setSessionId(sessionId);
        behavior.setBehaviorType(behaviorType);
        behavior.setPageUrl(pageUrl);
        behavior.setPageTitle(pageTitle);
        behavior.setProductId(productId);
        behavior.setProductName(productName);
        behavior.setIp(ip);
        behavior.setDevice(device);
        behavior.setExtInfo(extInfo);
        behavior.setCreateTime(new Date());

        saveBehavior(behavior);

        // 更新会话最后活跃时间
        redisSessionManager.updateSessionActiveTime(sessionId);
        // 添加页面路径到会话
        redisSessionManager.addPagePath(sessionId, pageUrl);
    }

    @Override
    public Page<UserBehavior> findByUserId(Long userId, Pageable pageable) {
        return userBehaviorRepository.findByUserId(userId, pageable);
    }

    @Override
    public List<UserBehavior> findBySessionId(String sessionId) {
        return userBehaviorRepository.findBySessionId(sessionId);
    }

    @Override
    public Page<UserBehavior> findByBehaviorType(String behaviorType, Pageable pageable) {
        return userBehaviorRepository.findByBehaviorType(behaviorType, pageable);
    }

    @Override
    public Page<UserBehavior> findByCreateTimeBetween(Date startTime, Date endTime, Pageable pageable) {
        return userBehaviorRepository.findByCreateTimeBetween(startTime, endTime, pageable);
    }

    @Override
    public List<String> getUserPath(Long userId, Date startTime, Date endTime) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(0, 1000);
        Page<UserBehavior> behaviors = userBehaviorRepository.findByUserIdAndBehaviorType(userId, "browse", pageable);
        return behaviors.stream()
                .filter(behavior -> behavior.getCreateTime().after(startTime) && behavior.getCreateTime().before(endTime))
                .sorted(Comparator.comparing(UserBehavior::getCreateTime))
                .map(UserBehavior::getPageUrl)
                .collect(Collectors.toList());
    }
}
