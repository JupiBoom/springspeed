package com.yasinyuan.testspring.point.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yasinyuan.testspring.point.entity.PointAccount;
import com.yasinyuan.testspring.point.mapper.PointAccountMapper;
import com.yasinyuan.testspring.point.service.PointAccountService;
import org.springframework.stereotype.Service;

/**
 * 积分账户Service实现类
 */
@Service
public class PointAccountServiceImpl extends ServiceImpl<PointAccountMapper, PointAccount> implements PointAccountService {
}
