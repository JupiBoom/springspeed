package com.yasinyuan.testspring.dao.analytics;

import com.yasinyuan.testspring.entity.analytics.MemberLTV;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface MemberLTVRepository {

    // 根据会员ID查找LTV记录
    MemberLTV findByMemberId(Long memberId);

    // 查找LTV评分大于等于指定值的会员
    List<MemberLTV> findByLtvScoreGreaterThanEqual(Double score);

    // 查找LTV评分小于指定值的会员
    List<MemberLTV> findByLtvScoreLessThan(Double score);

    // 按LTV评分降序排列
    List<MemberLTV> findAllByOrderByLtvScoreDesc();

    // 按LTV评分升序排列
    List<MemberLTV> findAllByOrderByLtvScoreAsc();

    // 统计LTV相关的平均值
    Object[] getLTVStatistics();

    // 查找在指定日期之后计算过LTV的会员
    List<MemberLTV> findByLastCalculationDateAfter(Date lastCalculationDate);

    // 查找需要重新计算LTV的会员（上次计算在指定天数之前）
    List<MemberLTV> findMembersNeedingLTVRecalculation(Integer days);
}
