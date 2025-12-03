package com.yasinyuan.testspring.settlement.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 结算流水号生成器
 * 生成唯一的结算流水号，格式：YYYYMMDDHHmmss + 6位自增序列
 */
@Component
public class SettlementNoGenerator {
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    private static final String REDIS_KEY_PREFIX = "settlement:no:";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final int SEQUENCE_LENGTH = 6;
    
    /**
     * 生成结算流水号
     * @return 结算流水号
     */
    public String generateSettlementNo() {
        String datePart = LocalDateTime.now().format(DATE_FORMATTER);
        String redisKey = REDIS_KEY_PREFIX + datePart;
        
        // 自增序列，过期时间设置为24小时
        Long sequence = redisTemplate.opsForValue().increment(redisKey);
        if (sequence == 1) {
            redisTemplate.expire(redisKey, 24, TimeUnit.HOURS);
        }
        
        // 格式化序列，补零到6位
        String sequencePart = String.format("%0" + SEQUENCE_LENGTH + "d", sequence);
        
        return datePart + sequencePart;
    }
}