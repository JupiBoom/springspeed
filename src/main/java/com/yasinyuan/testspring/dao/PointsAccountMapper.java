package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.PointsAccount;
import com.yasinyuan.testspring.core.Mapper;

public interface PointsAccountMapper extends Mapper<PointsAccount> {
    // 根据会员ID查询积分账户
    PointsAccount findByMemberId(Long memberId);
}