package com.yasinyuan.testspring.service.member;

import com.yasinyuan.testspring.entity.member.Member;
import com.yasinyuan.testspring.entity.member.MemberLevel;

import java.util.Date;
import java.util.List;

public interface MemberService {

    // 会员注册
    Member registerMember(Member member);

    // 会员登录
    Member loginMember(String username, String password);

    // 根据ID查找会员
    Member findMemberById(Long id);

    // 根据用户名查找会员
    Member findMemberByUsername(String username);

    // 根据邮箱查找会员
    Member findMemberByEmail(String email);

    // 根据手机号查找会员
    Member findMemberByPhone(String phone);

    // 更新会员信息
    Member updateMember(Member member);

    // 更新会员成长值
    Member updateMemberGrowthValue(Long memberId, Integer growthValueChange);

    // 检查会员等级是否需要变更
    MemberLevel checkMemberLevelChange(Long memberId);

    // 会员等级变更
    Member changeMemberLevel(Long memberId, Long newLevelId);

    // 禁用/启用会员
    Member toggleMemberStatus(Long memberId);

    // 延长会员有效期
    Member extendMemberExpireDate(Long memberId, Integer days);

    // 查找所有会员
    List<Member> findAllMembers();

    // 根据会员等级查找会员
    List<Member> findMembersByLevelId(Long levelId);

    // 根据状态查找会员
    List<Member> findMembersByStatus(Integer status);

    // 查找即将过期的会员
    List<Member> findExpiringMembers(Integer days);

    // 统计不同会员等级的数量
    List<Object[]> countMembersByLevel();

    // 统计指定时间段内的新增会员数量
    Integer countNewMembersByTimeRange(Date startDate, Date endDate);
}
