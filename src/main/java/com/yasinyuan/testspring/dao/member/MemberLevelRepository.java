package com.yasinyuan.testspring.dao.member;

import com.yasinyuan.testspring.entity.member.MemberLevel;
import com.yasinyuan.testspring.core.Mapper;

import java.util.List;

public interface MemberLevelRepository extends Mapper<MemberLevel> {

    // 根据等级值查找会员等级
    MemberLevel findByLevel(Integer level);

    // 查找已启用的会员等级
    List<MemberLevel> findByStatus(Integer status);

    // 根据成长值查找对应的会员等级
    MemberLevel findLevelByGrowthValue(Integer growthValue);

    // 查找等级大于等于指定值的会员等级
    List<MemberLevel> findByLevelGreaterThanEqual(Integer level);

    // 查找等级小于等于指定值的会员等级
    List<MemberLevel> findByLevelLessThanEqual(Integer level);

    // 按等级值升序排列
    List<MemberLevel> findAllByOrderByLevelAsc();

    // 按等级值降序排列
    List<MemberLevel> findAllByOrderByLevelDesc();
}
