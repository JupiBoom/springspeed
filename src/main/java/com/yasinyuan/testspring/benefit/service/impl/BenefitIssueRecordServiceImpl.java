package com.yasinyuan.testspring.benefit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yasinyuan.testspring.benefit.entity.BenefitIssueRecord;
import com.yasinyuan.testspring.benefit.mapper.BenefitIssueRecordMapper;
import com.yasinyuan.testspring.benefit.service.BenefitIssueRecordService;
import org.springframework.stereotype.Service;

/**
 * 权益发放记录Service实现类
 */
@Service
public class BenefitIssueRecordServiceImpl extends ServiceImpl<BenefitIssueRecordMapper, BenefitIssueRecord> implements BenefitIssueRecordService {
}
