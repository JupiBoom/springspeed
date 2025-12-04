package com.yasinyuan.testspring.point.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.point.entity.PointAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分账户Mapper接口
 */
@Mapper
public interface PointAccountMapper extends BaseMapper<PointAccount> {
}
