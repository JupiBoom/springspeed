package com.yasinyuan.testspring.dao.benefit;

import com.yasinyuan.testspring.entity.benefit.MemberBenefit;
import com.yasinyuan.testspring.core.Mapper;

import java.util.List;

public interface MemberBenefitRepository extends Mapper<MemberBenefit> {

    // 根据会员等级ID查找权益列表
    List<MemberBenefit> findByLevelId(Long levelId);

    // 根据权益类型查找权益列表
    List<MemberBenefit> findByBenefitType(Integer benefitType);

    // 查找已激活的权益
    List<MemberBenefit> findByIsActive(Boolean isActive);

    // 根据会员等级ID和权益类型查找权益
    MemberBenefit findByLevelIdAndBenefitType(Long levelId, Integer benefitType);

    // 查找指定会员等级和权益名称的权益
    MemberBenefit findByLevelIdAndBenefitName(Long levelId, String benefitName);

    // 查找在指定日期范围内有效的权益
    List<MemberBenefit> findCurrentValidBenefits();

    // 查找指定会员等级的当前有效权益
    List<MemberBenefit> findValidBenefitsByLevelId(Long levelId);
}
