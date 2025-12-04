package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.dao.MemberBenefitRecordMapper;
import com.yasinyuan.testspring.model.MemberBenefitRecord;
import com.yasinyuan.testspring.service.MemberBenefitService;
import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.service.MemberService;
import com.yasinyuan.testspring.service.MemberLevelService;
import com.yasinyuan.testspring.service.PointsService;
import com.yasinyuan.testspring.tools.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会员权益服务实现类
 * @author yinyuan
 * @since 2024-01-01
 */
@Service
public class MemberBenefitServiceImpl extends AbstractService<MemberBenefitRecord> implements MemberBenefitService {
    @Resource
    private MemberBenefitRecordMapper memberBenefitRecordMapper;
    
    @Resource
    private MemberService memberService;
    
    @Resource
    private MemberLevelService memberLevelService;
    
    @Resource
    private PointsService pointsService;
    
    // 防刷控制：记录会员每日权益领取次数
    private ConcurrentHashMap<String, Integer> benefit领取CountMap = new ConcurrentHashMap<>();
    
    // 每日权益领取上限
    private static final Integer DAILY_BENEFIT_LIMIT = 3;

    @Override
    public List<MemberBenefitRecord> findByMemberId(Long memberId) {
        return memberBenefitRecordMapper.findByMemberId(memberId);
    }

    @Override
    public List<MemberBenefitRecord> findByMemberIdAndBenefitType(Long memberId, Integer benefitType) {
        return memberBenefitRecordMapper.findByMemberIdAndBenefitType(memberId, benefitType);
    }

    @Override
    public List<MemberBenefitRecord> findAvailableBenefits(Long memberId, Integer benefitType) {
        return memberBenefitRecordMapper.findAvailableBenefits(memberId, benefitType);
    }

    @Override
    @Transactional
    public void issueFreightCoupons(Long memberId, Integer quantity, String reason) {
        // 防刷检查
        if (!checkBenefitAvailable(memberId, 1)) {
            throw new RuntimeException("今日权益领取已达上限");
        }
        
        Member member = memberService.selectByPrimaryKey(memberId);
        if (member == null) {
            throw new RuntimeException("会员不存在");
        }
        
        Date now = new Date();
        MemberBenefitRecord record = new MemberBenefitRecord();
        record.setMemberId(memberId);
        record.setBenefitType(1); // 运费券
        record.setBenefitName("运费券");
        record.setQuantity(quantity);
        record.setValidityStartDate(now);
        record.setValidityEndDate(DateUtils.addMonths(now, 1)); // 有效期1个月
        record.setStatus(0); // 未使用
        record.setReason(reason);
        record.setCreateTime(now);
        record.setUpdateTime(now);
        memberBenefitRecordMapper.insertSelective(record);
        
        // 更新防刷计数
        updateBenefit领取Count(memberId);
        
        // TODO: 发送权益发放通知
    }

    @Override
    @Transactional
    public void issueBirthdayPoints(Long memberId, String reason) {
        Member member = memberService.selectByPrimaryKey(memberId);
        if (member == null) {
            throw new RuntimeException("会员不存在");
        }
        
        // 获取会员等级对应的生日积分
        MemberLevel level = memberLevelService.selectByPrimaryKey(member.getCurrentLevelId());
        if (level == null) {
            throw new RuntimeException("会员等级不存在");
        }
        
        Integer birthdayPoints = level.getBirthdayBonusPoints();
        if (birthdayPoints > 0) {
            // 发放生日积分
            pointsService.addPoints(memberId, birthdayPoints, 2, null, reason);
            
            // 记录权益发放
            Date now = new Date();
            MemberBenefitRecord record = new MemberBenefitRecord();
            record.setMemberId(memberId);
            record.setBenefitType(2); // 生日积分
            record.setBenefitName("生日积分");
            record.setQuantity(birthdayPoints);
            record.setValidityStartDate(now);
            record.setValidityEndDate(DateUtils.addDays(now, 7)); // 有效期7天
            record.setStatus(1); // 已使用（积分直接发放）
            record.setReason(reason);
            record.setCreateTime(now);
            record.setUpdateTime(now);
            memberBenefitRecordMapper.insertSelective(record);
            
            // TODO: 发送生日祝福和积分发放通知
        }
    }

    @Override
    @Transactional
    public void assignExclusiveService(Long memberId, Long serviceUserId, String reason) {
        Member member = memberService.selectByPrimaryKey(memberId);
        if (member == null) {
            throw new RuntimeException("会员不存在");
        }
        
        // 更新会员专属客服
        member.setExclusiveServiceId(serviceUserId);
        member.setUpdateTime(new Date());
        memberService.updateByPrimaryKeySelective(member);
        
        // 记录权益发放
        Date now = new Date();
        MemberBenefitRecord record = new MemberBenefitRecord();
        record.setMemberId(memberId);
        record.setBenefitType(3); // 专属客服
        record.setBenefitName("专属客服");
        record.setQuantity(1);
        record.setValidityStartDate(now);
        record.setValidityEndDate(member.getValidityEndDate()); // 与会员有效期相同
        record.setStatus(1); // 已使用（直接分配）
        record.setReason(reason);
        record.setCreateTime(now);
        record.setUpdateTime(now);
        memberBenefitRecordMapper.insertSelective(record);
        
        // TODO: 发送专属客服分配通知
    }

    @Override
    @Transactional
    public void useBenefit(Long recordId) {
        MemberBenefitRecord record = memberBenefitRecordMapper.selectByPrimaryKey(recordId);
        if (record == null) {
            throw new RuntimeException("权益记录不存在");
        }
        
        // 检查权益是否可用
        if (record.getStatus() != 0) {
            throw new RuntimeException("权益已使用或已过期");
        }
        
        if (record.getValidityEndDate().before(new Date())) {
            throw new RuntimeException("权益已过期");
        }
        
        // 标记权益为已使用
        record.setStatus(1);
        record.setUpdateTime(new Date());
        memberBenefitRecordMapper.updateByPrimaryKeySelective(record);
        
        // TODO: 发送权益使用通知
    }

    @Override
    public boolean checkBenefitAvailable(Long memberId, Integer benefitType) {
        String key = memberId + ":" + DateUtils.formatDate(new Date(), "yyyyMMdd");
        Integer count = benefit领取CountMap.getOrDefault(key, 0);
        return count < DAILY_BENEFIT_LIMIT;
    }
    
    /**
     * 更新权益领取计数
     */
    private void updateBenefit领取Count(Long memberId) {
        String key = memberId + ":" + DateUtils.formatDate(new Date(), "yyyyMMdd");
        benefit领取CountMap.compute(key, (k, v) -> v == null ? 1 : v + 1);
        
        // TODO: 可以将计数持久化到Redis或数据库，避免内存重启丢失
    }
}