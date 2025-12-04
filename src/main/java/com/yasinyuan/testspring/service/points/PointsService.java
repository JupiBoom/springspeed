package com.yasinyuan.testspring.service.points;

import com.yasinyuan.testspring.entity.points.PointsAccount;
import com.yasinyuan.testspring.entity.points.PointsRecord;

import java.util.Date;
import java.util.List;

public interface PointsService {

    // 初始化会员积分账户
    PointsAccount initializePointsAccount(Long memberId);

    // 根据会员ID查找积分账户
    PointsAccount findPointsAccountByMemberId(Long memberId);

    // 会员获取积分
    PointsRecord earnPoints(Long memberId, Integer pointsAmount, Integer pointsScene, Long relatedId, String description, Date expireDate);

    // 会员消耗积分
    PointsRecord consumePoints(Long memberId, Integer pointsAmount, Integer pointsScene, Long relatedId, String description) throws Exception;

    // 冻结会员积分
    PointsRecord freezePoints(Long memberId, Integer pointsAmount, Long relatedId, String description) throws Exception;

    // 解冻会员积分
    PointsRecord unfreezePoints(Long memberId, Integer pointsAmount, Long relatedId, String description) throws Exception;

    // 处理积分过期
    void processPointsExpiration();

    // 查找会员的积分记录
    List<PointsRecord> findPointsRecordsByMemberId(Long memberId);

    // 查找会员的有效积分（未过期）
    Integer getMemberAvailablePoints(Long memberId);

    // 统计会员的总积分获取
    Integer getMemberTotalPointsEarned(Long memberId);

    // 统计会员的总积分消耗
    Integer getMemberTotalPointsUsed(Long memberId);

    // 查找即将过期的积分
    List<PointsRecord> findExpiringPoints(Integer days);

    // 统计指定日期范围内的积分获取总额
    Integer sumPointsEarnedByDateRange(Date startDate, Date endDate);

    // 统计指定日期范围内的积分消耗总额
    Integer sumPointsUsedByDateRange(Date startDate, Date endDate);
}
