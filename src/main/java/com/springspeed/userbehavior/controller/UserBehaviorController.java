package com.springspeed.userbehavior.controller;

import com.springspeed.userbehavior.entity.UserBehavior;
import com.springspeed.userbehavior.service.UserBehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户行为控制器
 * 处理用户行为相关的HTTP请求
 */
@RestController
@RequestMapping("/api/user-behavior")
public class UserBehaviorController {

    @Autowired
    private UserBehaviorService userBehaviorService;

    /**
     * 记录用户行为
     * @param userBehavior 用户行为实体
     * @return 记录结果
     */
    @PostMapping("/record")
    public ResponseEntity<String> recordUserBehavior(@RequestBody UserBehavior userBehavior) {
        try {
            userBehaviorService.recordUserBehavior(userBehavior);
            return ResponseEntity.ok("用户行为记录成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("用户行为记录失败：" + e.getMessage());
        }
    }

    /**
     * 批量记录用户行为
     * @param userBehaviors 用户行为实体列表
     * @return 记录结果
     */
    @PostMapping("/batch-record")
    public ResponseEntity<String> batchRecordUserBehaviors(@RequestBody List<UserBehavior> userBehaviors) {
        try {
            userBehaviorService.batchRecordUserBehaviors(userBehaviors);
            return ResponseEntity.ok("用户行为批量记录成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("用户行为批量记录失败：" + e.getMessage());
        }
    }

    /**
     * 根据用户ID查询用户行为
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 用户行为分页列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<UserBehavior>> getUserBehaviorsByUserId(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "behaviorTime"));
            Page<UserBehavior> userBehaviors = userBehaviorService.getUserBehaviorsByUserId(userId, pageable);
            return ResponseEntity.ok(userBehaviors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 根据行为类型查询用户行为
     * @param behaviorType 行为类型
     * @param page 页码
     * @param size 每页大小
     * @return 用户行为分页列表
     */
    @GetMapping("/type/{behaviorType}")
    public ResponseEntity<Page<UserBehavior>> getUserBehaviorsByBehaviorType(
            @PathVariable String behaviorType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "behaviorTime"));
            Page<UserBehavior> userBehaviors = userBehaviorService.getUserBehaviorsByBehaviorType(behaviorType, pageable);
            return ResponseEntity.ok(userBehaviors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 根据会话ID查询用户行为
     * @param sessionId 会话ID
     * @param page 页码
     * @param size 每页大小
     * @return 用户行为分页列表
     */
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<Page<UserBehavior>> getUserBehaviorsBySessionId(
            @PathVariable String sessionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "behaviorTime"));
            Page<UserBehavior> userBehaviors = userBehaviorService.getUserBehaviorsBySessionId(sessionId, pageable);
            return ResponseEntity.ok(userBehaviors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 根据时间范围查询用户行为
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页码
     * @param size 每页大小
     * @return 用户行为分页列表
     */
    @GetMapping("/time-range")
    public ResponseEntity<Page<UserBehavior>> getUserBehaviorsByTimeRange(
            @RequestParam Date startTime,
            @RequestParam Date endTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "behaviorTime"));
            Page<UserBehavior> userBehaviors = userBehaviorService.getUserBehaviorsByTimeRange(startTime, endTime, pageable);
            return ResponseEntity.ok(userBehaviors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 查询用户行为统计数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数据
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getUserBehaviorStatistics(
            @RequestParam(required = false) Date startTime,
            @RequestParam(required = false) Date endTime) {
        try {
            Map<String, Object> statistics = userBehaviorService.getUserBehaviorStatistics(startTime, endTime);
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 查询行为类型统计数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 行为类型统计数据
     */
    @GetMapping("/type-statistics")
    public ResponseEntity<List<Map<String, Object>>> getBehaviorTypeStatistics(
            @RequestParam(required = false) Date startTime,
            @RequestParam(required = false) Date endTime) {
        try {
            List<Map<String, Object>> typeStatistics = userBehaviorService.getBehaviorTypeStatistics(startTime, endTime);
            return ResponseEntity.ok(typeStatistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 查询用户路径分析数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param minCount 最小路径数量
     * @return 用户路径分析数据
     */
    @GetMapping("/path-analysis")
    public ResponseEntity<List<Map<String, Object>>> getUserPathAnalysis(
            @RequestParam(required = false) Date startTime,
            @RequestParam(required = false) Date endTime,
            @RequestParam(defaultValue = "1") int minCount) {
        try {
            List<Map<String, Object>> pathAnalysis = userBehaviorService.getUserPathAnalysis(startTime, endTime, minCount);
            return ResponseEntity.ok(pathAnalysis);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 分析自定义转化漏斗
     * @param funnelSteps 漏斗步骤列表
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 漏斗分析结果
     */
    @PostMapping("/funnel-analysis")
    public ResponseEntity<Map<String, Object>> analyzeFunnel(
            @RequestBody List<String> funnelSteps,
            @RequestParam(required = false) Date startTime,
            @RequestParam(required = false) Date endTime) {
        try {
            Map<String, Object> funnelAnalysis = userBehaviorService.analyzeFunnel(funnelSteps, startTime, endTime);
            return ResponseEntity.ok(funnelAnalysis);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 查询用户行为日报
     * @param reportDate 报告日期
     * @return 用户行为日报
     */
    @GetMapping("/daily-report")
    public ResponseEntity<Map<String, Object>> getUserBehaviorDailyReport(@RequestParam Date reportDate) {
        try {
            Map<String, Object> dailyReport = userBehaviorService.getUserBehaviorDailyReport(reportDate);
            return ResponseEntity.ok(dailyReport);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 查询用户行为周报
     * @param startDate 周报开始日期
     * @param endDate 周报结束日期
     * @return 用户行为周报
     */
    @GetMapping("/weekly-report")
    public ResponseEntity<Map<String, Object>> getUserBehaviorWeeklyReport(
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        try {
            Map<String, Object> weeklyReport = userBehaviorService.getUserBehaviorWeeklyReport(startDate, endDate);
            return ResponseEntity.ok(weeklyReport);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 查询用户行为月报
     * @param reportMonth 报告月份（格式：yyyy-MM）
     * @return 用户行为月报
     */
    @GetMapping("/monthly-report")
    public ResponseEntity<Map<String, Object>> getUserBehaviorMonthlyReport(@RequestParam String reportMonth) {
        try {
            Map<String, Object> monthlyReport = userBehaviorService.getUserBehaviorMonthlyReport(reportMonth);
            return ResponseEntity.ok(monthlyReport);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}