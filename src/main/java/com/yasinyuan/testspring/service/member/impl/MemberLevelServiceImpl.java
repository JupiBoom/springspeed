package com.yasinyuan.testspring.service.member.impl;

import com.yasinyuan.testspring.dao.member.MemberLevelRepository;
import com.yasinyuan.testspring.entity.member.MemberLevel;
import com.yasinyuan.testspring.service.member.MemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MemberLevelServiceImpl implements MemberLevelService {

    @Autowired
    private MemberLevelRepository memberLevelRepository;

    @Override
    public MemberLevel createMemberLevel(MemberLevel level) {
        // 检查等级值是否已存在
        if (memberLevelRepository.findByLevel(level.getLevel()) != null) {
            throw new RuntimeException("等级值已存在");
        }

        // 检查成长值是否已被占用
        if (memberLevelRepository.findByMinGrowthValue(level.getMinGrowthValue()) != null) {
            throw new RuntimeException("成长值范围已被占用");
        }
        if (level.getMaxGrowthValue() != null) {
            if (memberLevelRepository.findByMaxGrowthValueLessThanEqual(level.getMaxGrowthValue()) != null) {
                throw new RuntimeException("成长值范围已被占用");
            }
        }

        // 设置创建时间和状态
        level.setCreateTime(new Date());
        level.setStatus(1); // 启用状态

        return memberLevelRepository.save(level);
    }

    @Override
    public MemberLevel updateMemberLevel(MemberLevel level) {
        MemberLevel existingLevel = memberLevelRepository.findOne(level.getId());
        if (existingLevel == null) {
            throw new RuntimeException("会员等级不存在");
        }

        // 检查等级值是否已存在（排除当前等级）
        if (!existingLevel.getLevel().equals(level.getLevel())) {
            if (memberLevelRepository.findByLevel(level.getLevel()) != null) {
                throw new RuntimeException("等级值已存在");
            }
        }

        // 检查成长值是否已被占用（排除当前等级）
        if (!existingLevel.getMinGrowthValue().equals(level.getMinGrowthValue())) {
            if (memberLevelRepository.findByMinGrowthValue(level.getMinGrowthValue()) != null) {
                throw new RuntimeException("成长值范围已被占用");
            }
        }
        if (level.getMaxGrowthValue() != null) {
            if (!level.getMaxGrowthValue().equals(existingLevel.getMaxGrowthValue())) {
                if (memberLevelRepository.findByMaxGrowthValueLessThanEqual(level.getMaxGrowthValue()) != null) {
                    throw new RuntimeException("成长值范围已被占用");
                }
            }
        }

        // 更新会员等级信息
        existingLevel.setName(level.getName());
        existingLevel.setDescription(level.getDescription());
        existingLevel.setLevel(level.getLevel());
        existingLevel.setMinGrowthValue(level.getMinGrowthValue());
        existingLevel.setMaxGrowthValue(level.getMaxGrowthValue());
        existingLevel.setUpdateTime(new Date());

        return memberLevelRepository.save(existingLevel);
    }

    @Override
    public MemberLevel findMemberLevelById(Long id) {
        return memberLevelRepository.findOne(id);
    }

    @Override
    public MemberLevel findMemberLevelByLevel(Integer level) {
        return memberLevelRepository.findByLevel(level);
    }

    @Override
    public MemberLevel findLevelByGrowthValue(Integer growthValue) {
        return memberLevelRepository.findLevelByGrowthValue(growthValue);
    }

    @Override
    public List<MemberLevel> findAllMemberLevels() {
        return memberLevelRepository.findAll();
    }

    @Override
    public List<MemberLevel> findMemberLevelsByStatus(Integer status) {
        return memberLevelRepository.findByStatus(status);
    }

    @Override
    public List<MemberLevel> findMemberLevelsOrderByLevel() {
        return memberLevelRepository.findByOrderByLevelAsc();
    }

    @Override
    public List<MemberLevel> findMemberLevelsOrderByMinGrowthValue() {
        return memberLevelRepository.findByOrderByMinGrowthValueAsc();
    }

    @Override
    public MemberLevel enableMemberLevel(Long id) {
        MemberLevel level = memberLevelRepository.findOne(id);
        if (level == null) {
            throw new RuntimeException("会员等级不存在");
        }

        level.setStatus(1);
        level.setUpdateTime(new Date());

        return memberLevelRepository.save(level);
    }

    @Override
    public MemberLevel disableMemberLevel(Long id) {
        MemberLevel level = memberLevelRepository.findOne(id);
        if (level == null) {
            throw new RuntimeException("会员等级不存在");
        }

        // 不能禁用默认等级（普通会员）
        if (level.getLevel().equals(1)) {
            throw new RuntimeException("不能禁用默认会员等级");
        }

        level.setStatus(0);
        level.setUpdateTime(new Date());

        return memberLevelRepository.save(level);
    }

    @Override
    public void deleteMemberLevel(Long id) {
        MemberLevel level = memberLevelRepository.findOne(id);
        if (level == null) {
            throw new RuntimeException("会员等级不存在");
        }

        // 不能删除默认等级（普通会员）
        if (level.getLevel().equals(1)) {
            throw new RuntimeException("不能删除默认会员等级");
        }

        memberLevelRepository.delete(id);
    }
}
