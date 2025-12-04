package com.yasinyuan.testspring.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yasinyuan.testspring.member.entity.Member;
import com.yasinyuan.testspring.member.mapper.MemberMapper;
import com.yasinyuan.testspring.member.service.MemberService;
import org.springframework.stereotype.Service;

/**
 * 会员Service实现类
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
}
