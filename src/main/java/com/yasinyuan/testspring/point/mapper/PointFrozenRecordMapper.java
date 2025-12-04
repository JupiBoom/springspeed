package com.yasinyuan.testspring.point.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.point.entity.PointFrozenRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分冻结记录Mapper接口
 */
@Mapper
public interface PointFrozenRecordMapper extends BaseMapper<PointFrozenRecord> {
}
