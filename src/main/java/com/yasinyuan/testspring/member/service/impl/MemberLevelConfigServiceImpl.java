package com.yasinyuan.testspring.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yasinyuan.testspring.member.entity.MemberLevelConfig;
import com.yasinyuan.testspring.member.mapper.MemberLevelConfigMapper;
import com.yasinyuan.testspring.member.service.MemberLevelConfigService;
import org.springframework.stereotype.Service;

/**
 * 会员等级配置Service实现类
 */
@Service
public class MemberLevelConfigServiceImpl extends ServiceImpl<MemberLevelConfigMapper, MemberLevelConfig> implements MemberLevelConfigService {
}
