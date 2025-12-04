package com.yasinyuan.testspring.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.benefit.entity.BenefitIssueRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权益发放记录Mapper接口
 */
@Mapper
public interface BenefitIssueRecordMapper extends BaseMapper<BenefitIssueRecord> {
}
