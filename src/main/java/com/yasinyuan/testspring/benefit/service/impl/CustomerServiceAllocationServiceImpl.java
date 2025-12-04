package com.yasinyuan.testspring.benefit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yasinyuan.testspring.benefit.entity.CustomerServiceAllocation;
import com.yasinyuan.testspring.benefit.mapper.CustomerServiceAllocationMapper;
import com.yasinyuan.testspring.benefit.service.CustomerServiceAllocationService;
import org.springframework.stereotype.Service;

/**
 * 客服分配Service实现类
 */
@Service
public class CustomerServiceAllocationServiceImpl extends ServiceImpl<CustomerServiceAllocationMapper, CustomerServiceAllocation> implements CustomerServiceAllocationService {
}
