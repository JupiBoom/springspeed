package com.yasinyuan.testspring.service.benefit;

import com.yasinyuan.testspring.entity.benefit.BenefitDistributionRecord;

import java.util.Date;
import java.util.List;

public interface BenefitDistributionService {

    // 发放会员权益
    BenefitDistributionRecord distributeBenefit(Long memberId, Long benefitId, Long relatedId, String description, Date expireDate) throws Exception;

    // 批量发放会员权益
    List<BenefitDistributionRecord> batchDistributeBenefits(List<Long> memberIds, Long benefitId, Long relatedId, String description, Date expireDate) throws Exception;

    // 发放生日特权
    List<BenefitDistributionRecord> distributeBirthdayBenefits() throws Exception;

    // 根据会员等级变更发放对应权益
    List<BenefitDistributionRecord> distributeBenefitsByLevelChange(Long memberId, Long newLevelId) throws Exception;

    // 根据ID查找权益发放记录
    BenefitDistributionRecord findBenefitDistributionRecordById(Long id);

    // 根据会员ID查找权益发放记录
    List<BenefitDistributionRecord> findBenefitDistributionRecordsByMemberId(Long memberId);

    // 根据权益ID查找权益发放记录
    List<BenefitDistributionRecord> findBenefitDistributionRecordsByBenefitId(Long benefitId);

    // 根据发放状态查找权益发放记录
    List<BenefitDistributionRecord> findBenefitDistributionRecordsByStatus(Integer status);

    // 处理权益过期
    void processBenefitExpiration();

    // 查找即将过期的权益
    List<BenefitDistributionRecord> findExpiringBenefits(Integer days);

    // 统计指定会员的权益发放总数
    Integer countBenefitsByMember(Long memberId);

    // 统计指定权益的发放总数
    Integer countDistributionsByBenefit(Long benefitId);

    // 检查防刷规则
    boolean checkAntiBrushRules(Long memberId, Long benefitId) throws Exception;
}
