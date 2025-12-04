package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.PointsRecord;
import com.yasinyuan.testspring.core.Mapper;

import java.util.Date;
import java.util.List;

public interface PointsRecordMapper extends Mapper<PointsRecord> {
    // 根据会员ID查询积分记录列表
    List<PointsRecord> findByMemberId(Long memberId);
    
    // 根据会员ID和业务类型查询积分记录
    List<PointsRecord> findByMemberIdAndBusinessType(Long memberId, Integer businessType);
    
    // 查询过期的积分记录
    List<PointsRecord> findExpiredRecords(Date expireDate);
    
    // 根据会员ID和业务ID查询积分记录
    PointsRecord findByMemberIdAndBusinessId(Long memberId, Long businessId);
}