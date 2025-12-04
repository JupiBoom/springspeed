package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.BaseTest;
import com.yasinyuan.testspring.model.MemberBenefitRecord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 会员权益服务单元测试类
 * @author yinyuan
 * @since 2024-01-01
 */
public class MemberBenefitServiceTest extends BaseTest {
    @Autowired
    private MemberBenefitService memberBenefitService;

    @Test
    public void testFindByMemberId() {
        // 测试根据会员ID查询权益记录
        List<MemberBenefitRecord> records = memberBenefitService.findByMemberId(1L);
        assertNotNull(records);
    }

    @Test
    public void testFindByMemberIdAndBenefitType() {
        // 测试根据会员ID和权益类型查询权益记录
        List<MemberBenefitRecord> records = memberBenefitService.findByMemberIdAndBenefitType(1L, 1);
        assertNotNull(records);
    }

    @Test
    public void testFindAvailableBenefits() {
        // 测试查询未使用且未过期的权益记录
        List<MemberBenefitRecord> records = memberBenefitService.findAvailableBenefits(1L, 1);
        assertNotNull(records);
    }

    @Test
    public void testIssueFreightCoupons() {
        // 测试发放运费券
        Long memberId = 1L;
        Integer quantity = 5;
        memberBenefitService.issueFreightCoupons(memberId, quantity, "测试发放运费券");
        List<MemberBenefitRecord> records = memberBenefitService.findByMemberIdAndBenefitType(memberId, 1);
        assertNotNull(records);
        assertTrue(records.size() >= quantity);
    }

    @Test
    public void testIssueBirthdayPoints() {
        // 测试发放生日积分
        Long memberId = 1L;
        memberBenefitService.issueBirthdayPoints(memberId, "测试发放生日积分");
        List<MemberBenefitRecord> records = memberBenefitService.findByMemberIdAndBenefitType(memberId, 2);
        assertNotNull(records);
    }

    @Test
    public void testAssignExclusiveService() {
        // 测试分配专属客服
        Long memberId = 1L;
        Long serviceUserId = 2L;
        memberBenefitService.assignExclusiveService(memberId, serviceUserId, "测试分配专属客服");
        List<MemberBenefitRecord> records = memberBenefitService.findByMemberIdAndBenefitType(memberId, 3);
        assertNotNull(records);
    }

    @Test
    public void testUseBenefit() {
        // 测试使用权益
        Long memberId = 1L;
        Integer quantity = 1;
        memberBenefitService.issueFreightCoupons(memberId, quantity, "测试使用权益前置操作");
        List<MemberBenefitRecord> records = memberBenefitService.findAvailableBenefits(memberId, 1);
        assertNotNull(records);
        assertTrue(records.size() > 0);
        Long recordId = records.get(0).getId();
        memberBenefitService.useBenefit(recordId);
        MemberBenefitRecord record = memberBenefitService.getById(recordId);
        assertNotNull(record);
        assertEquals(2, record.getStatus().intValue()); // 已使用状态
    }
}