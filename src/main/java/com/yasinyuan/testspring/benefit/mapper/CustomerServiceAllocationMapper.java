package com.yasinyuan.testspring.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.benefit.entity.CustomerServiceAllocation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客服分配Mapper接口
 */
@Mapper
public interface CustomerServiceAllocationMapper extends BaseMapper<CustomerServiceAllocation> {
}
