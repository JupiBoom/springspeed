package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.dao.MemberMapper;
import com.yasinyuan.testspring.model.Member;
import com.yasinyuan.testspring.service.MemberService;
import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.service.MemberLevelService;
import com.yasinyuan.testspring.service.GrowthValueRecordService;
import com.yasinyuan.testspring.tools.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会员服务实现类
 * @author yinyuan
 * @since 2024-01-01
 */
@Service
public class MemberServiceImpl extends AbstractService<Member> implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    
    @Resource
    private MemberLevelService memberLevelService;
    
    @Resource
    private GrowthValueRecordService growthValueRecordService;

    @Override
    public Member findByUserId(Long userId) {
        return memberMapper.findByUserId(userId);
    }

    @Override
    @Transactional
    public void registerMember(Long userId) {
        // 检查是否已存在会员
        Member existingMember = memberMapper.findByUserId(userId);
        if (existingMember != null) {
            throw new RuntimeException("该用户已注册为会员");
        }
        
        // 创建新会员
        Member member = new Member();
        member.setUserId(userId);
        
        // 默认注册为普通会员
        List<MemberLevel> levels = memberLevelService.findEnabledLevels();
        MemberLevel defaultLevel = levels.stream()
            .filter(level -> "NORMAL".equals(level.getLevelCode()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("未找到默认会员等级"));
        
        member.setCurrentLevelId(defaultLevel.getId());
        member.setCurrentGrowthValue(0);
        member.setTotalGrowthValue(0);
        
        // 设置会员有效期（默认为1年）
        Date now = new Date();
        member.setValidityStartDate(now);
        member.setValidityEndDate(DateUtils.addYears(now, 1));
        
        member.setStatus(1);
        member.setRegisterTime(now);
        member.setCreateTime(now);
        member.setUpdateTime(now);
        
        memberMapper.insertSelective(member);
    }

    @Override
    @Transactional
    public void updateGrowthValue(Long memberId, Integer growthValue, Integer businessType, Long businessId, String remark) {
        Member member = memberMapper.selectByPrimaryKey(memberId);
        if (member == null) {
            throw new RuntimeException("会员不存在");
        }
        
        Integer beforeValue = member.getCurrentGrowthValue();
        Integer afterValue = beforeValue + growthValue;
        
        // 更新会员成长值
        member.setCurrentGrowthValue(afterValue);
        member.setTotalGrowthValue(member.getTotalGrowthValue() + Math.abs(growthValue));
        member.setUpdateTime(new Date());
        memberMapper.updateByPrimaryKeySelective(member);
        
        // 记录成长值变化
        growthValueRecordService.addGrowthValueRecord(memberId, growthValue > 0 ? 1 : 2, 
            Math.abs(growthValue), beforeValue, afterValue, businessType, businessId, remark);
        
        // 检查并更新会员等级
        checkAndUpdateMemberLevel(memberId);
    }

    @Override
    @Transactional
    public void checkAndUpdateMemberLevel(Long memberId) {
        Member member = memberMapper.selectByPrimaryKey(memberId);
        if (member == null) {
            throw new RuntimeException("会员不存在");
        }
        
        // 根据当前成长值查询对应的会员等级
        MemberLevel newLevel = memberLevelService.findByGrowthValue(member.getCurrentGrowthValue());
        if (newLevel == null) {
            throw new RuntimeException("未找到对应的会员等级");
        }
        
        // 如果等级发生变化
        if (!newLevel.getId().equals(member.getCurrentLevelId())) {
            Long oldLevelId = member.getCurrentLevelId();
            member.setCurrentLevelId(newLevel.getId());
            member.setUpdateTime(new Date());
            memberMapper.updateByPrimaryKeySelective(member);
            
            // 延长会员有效期（等级提升时延长1年）
            if (newLevel.getGrowthValueMin() > memberLevelService.selectByPrimaryKey(oldLevelId).getGrowthValueMin()) {
                member.setValidityEndDate(DateUtils.addYears(member.getValidityEndDate(), 1));
                memberMapper.updateByPrimaryKeySelective(member);
            }
            
            // TODO: 发送会员等级变化通知
        }
    }

    @Override
    @Transactional
    public void processMemberBirthdayBenefits() {
        // TODO: 查询今天过生日的会员
        List<Member> birthdayMembers = memberMapper.selectAll().stream()
            .filter(member -> member.getBirthday() != null && DateUtils.isSameDayOfYear(member.getBirthday(), new Date()))
            .collect(Collectors.toList());
        
        // 为过生日的会员发放生日权益
        for (Member member : birthdayMembers) {
            // TODO: 发放生日积分
            // TODO: 发放生日礼品券
            // TODO: 发送生日祝福通知
        }
    }

    @Override
    public MemberStatistics getMemberStatistics() {
        MemberStatistics statistics = new MemberStatistics();
        
        // 总会员数
        statistics.setTotalMembers(memberMapper.selectCount(null));
        
        // 今日新增会员数
        Date today = new Date();
        Date startOfDay = DateUtils.getStartOfDay(today);
        Date endOfDay = DateUtils.getEndOfDay(today);
        
        // TODO: 实现今日新增会员数查询
        statistics.setNewMembersToday(0);
        
        // 本月活跃会员数
        Date startOfMonth = DateUtils.getStartOfMonth(today);
        Date endOfMonth = DateUtils.getEndOfMonth(today);
        
        // TODO: 实现本月活跃会员数查询
        statistics.setActiveMembersThisMonth(0);
        
        // 平均成长值
        // TODO: 实现平均成长值查询
        statistics.setAverageGrowthValue(0.0);
        
        return statistics;
    }

    @Override
    public List<MemberActivityHeatmapData> getMemberActivityHeatmap() {
        // TODO: 实现会员活跃度热力图数据查询
        return null;
    }
}