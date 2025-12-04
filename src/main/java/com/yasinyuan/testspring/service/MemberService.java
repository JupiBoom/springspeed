package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.model.Member;
import com.yasinyuan.testspring.core.Service;

import java.util.List;

public interface MemberService extends Service<Member> {
    // 根据用户ID查询会员信息
    Member findByUserId(Long userId);
    
    // 会员注册
    void registerMember(Long userId);
    
    // 更新会员成长值
    void updateGrowthValue(Long memberId, Integer growthValue, Integer businessType, Long businessId, String remark);
    
    // 检查并更新会员等级
    void checkAndUpdateMemberLevel(Long memberId);
    
    // 会员生日提醒及权益发放
    void processMemberBirthdayBenefits();
    
    // 获取会员统计信息
    MemberStatistics getMemberStatistics();
    
    // 获取会员活跃度热力图数据
    List<MemberActivityHeatmapData> getMemberActivityHeatmap();
    
    // 内部类：会员统计信息
    class MemberStatistics {
        private Integer totalMembers;
        private Integer newMembersToday;
        private Integer activeMembersThisMonth;
        private Double averageGrowthValue;
        
        // getter和setter方法
        public Integer getTotalMembers() { return totalMembers; }
        public void setTotalMembers(Integer totalMembers) { this.totalMembers = totalMembers; }
        public Integer getNewMembersToday() { return newMembersToday; }
        public void setNewMembersToday(Integer newMembersToday) { this.newMembersToday = newMembersToday; }
        public Integer getActiveMembersThisMonth() { return activeMembersThisMonth; }
        public void setActiveMembersThisMonth(Integer activeMembersThisMonth) { this.activeMembersThisMonth = activeMembersThisMonth; }
        public Double getAverageGrowthValue() { return averageGrowthValue; }
        public void setAverageGrowthValue(Double averageGrowthValue) { this.averageGrowthValue = averageGrowthValue; }
    }
    
    // 内部类：会员活跃度热力图数据
    class MemberActivityHeatmapData {
        private String date;
        private Integer activeMemberCount;
        
        // getter和setter方法
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public Integer getActiveMemberCount() { return activeMemberCount; }
        public void setActiveMemberCount(Integer activeMemberCount) { this.activeMemberCount = activeMemberCount; }
    }
}