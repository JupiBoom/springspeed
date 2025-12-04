package com.yasinyuan.testspring.point.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yasinyuan.testspring.point.entity.PointRuleConfig;
import com.yasinyuan.testspring.point.mapper.PointRuleConfigMapper;
import com.yasinyuan.testspring.point.service.PointRuleConfigService;
import org.springframework.stereotype.Service;

/**
 * 积分规则配置Service实现类
 */
@Service
public class PointRuleConfigServiceImpl extends ServiceImpl<PointRuleConfigMapper, PointRuleConfig> implements PointRuleConfigService {
}
