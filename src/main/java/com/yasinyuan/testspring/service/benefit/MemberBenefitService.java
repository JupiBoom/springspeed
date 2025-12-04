package com.yasinyuan.testspring.service.benefit;

import com.yasinyuan.testspring.entity.benefit.MemberBenefit;

import java.util.List;

public interface MemberBenefitService {

    // 创建会员权益
    MemberBenefit createMemberBenefit(MemberBenefit memberBenefit);

    // 更新会员权益
    MemberBenefit updateMemberBenefit(MemberBenefit memberBenefit);

    // 根据ID查找会员权益
    MemberBenefit findMemberBenefitById(Long id);

    // 根据会员等级ID查找权益列表
    List<MemberBenefit> findMemberBenefitsByLevelId(Long levelId);

    // 根据权益类型查找权益列表
    List<MemberBenefit> findMemberBenefitsByType(Integer benefitType);

    // 查找已激活的权益
    List<MemberBenefit> findActiveMemberBenefits();

    // 查找指定会员等级的当前有效权益
    List<MemberBenefit> findValidBenefitsByLevelId(Long levelId);

    // 激活/停用会员权益
    MemberBenefit toggleMemberBenefitStatus(Long id);

    // 查找所有会员权益
    List<MemberBenefit> findAllMemberBenefits();

    // 删除会员权益
    void deleteMemberBenefit(Long id);
}
