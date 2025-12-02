package com.ecommerce.controller;

import com.ecommerce.entity.UserSegment;
import com.ecommerce.service.UserSegmentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-segmentation")
public class UserSegmentationController {

    @Autowired
    private UserSegmentationService userSegmentationService;

    /**
     * 基于RFM模型进行用户分群
     * @param recencyThreshold 最近购买阈值（天）
     * @param frequencyThreshold 购买频率阈值（次）
     * @param monetaryThreshold 购买金额阈值（元）
     * @return 用户分群列表
     */
    @PostMapping("/rfm-segmentation")
    public ResponseEntity<List<UserSegment>> segmentUsersByRFM(
            @RequestParam Integer recencyThreshold,
            @RequestParam Integer frequencyThreshold,
            @RequestParam Double monetaryThreshold) {
        List<UserSegment> segments = userSegmentationService.segmentUsersByRFM(
                recencyThreshold, frequencyThreshold, monetaryThreshold);
        return ResponseEntity.ok(segments);
    }

    /**
     * 基于用户生命周期进行用户分群
     * @param newUserDays 新客天数阈值（天）
     * @param activeUserDays 活跃用户天数阈值（天）
     * @param silentUserDays 沉默用户天数阈值（天）
     * @return 用户分群列表
     */
    @PostMapping("/lifecycle-segmentation")
    public ResponseEntity<List<UserSegment>> segmentUsersByLifecycle(
            @RequestParam Integer newUserDays,
            @RequestParam Integer activeUserDays,
            @RequestParam Integer silentUserDays) {
        List<UserSegment> segments = userSegmentationService.segmentUsersByLifecycle(
                newUserDays, activeUserDays, silentUserDays);
        return ResponseEntity.ok(segments);
    }

    /**
     * 创建自定义用户分群
     * @param segmentName 分群名称
     * @param userIds 用户ID列表
     * @param createdBy 创建者
     * @return 用户分群列表
     */
    @PostMapping("/custom-segmentation")
    public ResponseEntity<List<UserSegment>> createCustomUserSegment(
            @RequestParam String segmentName,
            @RequestParam List<Long> userIds,
            @RequestParam String createdBy) {
        List<UserSegment> segments = userSegmentationService.createCustomUserSegment(
                segmentName, userIds, createdBy);
        return ResponseEntity.ok(segments);
    }

    /**
     * 根据用户ID查询用户分群信息
     * @param userId 用户ID
     * @return 用户分群列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserSegment>> getUserSegmentsByUserId(@PathVariable Long userId) {
        List<UserSegment> segments = userSegmentationService.getUserSegmentsByUserId(userId);
        return ResponseEntity.ok(segments);
    }

    /**
     * 根据分群名称查询用户分群信息
     * @param segmentName 分群名称
     * @return 用户分群列表
     */
    @GetMapping("/segment/{segmentName}")
    public ResponseEntity<List<UserSegment>> getUserSegmentsBySegmentName(@PathVariable String segmentName) {
        List<UserSegment> segments = userSegmentationService.getUserSegmentsBySegmentName(segmentName);
        return ResponseEntity.ok(segments);
    }

    /**
     * 根据生命周期阶段查询用户分群信息
     * @param lifecycleStage 生命周期阶段
     * @return 用户分群列表
     */
    @GetMapping("/lifecycle/{lifecycleStage}")
    public ResponseEntity<List<UserSegment>> getUserSegmentsByLifecycleStage(@PathVariable String lifecycleStage) {
        List<UserSegment> segments = userSegmentationService.getUserSegmentsByLifecycleStage(lifecycleStage);
        return ResponseEntity.ok(segments);
    }

    /**
     * 更新用户分群信息
     * @param segmentId 分群ID
     * @param userSegment 用户分群信息
     * @return 更新后的用户分群信息
     */
    @PutMapping("/segments/{segmentId}")
    public ResponseEntity<UserSegment> updateUserSegment(@PathVariable Long segmentId, @RequestBody UserSegment userSegment) {
        userSegment.setId(segmentId);
        UserSegment updatedSegment = userSegmentationService.updateUserSegment(userSegment);
        return ResponseEntity.ok(updatedSegment);
    }

    /**
     * 删除用户分群信息
     * @param segmentId 分群ID
     * @return 响应状态
     */
    @DeleteMapping("/segments/{segmentId}")
    public ResponseEntity<Void> deleteUserSegment(@PathVariable Long segmentId) {
        userSegmentationService.deleteUserSegment(segmentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 统计各分群的用户数量
     * @return 分群统计列表
     */
    @GetMapping("/stats/by-segment")
    public ResponseEntity<List<Object[]>> countUsersBySegment() {
        List<Object[]> stats = userSegmentationService.countUsersBySegment();
        return ResponseEntity.ok(stats);
    }

    /**
     * 统计各生命周期阶段的用户数量
     * @return 生命周期统计列表
     */
    @GetMapping("/stats/by-lifecycle")
    public ResponseEntity<List<Object[]>> countUsersByLifecycleStage() {
        List<Object[]> stats = userSegmentationService.countUsersByLifecycleStage();
        return ResponseEntity.ok(stats);
    }
}
