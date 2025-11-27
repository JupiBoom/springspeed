package com.springspeed.controller;

import com.springspeed.model.User;
import com.springspeed.repository.UserRepository;
import com.springspeed.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * User Profile Controller
 * 用户画像控制器
 */
@RestController
@RequestMapping("/api/user-profile")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileService userProfileService;

    /**
     * 获取用户画像概览
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUserProfile(@PathVariable Long userId) {
        Map<String, Object> profile = userProfileService.getUserProfileOverview(userId);
        if (profile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profile);
    }

    /**
     * 获取用户兴趣标签
     */
    @GetMapping("/{userId}/interest-tags")
    public ResponseEntity<List<String>> getUserInterestTags(@PathVariable Long userId) {
        List<String> tags = userProfileService.getUserInterestTags(userId);
        return ResponseEntity.ok(tags);
    }

    /**
     * 更新用户画像
     */
    @PostMapping("/{userId}/update")
    public ResponseEntity<Void> updateUserProfile(@PathVariable Long userId) {
        userProfileService.updateUserProfile(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 获取用户列表（支持分页）
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<User> users = userRepository.findActiveUsersOrderByLastActive();
        // 简单分页实现
        int start = page * size;
        int end = Math.min(start + size, users.size());
        if (start >= users.size()) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(users.subList(start, end));
    }

    /**
     * 获取高价值用户列表
     */
    @GetMapping("/high-value-users")
    public ResponseEntity<List<User>> getHighValueUsers(
            @RequestParam(defaultValue = "20") int limit) {
        List<User> users = userRepository.findHighValueUsers(limit);
        return ResponseEntity.ok(users);
    }

    /**
     * 获取新用户列表
     */
    @GetMapping("/new-users")
    public ResponseEntity<List<User>> getNewUsers(
            @RequestParam(defaultValue = "30") int days,
            @RequestParam(defaultValue = "20") int limit) {
        List<User> users = userRepository.findNewUsers(days, limit);
        return ResponseEntity.ok(users);
    }

    /**
     * 批量更新用户画像
     */
    @PostMapping("/batch-update")
    public ResponseEntity<Void> batchUpdateUserProfiles() {
        new Thread(() -> {
            try {
                userProfileService.batchUpdateUserProfiles();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        return ResponseEntity.ok().build();
    }
}
