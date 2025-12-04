package com.yasinyuan.testspring.analysis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yasinyuan.testspring.analysis.entity.MemberLifecycleValue;
import com.yasinyuan.testspring.analysis.mapper.MemberLifecycleValueMapper;
import com.yasinyuan.testspring.analysis.service.MemberLifecycleValueService;
import org.springframework.stereotype.Service;

/**
 * 会员生命周期价值分析Service实现类
 */
@Service
public class MemberLifecycleValueServiceImpl extends ServiceImpl<MemberLifecycleValueMapper, MemberLifecycleValue> implements MemberLifecycleValueService {
}
