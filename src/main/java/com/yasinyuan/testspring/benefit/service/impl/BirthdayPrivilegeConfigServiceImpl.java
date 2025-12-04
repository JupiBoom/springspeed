package com.yasinyuan.testspring.benefit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yasinyuan.testspring.benefit.entity.BirthdayPrivilegeConfig;
import com.yasinyuan.testspring.benefit.mapper.BirthdayPrivilegeConfigMapper;
import com.yasinyuan.testspring.benefit.service.BirthdayPrivilegeConfigService;
import org.springframework.stereotype.Service;

/**
 * 生日特权配置Service实现类
 */
@Service
public class BirthdayPrivilegeConfigServiceImpl extends ServiceImpl<BirthdayPrivilegeConfigMapper, BirthdayPrivilegeConfig> implements BirthdayPrivilegeConfigService {
}
