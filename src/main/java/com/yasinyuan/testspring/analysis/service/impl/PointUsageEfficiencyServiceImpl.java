package com.yasinyuan.testspring.analysis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yasinyuan.testspring.analysis.entity.PointUsageEfficiency;
import com.yasinyuan.testspring.analysis.mapper.PointUsageEfficiencyMapper;
import com.yasinyuan.testspring.analysis.service.PointUsageEfficiencyService;
import org.springframework.stereotype.Service;

/**
 * 积分使用效率分析Service实现类
 */
@Service
public class PointUsageEfficiencyServiceImpl extends ServiceImpl<PointUsageEfficiencyMapper, PointUsageEfficiency> implements PointUsageEfficiencyService {
}
