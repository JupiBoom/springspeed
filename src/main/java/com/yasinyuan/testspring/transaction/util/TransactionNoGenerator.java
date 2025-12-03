package com.yasinyuan.testspring.transaction.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 交易流水号生成器
 * 生成规则: 时间戳(14位) + 自增序列(6位)
 * @author yinyuan
 */
@Component
public class TransactionNoGenerator {
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    private static final String KEY_PREFIX = "transaction:seq:";
    private static final int SEQ_LENGTH = 6;
    private static final long EXPIRE_SECONDS = 86400; // 24小时过期
    
    public String generate() {
        String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String key = KEY_PREFIX + dateStr;
        
        // 原子递增
        Long seq = redisTemplate.opsForValue().increment(key, 1);
        
        // 设置过期时间（仅在第一次设置时）
        if (seq == 1) {
            redisTemplate.expire(key, EXPIRE_SECONDS, TimeUnit.SECONDS);
        }
        
        // 补零
        String seqStr = String.format("%0" + SEQ_LENGTH + "d", seq);
        
        return dateStr + seqStr;
    }
}
