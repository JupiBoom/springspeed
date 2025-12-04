package com.yasinyuan.testspring.service.benefit.impl;

import com.yasinyuan.testspring.dao.benefit.BenefitDistributionRecordRepository;
import com.yasinyuan.testspring.dao.benefit.MemberBenefitRepository;
import com.yasinyuan.testspring.entity.benefit.BenefitDistributionRecord;
import com.yasinyuan.testspring.entity.benefit.MemberBenefit;
import com.yasinyuan.testspring.service.benefit.BenefitDistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BenefitDistributionServiceImpl implements BenefitDistributionService {

    @Autowired
    private BenefitDistributionRecordRepository distributionRecordRepository;

    @Autowired
    private MemberBenefitRepository memberBenefitRepository;

    @Override
    public BenefitDistributionRecord distributeBenefit(Long memberId, Long benefitId) {
        // 检查权益是否存在且有效
        MemberBenefit benefit = memberBenefitRepository.findOne(benefitId);
        if (benefit == null) {
            throw new RuntimeException("权益不存在");
        }
        if (benefit.getStatus() != 1) {
            throw new RuntimeException("权益已停用");
        }

        // 防刷控制：检查会员在指定时间内是否已获取过该权益
        if (isBenefitDistributedRecently(memberId, benefitId)) {
            throw new RuntimeException("权益获取过于频繁，请稍后再试");
        }

        // 创建权益发放记录
        BenefitDistributionRecord record = new BenefitDistributionRecord();
        record.setMemberId(memberId);
        record.setBenefitId(benefitId);
        record.setDistributionStatus(1); // 待发放
        record.setBenefitValue(benefit.getBenefitValue());
        record.setDistributionTime(new Date());

        // 设置过期时间
        if (benefit.getValidityPeriod() != null && benefit.getValidityPeriod() > 0) {
            Date expireDate = new Date(System.currentTimeMillis() + (long) benefit.getValidityPeriod() * 24 * 60 * 60 * 1000);
            record.setExpireDate(expireDate);
        }

        // 保存发放记录
        record = distributionRecordRepository.save(record);

        // 实际发放权益的逻辑（例如，添加优惠券到用户账户，发送通知等）
        // 这里可以调用其他服务来实现具体的权益发放

        // 更新发放状态为成功
        record.setDistributionStatus(2); // 发放成功
        distributionRecordRepository.save(record);

        return record;
    }

    @Override
    public List<BenefitDistributionRecord> distributeBenefitsBatch(List<Long> memberIds, Long benefitId) {
        // 检查权益是否存在且有效
        MemberBenefit benefit = memberBenefitRepository.findOne(benefitId);
        if (benefit == null) {
            throw new RuntimeException("权益不存在");
        }
        if (benefit.getStatus() != 1) {
            throw new RuntimeException("权益已停用");
        }

        List<BenefitDistributionRecord> records = new ArrayList<>();

        for (Long memberId : memberIds) {
            try {
                // 防刷控制：检查会员在指定时间内是否已获取过该权益
                if (isBenefitDistributedRecently(memberId, benefitId)) {
                    continue;
                }

                // 创建权益发放记录
                BenefitDistributionRecord record = new BenefitDistributionRecord();
                record.setMemberId(memberId);
                record.setBenefitId(benefitId);
                record.setDistributionStatus(1); // 待发放
                record.setBenefitValue(benefit.getBenefitValue());
                record.setDistributionTime(new Date());

                // 设置过期时间
                if (benefit.getValidityPeriod() != null && benefit.getValidityPeriod() > 0) {
                    Date expireDate = new Date(System.currentTimeMillis() + (long) benefit.getValidityPeriod() * 24 * 60 * 60 * 1000);
                    record.setExpireDate(expireDate);
                }

                // 保存发放记录
                record = distributionRecordRepository.save(record);

                // 实际发放权益的逻辑

                // 更新发放状态为成功
                record.setDistributionStatus(2); // 发放成功
                distributionRecordRepository.save(record);

                records.add(record);
            } catch (Exception e) {
                // 处理单个会员发放失败的情况
                BenefitDistributionRecord record = new BenefitDistributionRecord();
                record.setMemberId(memberId);
                record.setBenefitId(benefitId);
                record.setDistributionStatus(3); // 发放失败
                record.setBenefitValue(benefit.getBenefitValue());
                record.setDistributionTime(new Date());
                record.setDescription("发放失败：" + e.getMessage());
                distributionRecordRepository.save(record);
            }
        }

        return records;
    }

    @Override
    public List<BenefitDistributionRecord> distributeBirthdayBenefits(Integer daysBeforeBirthday) {
        // 查找即将过生日的会员
        // 这里需要根据会员的生日来查询，实际项目中可能需要使用数据库函数来处理
        
        // 假设已经获取到即将过生日的会员列表
        List<Long> memberIds = new ArrayList<>();

        // 查找生日特权权益
        List<MemberBenefit> birthdayBenefits = memberBenefitRepository.findByBenefitType(2); // 生日特权类型

        List<BenefitDistributionRecord> allRecords = new ArrayList<>();

        for (MemberBenefit benefit : birthdayBenefits) {
            // 批量发放生日特权
            List<BenefitDistributionRecord> records = distributeBenefitsBatch(memberIds, benefit.getId());
            allRecords.addAll(records);
        }

        return allRecords;
    }

    @Override
    public List<BenefitDistributionRecord> distributeLevelChangeBenefits(Long memberId, Long oldLevelId, Long newLevelId) {
        // 查找新等级对应的权益
        List<MemberBenefit> newLevelBenefits = memberBenefitRepository.findByLevelId(newLevelId);

        List<BenefitDistributionRecord> records = new ArrayList<>();

        for (MemberBenefit benefit : newLevelBenefits) {
            // 发放新等级的权益
            BenefitDistributionRecord record = distributeBenefit(memberId, benefit.getId());
            records.add(record);
        }

        return records;
    }

    @Override
    public BenefitDistributionRecord findDistributionRecordById(Long id) {
        return distributionRecordRepository.findOne(id);
    }

    @Override
    public List<BenefitDistributionRecord> findDistributionRecordsByMemberId(Long memberId) {
        return distributionRecordRepository.findByMemberIdOrderByDistributionTimeDesc(memberId);
    }

    @Override
    public List<BenefitDistributionRecord> findDistributionRecordsByBenefitId(Long benefitId) {
        return distributionRecordRepository.findByBenefitIdOrderByDistributionTimeDesc(benefitId);
    }

    @Override
    public List<BenefitDistributionRecord> findDistributionRecordsByStatus(Integer status) {
        return distributionRecordRepository.findByDistributionStatus(status);
    }

    @Override
    public List<BenefitDistributionRecord> findExpiringBenefits(Integer daysBeforeExpire) {
        return distributionRecordRepository.findExpiringBenefits(daysBeforeExpire);
    }

    @Override
    public void processBenefitExpiration() {
        // 查找所有已过期的权益发放记录
        List<BenefitDistributionRecord> expiredRecords = distributionRecordRepository.findExpiredBenefits();

        for (BenefitDistributionRecord record : expiredRecords) {
            // 处理权益过期的逻辑（例如，从用户账户中移除优惠券，发送过期通知等）

            // 更新记录状态为已过期（如果需要的话）
            // record.setDistributionStatus(4); // 已过期
            // distributionRecordRepository.save(record);
        }
    }

    @Override
    public boolean isBenefitDistributedRecently(Long memberId, Long benefitId) {
        // 防刷控制：检查会员在过去24小时内是否已获取过该权益
        Date yesterday = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        Integer count = distributionRecordRepository.countByMemberIdAndBenefitIdAndDistributionTimeAfter(memberId, benefitId, yesterday);
        return count != null && count > 0;
    }

    @Override
    public Integer countDistributedBenefitsByMemberIdAndBenefitId(Long memberId, Long benefitId) {
        return distributionRecordRepository.countByMemberIdAndBenefitId(memberId, benefitId);
    }

    @Override
    public Integer countDistributedBenefitsByDateRange(Date startDate, Date endDate) {
        return distributionRecordRepository.countByDistributionTimeBetween(startDate, endDate);
    }
}
