package com.yasinyuan.testspring.dao.points;

import com.yasinyuan.testspring.entity.points.PointsAccount;
import com.yasinyuan.testspring.core.Mapper;

import java.util.List;

public interface PointsAccountRepository extends Mapper<PointsAccount> {

    // 根据会员ID查找积分账户
    PointsAccount findByMemberId(Long memberId);

    // 查找可用积分大于等于指定值的积分账户
    List<PointsAccount> findByAvailablePointsGreaterThanEqual(Integer points);

    // 查找冻结积分大于等于指定值的积分账户
    List<PointsAccount> findByFrozenPointsGreaterThanEqual(Integer points);

    // 统计所有会员的总积分
    Integer sumTotalPoints();

    // 统计平均每个会员的积分
    Double avgTotalPointsPerMember();
}
