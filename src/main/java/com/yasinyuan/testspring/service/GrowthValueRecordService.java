package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.model.GrowthValueRecord;
import com.yasinyuan.testspring.core.Service;

import java.util.List;

public interface GrowthValueRecordService extends Service<GrowthValueRecord> {
    // 根据会员ID查询成长值记录列表
    List<GrowthValueRecord> findByMemberId(Long memberId);
    
    // 根据会员ID和业务类型查询成长值记录
    List<GrowthValueRecord> findByMemberIdAndBusinessType(Long memberId, Integer businessType);
    
    // 添加成长值记录
    void addGrowthValueRecord(Long memberId, Integer changeType, Integer changeValue, 
        Integer beforeValue, Integer afterValue, Integer businessType, Long businessId, String remark);
}