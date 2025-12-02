package com.springspeed.userbehavior.controller;

import com.springspeed.userbehavior.entity.UserSegment;
import com.springspeed.userbehavior.entity.UserSegmentMember;
import com.springspeed.userbehavior.service.UserSegmentService;
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
 * 用户分群控制器
 * 处理用户分群相关的HTTP请求
 */
@RestController
@RequestMapping("/api/user-segment")
public class UserSegmentController {

    @Autowired
    private UserSegmentService userSegmentService;

    /**
     * 创建用户分群
     * @param userSegment 用户分群实体
     * @return 创建的用户分群
     */
    @PostMapping("/create")
    public ResponseEntity<UserSegment> createUserSegment(@RequestBody UserSegment userSegment) {
        try {
            UserSegment createdSegment = userSegmentService.createUserSegment(userSegment);
            return ResponseEntity.ok(createdSegment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 更新用户分群
     * @param userSegment 用户分群实体
     * @return 更新的用户分群
     */
    @PutMapping("/update")
    public ResponseEntity<UserSegment> updateUserSegment(@RequestBody UserSegment userSegment) {
        try {
            UserSegment updatedSegment = userSegmentService.updateUserSegment(userSegment);
            return ResponseEntity.ok(updatedSegment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 根据分群ID查询用户分群
     * @param segmentId 分群ID
     * @return 用户分群
     */
    @GetMapping("/segment/{segmentId}")
    public ResponseEntity<UserSegment> getUserSegmentBySegmentId(@PathVariable String segmentId) {
        try {
            UserSegment userSegment = userSegmentService.getUserSegmentBySegmentId(segmentId).orElse(null);
            return ResponseEntity.ok(userSegment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 根据分群名称查询用户分群
     * @param segmentName 分群名称
     * @return 用户分群列表
     */
    @GetMapping("/name/{segmentName}")
    public ResponseEntity<List<UserSegment>> getUserSegmentsBySegmentName(@PathVariable String segmentName) {
        try {
            List<UserSegment> userSegments = userSegmentService.getUserSegmentsBySegmentName(segmentName);
            return ResponseEntity.ok(userSegments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 根据分群类型查询用户分群
     * @param segmentType 分群类型
     * @return 用户分群列表
     */
    @GetMapping("/type/{segmentType}")
    public ResponseEntity<List<UserSegment>> getUserSegmentsBySegmentType(@PathVariable String segmentType) {
        try {
            List<UserSegment> userSegments = userSegmentService.getUserSegmentsBySegmentType(segmentType);
            return ResponseEntity.ok(userSegments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 查询活跃的用户分群
     * @return 活跃用户分群列表
     */
    @GetMapping("/active")
    public ResponseEntity<List<UserSegment>> getActiveUserSegments() {
        try {
            List<UserSegment> activeSegments = userSegmentService.getActiveUserSegments();
            return ResponseEntity.ok(activeSegments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 删除用户分群
     * @param segmentId 分群ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{segmentId}")
    public ResponseEntity<String> deleteUserSegment(@PathVariable String segmentId) {
        try {
            userSegmentService.deleteUserSegment(segmentId);
            return ResponseEntity.ok("用户分群删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("用户分群删除失败：" + e.getMessage());
        }
    }

    /**
     * 批量删除用户分群
     * @param segmentIds 分群ID列表
     * @return 删除结果
     */
    @DeleteMapping("/batch-delete")
    public ResponseEntity<String> batchDeleteUserSegments(@RequestBody List<String> segmentIds) {
        try {
            userSegmentService.batchDeleteUserSegments(segmentIds);
            return ResponseEntity.ok("用户分群批量删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("用户分群批量删除失败：" + e.getMessage());
        }
    }

    /**
     * 根据条件查询用户分群
     * @param segmentName 分群名称（模糊查询）
     * @param segmentType 分群类型
     * @param status 分群状态
     * @param page 页码
     * @param size 每页大小
     * @return 用户分群分页列表
     */
    @GetMapping("/query")
    public ResponseEntity<Page<UserSegment>> getUserSegmentsByCondition(
            @RequestParam(required = false) String segmentName,
            @RequestParam(required = false) String segmentType,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
            Page<UserSegment> userSegments = userSegmentService.getUserSegmentsByCondition(segmentName, segmentType, status, pageable);
            return ResponseEntity.ok(userSegments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 查询用户分群统计数据
     * @return 统计数据
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getUserSegmentStatistics() {
        try {
            Map<String, Object> statistics = userSegmentService.getUserSegmentStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 查询分群类型统计数据
     * @return 分群类型统计数据
     */
    @GetMapping("/type-statistics")
    public ResponseEntity<List<Map<String, Object>>> getSegmentTypeStatistics() {
        try {
            List<Map<String, Object>> typeStatistics = userSegmentService.getSegmentTypeStatistics();
            return ResponseEntity.ok(typeStatistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 添加用户到分群
     * @param segmentId 分群ID
     * @param userId 用户ID
     * @return 用户分群成员
     */
    @PostMapping("/add-user/{segmentId}/{userId}")
    public ResponseEntity<UserSegmentMember> addUserToSegment(
            @PathVariable String segmentId,
            @PathVariable String userId) {
        try {
            UserSegmentMember member = userSegmentService.addUserToSegment(segmentId, userId);
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 批量添加用户到分群
     * @param segmentId 分群ID
     * @param userIds 用户ID列表
     * @return 添加成功的用户数量
     */
    @PostMapping("/batch-add-users/{segmentId}")
    public ResponseEntity<Integer> batchAddUsersToSegment(
            @PathVariable String segmentId,
            @RequestBody List<String> userIds) {
        try {
            int addCount = userSegmentService.batchAddUsersToSegment(segmentId, userIds);
            return ResponseEntity.ok(addCount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 从分群中移除用户
     * @param segmentId 分群ID
     * @param userId 用户ID
     * @return 移除结果
     */
    @PostMapping("/remove-user/{segmentId}/{userId}")
    public ResponseEntity<String> removeUserFromSegment(
            @PathVariable String segmentId,
            @PathVariable String userId) {
        try {
            userSegmentService.removeUserFromSegment(segmentId, userId);
            return ResponseEntity.ok("用户从分群中移除成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("用户从分群中移除失败：" + e.getMessage());
        }
    }

    /**
     * 批量从分群中移除用户
     * @param segmentId 分群ID
     * @param userIds 用户ID列表
     * @return 移除成功的用户数量
     */
    @PostMapping("/batch-remove-users/{segmentId}")
    public ResponseEntity<Integer> batchRemoveUsersFromSegment(
            @PathVariable String segmentId,
            @RequestBody List<String> userIds) {
        try {
            int removeCount = userSegmentService.batchRemoveUsersFromSegment(segmentId, userIds);
            return ResponseEntity.ok(removeCount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 根据分群ID查询分群成员
     * @param segmentId 分群ID
     * @param page 页码
     * @param size 每页大小
     * @return 分群成员分页列表
     */
    @GetMapping("/members/{segmentId}")
    public ResponseEntity<Page<UserSegmentMember>> getSegmentMembersBySegmentId(
            @PathVariable String segmentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "joinTime"));
            Page<UserSegmentMember> members = userSegmentService.getSegmentMembersBySegmentId(segmentId, pageable);
            return ResponseEntity.ok(members);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 根据用户ID查询用户所属的分群
     * @param userId 用户ID
     * @return 用户所属的分群列表
     */
    @GetMapping("/user-segments/{userId}")
    public ResponseEntity<List<UserSegment>> getUserSegmentsByUserId(@PathVariable String userId) {
        try {
            List<UserSegment> userSegments = userSegmentService.getUserSegmentsByUserId(userId);
            return ResponseEntity.ok(userSegments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 检查用户是否属于某个分群
     * @param segmentId 分群ID
     * @param userId 用户ID
     * @return true：属于，false：不属于
     */
    @GetMapping("/check-user/{segmentId}/{userId}")
    public ResponseEntity<Boolean> checkUserInSegment(
            @PathVariable String segmentId,
            @PathVariable String userId) {
        try {
            boolean isInSegment = userSegmentService.checkUserInSegment(segmentId, userId);
            return ResponseEntity.ok(isInSegment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 根据RFM模型进行用户分群
     * @param recencyDays 最近购买天数阈值
     * @param frequencyThreshold 购买频率阈值
     * @param monetaryThreshold 购买金额阈值
     * @return RFM分群结果
     */
    @GetMapping("/rfm-segmentation")
    public ResponseEntity<Map<String, List<String>>> segmentUsersByRFM(
            @RequestParam(required = false) Integer recencyDays,
            @RequestParam(required = false) Integer frequencyThreshold,
            @RequestParam(required = false) Double monetaryThreshold) {
        try {
            Map<String, List<String>> rfmSegmentation = userSegmentService.segmentUsersByRFM(recencyDays, frequencyThreshold, monetaryThreshold);
            return ResponseEntity.ok(rfmSegmentation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 根据用户生命周期进行用户分群
     * @param newUserDays 新客天数阈值
     * @param activeUserDays 活跃用户天数阈值
     * @param churnUserDays 流失用户天数阈值
     * @return 生命周期分群结果
     */
    @GetMapping("/lifecycle-segmentation")
    public ResponseEntity<Map<String, List<String>>> segmentUsersByLifecycle(
            @RequestParam(required = false) Integer newUserDays,
            @RequestParam(required = false) Integer activeUserDays,
            @RequestParam(required = false) Integer churnUserDays) {
        try {
            Map<String, List<String>> lifecycleSegmentation = userSegmentService.segmentUsersByLifecycle(newUserDays, activeUserDays, churnUserDays);
            return ResponseEntity.ok(lifecycleSegmentation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 导出分群成员列表
     * @param segmentId 分群ID
     * @return 分群成员列表
     */
    @GetMapping("/export-members/{segmentId}")
    public ResponseEntity<List<Map<String, Object>>> exportSegmentMembers(@PathVariable String segmentId) {
        try {
            List<Map<String, Object>> exportMembers = userSegmentService.exportSegmentMembers(segmentId);
            return ResponseEntity.ok(exportMembers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}