package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.dao.MemberLevelMapper;
import com.yasinyuan.testspring.model.MemberLevel;
import com.yasinyuan.testspring.service.MemberLevelService;
import com.yasinyuan.testspring.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会员等级服务实现类
 * @author yinyuan
 * @since 2024-01-01
 */
@Service
public class MemberLevelServiceImpl extends AbstractService<MemberLevel> implements MemberLevelService {
    @Resource
    private MemberLevelMapper memberLevelMapper;

    @Override
    public List<MemberLevel> findEnabledLevels() {
        return memberLevelMapper.findEnabledLevels();
    }

    @Override
    public MemberLevel findByGrowthValue(Integer growthValue) {
        List<MemberLevel> levels = memberLevelMapper.findEnabledLevels();
        
        // 按成长值下限降序排序，找到第一个成长值下限小于等于当前成长值的等级
        return levels.stream()
            .sorted((l1, l2) -> l2.getGrowthValueMin().compareTo(l1.getGrowthValueMin()))
            .filter(level -> level.getGrowthValueMin() <= growthValue)
            .findFirst()
            .orElse(null);
    }

    @Override
    @Transactional
    public void initMemberLevels() {
        // 检查是否已初始化
        List<MemberLevel> existingLevels = memberLevelMapper.selectAll();
        if (!existingLevels.isEmpty()) {
            return;
        }
        
        Date now = new Date();
        
        // 普通会员
        MemberLevel normalLevel = new MemberLevel();
        normalLevel.setLevelName("普通会员");
        normalLevel.setLevelCode("NORMAL");
        normalLevel.setGrowthValueMin(0);
        normalLevel.setGrowthValueMax(999);
        normalLevel.setDiscount(1.0);
        normalLevel.setFreightCouponCount(0);
        normalLevel.setPriorityShipping(false);
        normalLevel.setExclusiveService(false);
        normalLevel.setBirthdayBonusPoints(0);
        normalLevel.setStatus(1);
        normalLevel.setCreateTime(now);
        normalLevel.setUpdateTime(now);
        memberLevelMapper.insertSelective(normalLevel);
        
        // 白银会员
        MemberLevel silverLevel = new MemberLevel();
        silverLevel.setLevelName("白银会员");
        silverLevel.setLevelCode("SILVER");
        silverLevel.setGrowthValueMin(1000);
        silverLevel.setGrowthValueMax(4999);
        silverLevel.setDiscount(0.95);
        silverLevel.setFreightCouponCount(2);
        silverLevel.setPriorityShipping(false);
        silverLevel.setExclusiveService(false);
        silverLevel.setBirthdayBonusPoints(100);
        silverLevel.setStatus(1);
        silverLevel.setCreateTime(now);
        silverLevel.setUpdateTime(now);
        memberLevelMapper.insertSelective(silverLevel);
        
        // 黄金会员
        MemberLevel goldLevel = new MemberLevel();
        goldLevel.setLevelName("黄金会员");
        goldLevel.setLevelCode("GOLD");
        goldLevel.setGrowthValueMin(5000);
        goldLevel.setGrowthValueMax(19999);
        goldLevel.setDiscount(0.9);
        goldLevel.setFreightCouponCount(5);
        goldLevel.setPriorityShipping(true);
        goldLevel.setExclusiveService(false);
        goldLevel.setBirthdayBonusPoints(200);
        goldLevel.setStatus(1);
        goldLevel.setCreateTime(now);
        goldLevel.setUpdateTime(now);
        memberLevelMapper.insertSelective(goldLevel);
        
        // 铂金会员
        MemberLevel platinumLevel = new MemberLevel();
        platinumLevel.setLevelName("铂金会员");
        platinumLevel.setLevelCode("PLATINUM");
        platinumLevel.setGrowthValueMin(20000);
        platinumLevel.setGrowthValueMax(Integer.MAX_VALUE);
        platinumLevel.setDiscount(0.85);
        platinumLevel.setFreightCouponCount(10);
        platinumLevel.setPriorityShipping(true);
        platinumLevel.setExclusiveService(true);
        platinumLevel.setBirthdayBonusPoints(500);
        platinumLevel.setStatus(1);
        platinumLevel.setCreateTime(now);
        platinumLevel.setUpdateTime(now);
        memberLevelMapper.insertSelective(platinumLevel);
    }
}