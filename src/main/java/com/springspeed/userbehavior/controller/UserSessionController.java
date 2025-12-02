package com.springspeed.userbehavior.controller;

import com.springspeed.userbehavior.entity.UserSession;
import com.springspeed.userbehavior.service.UserSessionService;
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
 * 用户会话控制器
 * 处理用户会话相关的HTTP请求
 */
@RestController
@RequestMapping("/api/user-session")
public class UserSessionController {

    @Autowired
    private UserSessionService userSessionService;

    /**
     * 创建用户会话
     * @param userSession 用户会话实体
     * @return 创建的用户会话
     */
    @PostMapping("/create")
    public ResponseEntity<UserSession> createUserSession(@RequestBody UserSession userSession) {
        try {
            UserSession createdSession = userSessionService.createUserSession(userSession);
            return ResponseEntity.ok(createdSession);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 更新用户会话
     * @param userSession 用户会话实体
     * @return 更新的用户会话
     */
    @PutMapping("/update")
    public ResponseEntity<UserSession> updateUserSession(@RequestBody UserSession userSession) {
        try {
            UserSession updatedSession = userSessionService.updateUserSession(userSession);
            return ResponseEntity.ok(updatedSession);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 根据会话ID查询用户会话
     * @param sessionId 会话ID
     * @return 用户会话
     */
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<UserSession> getUserSessionBySessionId(@PathVariable String sessionId) {
        try {
            UserSession userSession = userSessionService.getUserSessionBySessionId(sessionId);
            return ResponseEntity.ok(userSession);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 根据用户ID查询用户会话
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 用户会话分页列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<UserSession>> getUserSessionsByUserId(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "startTime"));
            Page<UserSession> userSessions = userSessionService.getUserSessionsByUserId(userId, pageable);
            return ResponseEntity.ok(userSessions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 查询活跃的用户会话
     * @param page 页码
     * @param size 每页大小
     * @return 活跃用户会话分页列表
     */
    @GetMapping("/active")
    public ResponseEntity<Page<UserSession>> getActiveUserSessions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lastActivityTime"));
            Page<UserSession> activeSessions = userSessionService.getActiveUserSessions(pageable);
            return ResponseEntity.ok(activeSessions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 根据时间范围查询用户会话
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页码
     * @param size 每页大小
     * @return 用户会话分页列表
     */
    @GetMapping("/time-range")
    public ResponseEntity<Page<UserSession>> getUserSessionsByTimeRange(
            @RequestParam Date startTime,
            @RequestParam Date endTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "startTime"));
            Page<UserSession> userSessions = userSessionService.getUserSessionsByTimeRange(startTime, endTime, pageable);
            return ResponseEntity.ok(userSessions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 结束用户会话
     * @param sessionId 会话ID
     * @return 结束结果
     */
    @PostMapping("/end/{sessionId}")
    public ResponseEntity<String> endUserSession(@PathVariable String sessionId) {
        try {
            userSessionService.endUserSession(sessionId);
            return ResponseEntity.ok("用户会话结束成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("用户会话结束失败：" + e.getMessage());
        }
    }

    /**
     * 批量结束用户会话
     * @param sessionIds 会话ID列表
     * @return 结束结果
     */
    @PostMapping("/batch-end")
    public ResponseEntity<String> batchEndUserSessions(@RequestBody List<String> sessionIds) {
        try {
            userSessionService.batchEndUserSessions(sessionIds);
            return ResponseEntity.ok("用户会话批量结束成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("用户会话批量结束失败：" + e.getMessage());
        }
    }

    /**
     * 清理超时的用户会话
     * @return 清理结果
     */
    @PostMapping("/cleanup-timeout")
    public ResponseEntity<String> cleanupTimeoutUserSessions() {
        try {
            int cleanupCount = userSessionService.cleanupTimeoutUserSessions();
            return ResponseEntity.ok("清理超时用户会话成功，共清理 " + cleanupCount + " 个会话");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("清理超时用户会话失败：" + e.getMessage());
        }
    }

    /**
     * 查询用户会话统计数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数据
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getUserSessionStatistics(
            @RequestParam(required = false) Date startTime,
            @RequestParam(required = false) Date endTime) {
        try {
            Map<String, Object> statistics = userSessionService.getUserSessionStatistics(startTime, endTime);
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 查询会话来源渠道统计数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 来源渠道统计数据
     */
    @GetMapping("/source-statistics")
    public ResponseEntity<List<Map<String, Object>>> getSessionSourceStatistics(
            @RequestParam(required = false) Date startTime,
            @RequestParam(required = false) Date endTime) {
        try {
            List<Map<String, Object>> sourceStatistics = userSessionService.getSessionSourceStatistics(startTime, endTime);
            return ResponseEntity.ok(sourceStatistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 查询会话设备类型统计数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 设备类型统计数据
     */
    @GetMapping("/device-statistics")
    public ResponseEntity<List<Map<String, Object>>> getSessionDeviceStatistics(
            @RequestParam(required = false) Date startTime,
            @RequestParam(required = false) Date endTime) {
        try {
            List<Map<String, Object>> deviceStatistics = userSessionService.getSessionDeviceStatistics(startTime, endTime);
            return ResponseEntity.ok(deviceStatistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 查询会话时长分布统计数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 时长分布统计数据
     */
    @GetMapping("/duration-distribution")
    public ResponseEntity<List<Map<String, Object>>> getSessionDurationDistribution(
            @RequestParam(required = false) Date startTime,
            @RequestParam(required = false) Date endTime) {
        try {
            List<Map<String, Object>> durationDistribution = userSessionService.getSessionDurationDistribution(startTime, endTime);
            return ResponseEntity.ok(durationDistribution);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 查询会话PV/UV统计数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return PV/UV统计数据
     */
    @GetMapping("/pv-uv-statistics")
    public ResponseEntity<Map<String, Object>> getSessionPVUVStatistics(
            @RequestParam(required = false) Date startTime,
            @RequestParam(required = false) Date endTime) {
        try {
            Map<String, Object> pvuvStatistics = userSessionService.getSessionPVUVStatistics(startTime, endTime);
            return ResponseEntity.ok(pvuvStatistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}