package com.yasinyuan.testspring.analysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.analysis.entity.PointUsageEfficiency;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分使用效率分析Mapper接口
 */
@Mapper
public interface PointUsageEfficiencyMapper extends BaseMapper<PointUsageEfficiency> {
}
