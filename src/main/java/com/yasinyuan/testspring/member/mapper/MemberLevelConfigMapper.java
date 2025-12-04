package com.yasinyuan.testspring.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.member.entity.MemberLevelConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员等级配置Mapper接口
 */
@Mapper
public interface MemberLevelConfigMapper extends BaseMapper<MemberLevelConfig> {
}
