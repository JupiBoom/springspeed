package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.Member;
import com.yasinyuan.testspring.core.Mapper;

public interface MemberMapper extends Mapper<Member> {
    // 根据用户ID查询会员信息
    Member findByUserId(Long userId);
    
    // 根据会员等级ID查询会员列表
    List<Member> findByLevelId(Long levelId);
}