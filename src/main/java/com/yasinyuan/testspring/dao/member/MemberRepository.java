package com.yasinyuan.testspring.dao.member;

import com.yasinyuan.testspring.entity.member.Member;
import com.yasinyuan.testspring.core.Mapper;

import java.util.Date;
import java.util.List;

public interface MemberRepository extends Mapper<Member> {

    // 根据用户名查找会员
    Member findByUsername(String username);

    // 根据邮箱查找会员
    Member findByEmail(String email);

    // 根据手机号查找会员
    Member findByPhone(String phone);

    // 根据会员等级ID查找会员列表
    List<Member> findByLevelId(Long levelId);

    // 根据状态查找会员列表
    List<Member> findByStatus(Integer status);

    // 查找在指定日期之后注册的会员
    List<Member> findByJoinDateAfter(Date joinDate);

    // 查找在指定日期范围内注册的会员
    List<Member> findByJoinDateBetween(Date startDate, Date endDate);

    // 根据成长值范围查找会员
    List<Member> findByGrowthValueRange(Integer minGrowth, Integer maxGrowth);

    // 查找即将过期的会员（过期日期在指定天数内）
    List<Member> findExpiringMembers(Integer days);

    // 统计不同会员等级的数量
    List<Object[]> countMembersByLevel();

    // 统计指定时间段内的新增会员数量
    Integer countNewMembersByTimeRange(Date startDate, Date endDate);
}
