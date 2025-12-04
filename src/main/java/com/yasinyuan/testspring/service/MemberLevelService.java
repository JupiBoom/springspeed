package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.model.MemberLevel;
import com.yasinyuan.testspring.core.Service;

import java.util.List;

public interface MemberLevelService extends Service<MemberLevel> {
    // 查询启用的会员等级列表
    List<MemberLevel> findEnabledLevels();
    
    // 根据成长值查询对应的会员等级
    MemberLevel findByGrowthValue(Integer growthValue);
    
    // 初始化会员等级（普通/白银/黄金/铂金）
    void initMemberLevels();
}