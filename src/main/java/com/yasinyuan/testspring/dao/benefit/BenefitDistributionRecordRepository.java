package com.yasinyuan.testspring.dao.benefit;

import com.yasinyuan.testspring.entity.benefit.BenefitDistributionRecord;
import com.yasinyuan.testspring.core.Mapper;

import java.util.Date;
import java.util.List;

public interface BenefitDistributionRecordRepository extends Mapper<BenefitDistributionRecord> {

    // 根据会员ID查找权益发放记录
    List<BenefitDistributionRecord> findByMemberId(Long memberId);

    // 根据权益ID查找权益发放记录
    List<BenefitDistributionRecord> findByBenefitId(Long benefitId);

    // 根据发放状态查找权益发放记录
    List<BenefitDistributionRecord> findByDistributionStatus(Integer distributionStatus);

    // 根据会员ID和发放状态查找权益发放记录
    List<BenefitDistributionRecord> findByMemberIdAndDistributionStatus(Long memberId, Integer distributionStatus);

    // 根据会员ID和权益ID查找权益发放记录
    List<BenefitDistributionRecord> findByMemberIdAndBenefitId(Long memberId, Long benefitId);

    // 查找在指定日期范围内发放的权益记录
    List<BenefitDistributionRecord> findByDistributionDateBetween(Date startDate, Date endDate);

    // 查找即将过期的权益（过期日期在指定天数内）
    List<BenefitDistributionRecord> findExpiringBenefits(Integer days);

    // 统计指定会员的权益发放总数
    Integer countBenefitsByMember(Long memberId);

    // 统计指定权益的发放总数
    Integer countDistributionsByBenefit(Long benefitId);

    // 统计指定日期范围内的权益发放总数
    Integer countDistributionsByDateRange(Date startDate, Date endDate);
}
