package com.yasinyuan.testspring.analysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.analysis.entity.MemberLifecycleValue;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员生命周期价值分析Mapper接口
 */
@Mapper
public interface MemberLifecycleValueMapper extends BaseMapper<MemberLifecycleValue> {
}
