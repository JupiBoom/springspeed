package com.yasinyuan.testspring.point.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.point.entity.PointRuleConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分规则配置Mapper接口
 */
@Mapper
public interface PointRuleConfigMapper extends BaseMapper<PointRuleConfig> {
}
