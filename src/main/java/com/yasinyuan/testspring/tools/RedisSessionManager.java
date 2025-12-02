package com.yasinyuan.testspring.tools;

import com.alibaba.fastjson.JSON;
import com.yasinyuan.testspring.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Redis会话管理工具类
 * @author yasinyuan
 * @date 2025-05-02
 */
@Component
public class RedisSessionManager {

    private static final String SESSION_PREFIX = "user:session:";
    
    // 会话超时时间：30分钟
    private static final int SESSION_TIMEOUT = 30;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 创建新会话
     * @param userId 用户ID
     * @param ip IP地址
     * @param device 设备信息
     * @return 会话ID
     */
    public String createSession(Long userId, String ip, String device) {
        String sessionId = UUID.randomUUID().toString().replaceAll("-", "");
        UserSession session = new UserSession();
        session.setSessionId(sessionId);
        session.setUserId(userId);
        session.setStartTime(new Date());
        session.setLastActiveTime(new Date());
        session.setIp(ip);
        session.setDevice(device);
        session.setStatus("active");

        redisTemplate.opsForValue().set(SESSION_PREFIX + sessionId, JSON.toJSONString(session), SESSION_TIMEOUT, TimeUnit.MINUTES);
        return sessionId;
    }

    /**
     * 获取会话信息
     * @param sessionId 会话ID
     * @return 会话信息
     */
    public UserSession getSession(String sessionId) {
        String sessionJson = redisTemplate.opsForValue().get(SESSION_PREFIX + sessionId);
        if (sessionJson == null) {
            return null;
        }
        return JSON.parseObject(sessionJson, UserSession.class);
    }

    /**
     * 更新会话最后活跃时间
     * @param sessionId 会话ID
     */
    public void updateSessionActiveTime(String sessionId) {
        UserSession session = getSession(sessionId);
        if (session != null) {
            session.setLastActiveTime(new Date());
            redisTemplate.opsForValue().set(SESSION_PREFIX + sessionId, JSON.toJSONString(session), SESSION_TIMEOUT, TimeUnit.MINUTES);
        }
    }

    /**
     * 添加页面路径到会话
     * @param sessionId 会话ID
     * @param pageUrl 页面URL
     */
    public void addPagePath(String sessionId, String pageUrl) {
        UserSession session = getSession(sessionId);
        if (session != null) {
            session.getPagePath().add(pageUrl);
            redisTemplate.opsForValue().set(SESSION_PREFIX + sessionId, JSON.toJSONString(session), SESSION_TIMEOUT, TimeUnit.MINUTES);
        }
    }

    /**
     * 使会话过期
     * @param sessionId 会话ID
     */
    public void expireSession(String sessionId) {
        UserSession session = getSession(sessionId);
        if (session != null) {
            session.setStatus("expired");
            redisTemplate.opsForValue().set(SESSION_PREFIX + sessionId, JSON.toJSONString(session), 0, TimeUnit.MINUTES);
        }
    }

    /**
     * 删除会话
     * @param sessionId 会话ID
     */
    public void deleteSession(String sessionId) {
        redisTemplate.delete(SESSION_PREFIX + sessionId);
    }
}
