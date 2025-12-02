package com.ecommerce.controller;

import com.ecommerce.entity.UserBehaviorLog;
import com.ecommerce.service.UserBehaviorTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/behavior-tracking")
public class UserBehaviorTrackingController {

    @Autowired
    private UserBehaviorTrackingService behaviorTrackingService;

    /**
     * 记录用户行为
     * @param behaviorLog 用户行为日志
     * @return 记录的行为日志
     */
    @PostMapping("/track")
    public ResponseEntity<UserBehaviorLog> trackUserBehavior(@RequestBody UserBehaviorLog behaviorLog) {
        UserBehaviorLog savedLog = behaviorTrackingService.trackUserBehavior(
                behaviorLog.getUserId(),
                behaviorLog.getSessionId(),
                behaviorLog.getBehaviorType(),
                behaviorLog.getPageUrl(),
                behaviorLog.getProductId(),
                behaviorLog.getCategoryId(),
                behaviorLog.getReferrerUrl(),
                behaviorLog.getIpAddress(),
                behaviorLog.getUserAgent());
        return ResponseEntity.ok(savedLog);
    }

    /**
     * 根据用户ID和时间范围查询行为日志
     * @param userId 用户ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 行为日志列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserBehaviorLog>> getUserBehaviorLogs(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {
        List<UserBehaviorLog> logs = behaviorTrackingService.getUserBehaviorLogsByUserId(userId, startDate, endDate);
        return ResponseEntity.ok(logs);
    }

    /**
     * 根据会话ID查询用户行为路径
     * @param sessionId 会话ID
     * @return 行为日志列表（按时间排序）
     */
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<UserBehaviorLog>> getUserBehaviorPath(@PathVariable String sessionId) {
        List<UserBehaviorLog> path = behaviorTrackingService.getUserBehaviorPathBySessionId(sessionId);
        return ResponseEntity.ok(path);
    }

    /**
     * 统计特定行为类型的次数
     * @param behaviorType 行为类型
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 行为次数
     */
    @GetMapping("/stats/behavior-count")
    public ResponseEntity<Long> countBehaviorByType(
            @RequestParam String behaviorType,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {
        Long count = behaviorTrackingService.countBehaviorByTypeAndTimeRange(behaviorType, startDate, endDate);
        return ResponseEntity.ok(count);
    }

    /**
     * 统计指定时间范围内的活跃用户数
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 活跃用户数
     */
    @GetMapping("/stats/active-users")
    public ResponseEntity<Long> countActiveUsers(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {
        Long count = behaviorTrackingService.countActiveUsersByTimeRange(startDate, endDate);
        return ResponseEntity.ok(count);
    }

    /**
     * 统计指定时间范围内的新用户数
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 新用户数
     */
    @GetMapping("/stats/new-users")
    public ResponseEntity<Long> countNewUsers(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {
        Long count = behaviorTrackingService.countNewUsersByTimeRange(startDate, endDate);
        return ResponseEntity.ok(count);
    }
}
