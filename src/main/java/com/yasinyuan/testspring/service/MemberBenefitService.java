package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.model.MemberBenefitRecord;
import com.yasinyuan.testspring.core.Service;

import java.util.List;

public interface MemberBenefitService extends Service<MemberBenefitRecord> {
    // 根据会员ID查询权益记录
    List<MemberBenefitRecord> findByMemberId(Long memberId);
    
    // 根据会员ID和权益类型查询权益记录
    List<MemberBenefitRecord> findByMemberIdAndBenefitType(Long memberId, Integer benefitType);
    
    // 查询未使用且未过期的权益记录
    List<MemberBenefitRecord> findAvailableBenefits(Long memberId, Integer benefitType);
    
    // 发放运费券
    void issueFreightCoupons(Long memberId, Integer quantity, String reason);
    
    // 发放生日积分
    void issueBirthdayPoints(Long memberId, String reason);
    
    // 分配专属客服
    void assignExclusiveService(Long memberId, Long serviceUserId, String reason);
    
    // 使用权益
    void useBenefit(Long recordId);
    
    // 检查权益是否可使用（防刷控制）
    boolean checkBenefitAvailable(Long memberId, Integer benefitType);
}