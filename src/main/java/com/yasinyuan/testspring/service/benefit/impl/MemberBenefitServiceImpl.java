package com.yasinyuan.testspring.service.benefit.impl;

import com.yasinyuan.testspring.dao.benefit.MemberBenefitRepository;
import com.yasinyuan.testspring.entity.benefit.MemberBenefit;
import com.yasinyuan.testspring.service.benefit.MemberBenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MemberBenefitServiceImpl implements MemberBenefitService {

    @Autowired
    private MemberBenefitRepository memberBenefitRepository;

    @Override
    public MemberBenefit createMemberBenefit(MemberBenefit benefit) {
        // 检查权益名称是否已存在
        if (memberBenefitRepository.findByName(benefit.getName()) != null) {
            throw new RuntimeException("权益名称已存在");
        }

        // 设置创建时间和状态
        benefit.setCreateTime(new Date());
        benefit.setStatus(1); // 启用状态

        return memberBenefitRepository.save(benefit);
    }

    @Override
    public MemberBenefit updateMemberBenefit(MemberBenefit benefit) {
        MemberBenefit existingBenefit = memberBenefitRepository.findOne(benefit.getId());
        if (existingBenefit == null) {
            throw new RuntimeException("会员权益不存在");
        }

        // 检查权益名称是否已存在（排除当前权益）
        if (!existingBenefit.getName().equals(benefit.getName())) {
            if (memberBenefitRepository.findByName(benefit.getName()) != null) {
                throw new RuntimeException("权益名称已存在");
            }
        }

        // 更新会员权益信息
        existingBenefit.setName(benefit.getName());
        existingBenefit.setDescription(benefit.getDescription());
        existingBenefit.setBenefitType(benefit.getBenefitType());
        existingBenefit.setLevelId(benefit.getLevelId());
        existingBenefit.setBenefitValue(benefit.getBenefitValue());
        existingBenefit.setValidityPeriod(benefit.getValidityPeriod());
        existingBenefit.setMaxUseCount(benefit.getMaxUseCount());
        existingBenefit.setUpdateTime(new Date());

        return memberBenefitRepository.save(existingBenefit);
    }

    @Override
    public MemberBenefit findMemberBenefitById(Long id) {
        return memberBenefitRepository.findOne(id);
    }

    @Override
    public List<MemberBenefit> findMemberBenefitsByLevelId(Long levelId) {
        return memberBenefitRepository.findByLevelId(levelId);
    }

    @Override
    public List<MemberBenefit> findMemberBenefitsByType(Integer benefitType) {
        return memberBenefitRepository.findByBenefitType(benefitType);
    }

    @Override
    public List<MemberBenefit> findActiveMemberBenefits() {
        return memberBenefitRepository.findActiveMemberBenefits();
    }

    @Override
    public List<MemberBenefit> findValidMemberBenefits() {
        return memberBenefitRepository.findValidMemberBenefits();
    }

    @Override
    public List<MemberBenefit> findMemberBenefitsByLevelIdAndValid(Long levelId) {
        return memberBenefitRepository.findMemberBenefitsByLevelIdAndValid(levelId);
    }

    @Override
    public MemberBenefit activateMemberBenefit(Long id) {
        MemberBenefit benefit = memberBenefitRepository.findOne(id);
        if (benefit == null) {
            throw new RuntimeException("会员权益不存在");
        }

        benefit.setStatus(1);
        benefit.setUpdateTime(new Date());

        return memberBenefitRepository.save(benefit);
    }

    @Override
    public MemberBenefit deactivateMemberBenefit(Long id) {
        MemberBenefit benefit = memberBenefitRepository.findOne(id);
        if (benefit == null) {
            throw new RuntimeException("会员权益不存在");
        }

        benefit.setStatus(0);
        benefit.setUpdateTime(new Date());

        return memberBenefitRepository.save(benefit);
    }

    @Override
    public void deleteMemberBenefit(Long id) {
        MemberBenefit benefit = memberBenefitRepository.findOne(id);
        if (benefit == null) {
            throw new RuntimeException("会员权益不存在");
        }

        memberBenefitRepository.delete(id);
    }

    @Override
    public List<MemberBenefit> findAllMemberBenefits() {
        return memberBenefitRepository.findAll();
    }

    @Override
    public List<MemberBenefit> findMemberBenefitsByStatus(Integer status) {
        return memberBenefitRepository.findByStatus(status);
    }
}
