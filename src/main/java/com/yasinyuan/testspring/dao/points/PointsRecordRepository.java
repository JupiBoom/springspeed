package com.yasinyuan.testspring.dao.points;

import com.yasinyuan.testspring.entity.points.PointsRecord;
import com.yasinyuan.testspring.core.Mapper;

import java.util.Date;
import java.util.List;

public interface PointsRecordRepository extends Mapper<PointsRecord> {

    // 根据会员ID查找积分记录
    List<PointsRecord> findByMemberId(Long memberId);

    // 根据会员ID和积分类型查找积分记录
    List<PointsRecord> findByMemberIdAndPointsType(Long memberId, Integer pointsType);

    // 根据会员ID和积分场景查找积分记录
    List<PointsRecord> findByMemberIdAndPointsScene(Long memberId, Integer pointsScene);

    // 根据积分类型查找积分记录
    List<PointsRecord> findByPointsType(Integer pointsType);

    // 根据积分场景查找积分记录
    List<PointsRecord> findByPointsScene(Integer pointsScene);

    // 查找在指定日期范围内的积分记录
    List<PointsRecord> findByCreateDateBetween(Date startDate, Date endDate);

    // 查找指定会员在指定日期范围内的积分记录
    List<PointsRecord> findByMemberIdAndDateRange(Long memberId, Date startDate, Date endDate);

    // 查找即将过期的积分记录（过期日期在指定天数内）
    List<PointsRecord> findExpiringPoints(Integer days);

    // 统计指定会员的积分获取总额
    Integer sumPointsEarnedByMember(Long memberId);

    // 统计指定会员的积分消耗总额
    Integer sumPointsUsedByMember(Long memberId);

    // 统计指定日期范围内的积分获取总额
    Integer sumPointsEarnedByDateRange(Date startDate, Date endDate);

    // 统计指定日期范围内的积分消耗总额
    Integer sumPointsUsedByDateRange(Date startDate, Date endDate);
}
