package com.yasinyuan.testspring.point.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.point.entity.PointRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分变动记录Mapper接口
 */
@Mapper
public interface PointRecordMapper extends BaseMapper<PointRecord> {
}
