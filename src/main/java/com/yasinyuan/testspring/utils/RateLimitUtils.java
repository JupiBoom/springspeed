package com.yasinyuan.testspring.utils;

import com.yasinyuan.testspring.constant.Constants;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 防刷控制工具类
 * @author yinyuan
 * @since 2024-01-01
 */
@Component
public class RateLimitUtils {
    @Resource
    private RedisUtils redisUtils;

    /**
     * 检查请求是否超过限制
     */
    public boolean isRateLimited(String key) {
        String rateLimitKey = Constants.RATE_LIMIT_CACHE_PREFIX + key;
        Long count = redisUtils.increment(rateLimitKey, 1);
        if (count == 1) {
            // 设置过期时间
            redisUtils.expire(rateLimitKey, Constants.RATE_LIMIT_WINDOW, TimeUnit.SECONDS);
        }
        return count > Constants.RATE_LIMIT_MAX_REQUESTS;
    }

    /**
     * 检查请求是否超过限制
     */
    public boolean isRateLimited(String key, int maxRequests, int windowSeconds) {
        String rateLimitKey = Constants.RATE_LIMIT_CACHE_PREFIX + key;
        Long count = redisUtils.increment(rateLimitKey, 1);
        if (count == 1) {
            // 设置过期时间
            redisUtils.expire(rateLimitKey, windowSeconds, TimeUnit.SECONDS);
        }
        return count > maxRequests;
    }

    /**
     * 重置防刷控制计数
     */
    public void resetRateLimit(String key) {
        String rateLimitKey = Constants.RATE_LIMIT_CACHE_PREFIX + key;
        redisUtils.delete(rateLimitKey);
    }
}