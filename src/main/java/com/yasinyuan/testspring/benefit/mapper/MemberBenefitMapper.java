package com.yasinyuan.testspring.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.benefit.entity.MemberBenefit;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员权益Mapper接口
 */
@Mapper
public interface MemberBenefitMapper extends BaseMapper<MemberBenefit> {
}
