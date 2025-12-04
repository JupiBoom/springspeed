package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.MemberBenefitRecord;
import com.yasinyuan.testspring.core.Mapper;

import java.util.List;

public interface MemberBenefitRecordMapper extends Mapper<MemberBenefitRecord> {
    // 根据会员ID查询权益记录列表
    List<MemberBenefitRecord> findByMemberId(Long memberId);
    
    // 根据会员ID和权益类型查询权益记录
    List<MemberBenefitRecord> findByMemberIdAndBenefitType(Long memberId, Integer benefitType);
    
    // 查询未使用且未过期的权益记录
    List<MemberBenefitRecord> findAvailableBenefits(Long memberId, Integer benefitType);
}