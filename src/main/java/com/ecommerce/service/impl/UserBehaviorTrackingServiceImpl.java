package com.ecommerce.service.impl;

import com.ecommerce.entity.UserBehaviorLog;
import com.ecommerce.repository.UserBehaviorLogRepository;
import com.ecommerce.service.UserBehaviorTrackingService;
import com.ecommerce.util.ElasticsearchUtil;
import com.ecommerce.util.RedisSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserBehaviorTrackingServiceImpl implements UserBehaviorTrackingService {

    @Autowired
    private UserBehaviorLogRepository userBehaviorLogRepository;

    @Autowired
    private RedisSessionManager redisSessionManager;

    @Autowired
    private ElasticsearchUtil elasticsearchUtil;

    @Override
    public UserBehaviorLog trackUserBehavior(Long userId, String sessionId, String behaviorType, 
                                                String pageUrl, Long productId, Long categoryId, 
                                                String referrerUrl, String ipAddress, String userAgent) {
        
        // 创建用户行为日志
        UserBehaviorLog log = new UserBehaviorLog();
        log.setUserId(userId);
        log.setSessionId(sessionId);
        log.setBehaviorType(behaviorType);
        log.setPageUrl(pageUrl);
        log.setProductId(productId);
        log.setCategoryId(categoryId);
        log.setReferrerUrl(referrerUrl);
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        log.setCreatedTime(new Date());
        
        // 保存到MySQL
        UserBehaviorLog savedLog = userBehaviorLogRepository.save(log);
        
        // 保存到Elasticsearch（异步处理）
        try {
            elasticsearchUtil.saveUserBehaviorLog(savedLog);
        } catch (Exception e) {
            // 记录日志错误，但不影响主流程
            e.printStackTrace();
        }
        
        // 更新Redis会话
        redisSessionManager.createOrUpdateSession(sessionId, userId);
        
        return savedLog;
    }

    @Override
    public List<UserBehaviorLog> getUserBehaviorLogsByUserId(Long userId, Date startDate, Date endDate) {
        return userBehaviorLogRepository.findByUserIdAndCreatedTimeBetween(userId, startDate, endDate);
    }

    @Override
    public List<UserBehaviorLog> getUserBehaviorPathBySessionId(String sessionId) {
        return userBehaviorLogRepository.findBySessionIdOrderByCreatedTimeAsc(sessionId);
    }

    @Override
    public Long countBehaviorByTypeAndTimeRange(String behaviorType, Date startDate, Date endDate) {
        return userBehaviorLogRepository.countByBehaviorTypeAndTimeRange(behaviorType, startDate, endDate);
    }

    @Override
    public Long countActiveUsersByTimeRange(Date startDate, Date endDate) {
        return userBehaviorLogRepository.countActiveUsersByTimeRange(startDate, endDate);
    }

    @Override
    public Long countNewUsersByTimeRange(Date startDate, Date endDate) {
        return userBehaviorLogRepository.countNewUsersByTimeRange(startDate, endDate);
    }
}
