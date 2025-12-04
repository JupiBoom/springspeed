package com.yasinyuan.testspring.benefit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yasinyuan.testspring.benefit.entity.MemberBenefit;
import com.yasinyuan.testspring.benefit.mapper.MemberBenefitMapper;
import com.yasinyuan.testspring.benefit.service.MemberBenefitService;
import org.springframework.stereotype.Service;

/**
 * 会员权益Service实现类
 */
@Service
public class MemberBenefitServiceImpl extends ServiceImpl<MemberBenefitMapper, MemberBenefit> implements MemberBenefitService {
}
