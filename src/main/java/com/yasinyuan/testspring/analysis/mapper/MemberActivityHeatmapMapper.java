package com.yasinyuan.testspring.analysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.analysis.entity.MemberActivityHeatmap;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员活跃度热力图Mapper接口
 */
@Mapper
public interface MemberActivityHeatmapMapper extends BaseMapper<MemberActivityHeatmap> {
}
