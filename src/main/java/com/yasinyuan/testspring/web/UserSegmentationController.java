package com.yasinyuan.testspring.web;

import com.yasinyuan.testspring.model.UserLifecycle;
import com.yasinyuan.testspring.model.UserRfm;
import com.yasinyuan.testspring.model.UserTag;
import com.yasinyuan.testspring.service.UserSegmentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/user-segment")
public class UserSegmentationController {

    @Autowired
    private UserSegmentationService userSegmentationService;

    // RFM模型相关接口
    @PostMapping("/rfm/calculate")
    public List<UserRfm> calculateRfmModel(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return userSegmentationService.calculateRfmModel(startDate, endDate);
    }

    @GetMapping("/rfm/user/{userId}")
    public UserRfm getUserRfm(@PathVariable String userId) {
        return userSegmentationService.getUserRfm(userId);
    }

    @GetMapping("/rfm/segment/{segment}")
    public List<UserRfm> getUsersByRfmSegment(@PathVariable String segment) {
        return userSegmentationService.getUsersByRfmSegment(segment);
    }

    @GetMapping("/rfm/count")
    public Map<String, Long> countUsersByRfmSegment() {
        return userSegmentationService.countUsersByRfmSegment();
    }

    // 用户生命周期相关接口
    @PostMapping("/lifecycle/calculate")
    public List<UserLifecycle> calculateUserLifecycle() {
        return userSegmentationService.calculateUserLifecycle();
    }

    @GetMapping("/lifecycle/user/{userId}")
    public UserLifecycle getUserLifecycle(@PathVariable String userId) {
        return userSegmentationService.getUserLifecycle(userId);
    }

    @GetMapping("/lifecycle/stage/{stage}")
    public List<UserLifecycle> getUsersByLifecycleStage(@PathVariable String stage) {
        return userSegmentationService.getUsersByLifecycleStage(stage);
    }

    @GetMapping("/lifecycle/count")
    public Map<String, Long> countUsersByLifecycleStage() {
        return userSegmentationService.countUsersByLifecycleStage();
    }

    // 自定义标签相关接口
    @PostMapping("/tag")
    public UserTag saveUserTag(@RequestBody UserTag userTag) {
        return userSegmentationService.saveUserTag(userTag);
    }

    @PutMapping("/tag")
    public UserTag updateUserTag(@RequestBody UserTag userTag) {
        return userSegmentationService.updateUserTag(userTag);
    }

    @DeleteMapping("/tag/{tagId}")
    public void deleteUserTag(@PathVariable Long tagId) {
        userSegmentationService.deleteUserTag(tagId);
    }

    @GetMapping("/tag/list")
    public List<UserTag> listUserTags() {
        return userSegmentationService.listUserTags();
    }

    @PostMapping("/tag/assign")
    public void assignTagsToUser(
            @RequestParam String userId,
            @RequestParam List<Long> tagIds,
            @RequestParam String assignBy) {
        userSegmentationService.assignTagsToUser(userId, tagIds, assignBy);
    }

    @PostMapping("/tag/remove")
    public void removeTagsFromUser(
            @RequestParam String userId,
            @RequestParam List<Long> tagIds) {
        userSegmentationService.removeTagsFromUser(userId, tagIds);
    }

    @GetMapping("/tag/user/{userId}")
    public Set<UserTag> getUserTags(@PathVariable String userId) {
        return userSegmentationService.getUserTags(userId);
    }

    @GetMapping("/tag/users/{tagId}")
    public List<String> getUsersByTag(@PathVariable Long tagId) {
        return userSegmentationService.getUsersByTag(tagId);
    }
}
