package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.BaseTest;
import com.yasinyuan.testspring.model.PointsGainRule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 积分获取规则服务单元测试类
 * @author yinyuan
 * @since 2024-01-01
 */
public class PointsGainRuleServiceTest extends BaseTest {
    @Autowired
    private PointsGainRuleService pointsGainRuleService;

    @Test
    public void testFindEnabledRuleByBusinessType() {
        // 测试根据业务类型查询启用的积分获取规则
        PointsGainRule rule = pointsGainRuleService.findEnabledRuleByBusinessType(1);
        assertNotNull(rule);
        assertEquals(1, rule.getBusinessType().intValue());
        assertEquals(1, rule.getStatus().intValue()); // 启用状态
    }

    @Test
    public void testInitPointsGainRules() {
        // 测试初始化积分获取规则
        pointsGainRuleService.initPointsGainRules();
        PointsGainRule rule = pointsGainRuleService.findEnabledRuleByBusinessType(1);
        assertNotNull(rule);
    }

    @Test
    public void testCheckDailyLimit() {
        // 测试检查每日积分获取限制
        Long memberId = 1L;
        Integer businessType = 1;
        boolean withinLimit = pointsGainRuleService.checkDailyLimit(memberId, businessType);
        assertTrue(withinLimit);
    }

    @Test
    public void testCheckMonthlyLimit() {
        // 测试检查每月积分获取限制
        Long memberId = 1L;
        Integer businessType = 1;
        boolean withinLimit = pointsGainRuleService.checkMonthlyLimit(memberId, businessType);
        assertTrue(withinLimit);
    }
}