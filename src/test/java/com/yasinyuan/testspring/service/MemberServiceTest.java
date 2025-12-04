package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.BaseTest;
import com.yasinyuan.testspring.model.Member;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 会员服务单元测试类
 * @author yinyuan
 * @since 2024-01-01
 */
public class MemberServiceTest extends BaseTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void testFindByUserId() {
        // 测试根据用户ID查询会员信息
        Member member = memberService.findByUserId(1L);
        assertNotNull(member);
        assertEquals(1L, member.getUserId().longValue());
    }

    @Test
    public void testRegisterMember() {
        // 测试会员注册
        Long userId = 2L;
        memberService.registerMember(userId);
        Member member = memberService.findByUserId(userId);
        assertNotNull(member);
        assertEquals(userId, member.getUserId());
    }

    @Test
    public void testUpdateGrowthValue() {
        // 测试更新会员成长值
        Long memberId = 1L;
        Integer growthValue = 100;
        Integer businessType = 1;
        memberService.updateGrowthValue(memberId, growthValue, businessType, null, "测试更新成长值");
        Member member = memberService.findByUserId(1L);
        assertNotNull(member);
        assertEquals(growthValue.intValue(), member.getGrowthValue().intValue());
    }

    @Test
    public void testCheckAndUpdateMemberLevel() {
        // 测试检查并更新会员等级
        Long memberId = 1L;
        memberService.checkAndUpdateMemberLevel(memberId);
        Member member = memberService.findByUserId(1L);
        assertNotNull(member);
        assertNotNull(member.getLevelId());
    }

    @Test
    public void testProcessMemberBirthdayBenefits() {
        // 测试处理会员生日权益
        memberService.processMemberBirthdayBenefits();
        // 这里可以添加断言来验证生日权益是否发放成功
    }

    @Test
    public void testGetMemberStatistics() {
        // 测试获取会员统计信息
        MemberService.MemberStatistics statistics = memberService.getMemberStatistics();
        assertNotNull(statistics);
        assertNotNull(statistics.getTotalMembers());
        assertNotNull(statistics.getNewMembersToday());
        assertNotNull(statistics.getActiveMembersToday());
        assertNotNull(statistics.getMemberLevelDistribution());
    }

    @Test
    public void testGetMemberActivityHeatmap() {
        // 测试获取会员活跃度热力图数据
        assertNotNull(memberService.getMemberActivityHeatmap());
    }
}