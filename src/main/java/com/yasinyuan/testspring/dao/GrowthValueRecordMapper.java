package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.GrowthValueRecord;
import com.yasinyuan.testspring.core.Mapper;

import java.util.List;

public interface GrowthValueRecordMapper extends Mapper<GrowthValueRecord> {
    // 根据会员ID查询成长值记录列表
    List<GrowthValueRecord> findByMemberId(Long memberId);
    
    // 根据会员ID和业务类型查询成长值记录
    List<GrowthValueRecord> findByMemberIdAndBusinessType(Long memberId, Integer businessType);
}