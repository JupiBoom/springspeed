package com.ecommerce.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisSessionManager {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${analytics.session.timeout}")
    private long sessionTimeout; // 单位：毫秒

    /**
     * 创建或更新用户会话
     * @param sessionId 会话ID
     * @param userId 用户ID
     */
    public void createOrUpdateSession(String sessionId, Long userId) {
        String key = "session:" + sessionId;
        redisTemplate.opsForValue().set(key, userId, sessionTimeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 检查会话是否存在
     * @param sessionId 会话ID
     * @return 会话是否存在
     */
    public boolean isSessionExists(String sessionId) {
        String key = "session:" + sessionId;
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取会话对应的用户ID
     * @param sessionId 会话ID
     * @return 用户ID
     */
    public Long getUserIdBySessionId(String sessionId) {
        String key = "session:" + sessionId;
        return (Long) redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除会话
     * @param sessionId 会话ID
     */
    public void deleteSession(String sessionId) {
        String key = "session:" + sessionId;
        redisTemplate.delete(key);
    }

    /**
     * 延长会话超时时间
     * @param sessionId 会话ID
     */
    public void extendSessionTimeout(String sessionId) {
        String key = "session:" + sessionId;
        if (redisTemplate.hasKey(key)) {
            redisTemplate.expire(key, sessionTimeout, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 获取会话剩余超时时间
     * @param sessionId 会话ID
     * @return 剩余超时时间（毫秒），如果会话不存在则返回-2
     */
    public long getSessionRemainingTime(String sessionId) {
        String key = "session:" + sessionId;
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }
}
