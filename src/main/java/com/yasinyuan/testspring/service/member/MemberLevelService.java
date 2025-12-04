package com.yasinyuan.testspring.service.member;

import com.yasinyuan.testspring.entity.member.MemberLevel;

import java.util.List;

public interface MemberLevelService {

    // 创建会员等级
    MemberLevel createMemberLevel(MemberLevel memberLevel);

    // 更新会员等级
    MemberLevel updateMemberLevel(MemberLevel memberLevel);

    // 根据ID查找会员等级
    MemberLevel findMemberLevelById(Long id);

    // 根据等级值查找会员等级
    MemberLevel findMemberLevelByLevel(Integer level);

    // 根据成长值查找对应的会员等级
    MemberLevel findMemberLevelByGrowthValue(Integer growthValue);

    // 启用/禁用会员等级
    MemberLevel toggleMemberLevelStatus(Long id);

    // 查找所有会员等级
    List<MemberLevel> findAllMemberLevels();

    // 查找已启用的会员等级
    List<MemberLevel> findEnabledMemberLevels();

    // 按等级值升序排列
    List<MemberLevel> findAllMemberLevelsOrderedByLevelAsc();

    // 按等级值降序排列
    List<MemberLevel> findAllMemberLevelsOrderedByLevelDesc();
}
