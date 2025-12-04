package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.MemberLevel;
import com.yasinyuan.testspring.core.Mapper;

import java.util.List;

public interface MemberLevelMapper extends Mapper<MemberLevel> {
    // 查询启用的会员等级列表
    List<MemberLevel> findEnabledLevels();
    
    // 根据成长值查询对应的会员等级
    MemberLevel findByGrowthValue(Integer growthValue);
}