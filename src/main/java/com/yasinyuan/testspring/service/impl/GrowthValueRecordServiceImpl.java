package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.dao.GrowthValueRecordMapper;
import com.yasinyuan.testspring.model.GrowthValueRecord;
import com.yasinyuan.testspring.service.GrowthValueRecordService;
import com.yasinyuan.testspring.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 成长值记录服务实现类
 * @author yinyuan
 * @since 2024-01-01
 */
@Service
public class GrowthValueRecordServiceImpl extends AbstractService<GrowthValueRecord> implements GrowthValueRecordService {
    @Resource
    private GrowthValueRecordMapper growthValueRecordMapper;

    @Override
    public List<GrowthValueRecord> findByMemberId(Long memberId) {
        return growthValueRecordMapper.findByMemberId(memberId);
    }

    @Override
    public List<GrowthValueRecord> findByMemberIdAndBusinessType(Long memberId, Integer businessType) {
        return growthValueRecordMapper.findByMemberIdAndBusinessType(memberId, businessType);
    }

    @Override
    @Transactional
    public void addGrowthValueRecord(Long memberId, Integer changeType, Integer changeValue, 
        Integer beforeValue, Integer afterValue, Integer businessType, Long businessId, String remark) {
        GrowthValueRecord record = new GrowthValueRecord();
        record.setMemberId(memberId);
        record.setChangeType(changeType);
        record.setChangeValue(changeValue);
        record.setBeforeValue(beforeValue);
        record.setAfterValue(afterValue);
        record.setBusinessType(businessType);
        record.setBusinessId(businessId);
        record.setRemark(remark);
        record.setCreateTime(new Date());
        growthValueRecordMapper.insertSelective(record);
    }
}