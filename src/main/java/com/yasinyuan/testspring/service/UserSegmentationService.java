package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.model.UserLifecycle;
import com.yasinyuan.testspring.model.UserRfm;
import com.yasinyuan.testspring.model.UserTag;
import com.yasinyuan.testspring.model.UserUserTag;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserSegmentationService {
    // RFM模型相关
    List<UserRfm> calculateRfmModel(Date startDate, Date endDate);
    UserRfm getUserRfm(String userId);
    List<UserRfm> getUsersByRfmSegment(String segment);
    Map<String, Long> countUsersByRfmSegment();

    // 用户生命周期相关
    List<UserLifecycle> calculateUserLifecycle();
    UserLifecycle getUserLifecycle(String userId);
    List<UserLifecycle> getUsersByLifecycleStage(String stage);
    Map<String, Long> countUsersByLifecycleStage();

    // 自定义标签相关
    UserTag saveUserTag(UserTag userTag);
    UserTag updateUserTag(UserTag userTag);
    void deleteUserTag(Long tagId);
    List<UserTag> listUserTags();
    void assignTagsToUser(String userId, List<Long> tagIds, String assignBy);
    void removeTagsFromUser(String userId, List<Long> tagIds);
    Set<UserTag> getUserTags(String userId);
    List<String> getUsersByTag(Long tagId);
}
