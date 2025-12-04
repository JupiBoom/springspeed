package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.model.PointsAccount;
import com.yasinyuan.testspring.model.PointsRecord;
import com.yasinyuan.testspring.core.Service;

import java.util.List;

public interface PointsService extends Service<PointsAccount> {
    // 根据会员ID查询积分账户
    PointsAccount findByMemberId(Long memberId);
    
    // 增加积分
    void addPoints(Long memberId, Integer points, Integer businessType, Long businessId, String remark);
    
    // 扣除积分
    void deductPoints(Long memberId, Integer points, Integer businessType, Long businessId, String remark);
    
    // 冻结积分
    void freezePoints(Long memberId, Integer points, Integer businessType, Long businessId, String remark);
    
    // 解冻积分
    void unfreezePoints(Long memberId, Integer points, Integer businessType, Long businessId, String remark);
    
    // 处理积分过期
    void processPointsExpiration();
    
    // 根据会员ID查询积分记录
    List<PointsRecord> getPointsRecordsByMemberId(Long memberId);
    
    // 获取积分统计信息
    PointsStatistics getPointsStatistics();
    
    // 内部类：积分统计信息
    class PointsStatistics {
        private Integer totalPointsIssued;
        private Integer totalPointsUsed;
        private Integer totalPointsExpired;
        private Double pointsUtilizationRate;
        
        // getter和setter方法
        public Integer getTotalPointsIssued() { return totalPointsIssued; }
        public void setTotalPointsIssued(Integer totalPointsIssued) { this.totalPointsIssued = totalPointsIssued; }
        public Integer getTotalPointsUsed() { return totalPointsUsed; }
        public void setTotalPointsUsed(Integer totalPointsUsed) { this.totalPointsUsed = totalPointsUsed; }
        public Integer getTotalPointsExpired() { return totalPointsExpired; }
        public void setTotalPointsExpired(Integer totalPointsExpired) { this.totalPointsExpired = totalPointsExpired; }
        public Double getPointsUtilizationRate() { return pointsUtilizationRate; }
        public void setPointsUtilizationRate(Double pointsUtilizationRate) { this.pointsUtilizationRate = pointsUtilizationRate; }
    }
}