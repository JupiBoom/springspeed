package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.BaseTest;
import com.yasinyuan.testspring.model.PointsAccount;
import com.yasinyuan.testspring.model.PointsRecord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 积分服务单元测试类
 * @author yinyuan
 * @since 2024-01-01
 */
public class PointsServiceTest extends BaseTest {
    @Autowired
    private PointsService pointsService;

    @Test
    public void testFindByMemberId() {
        // 测试根据会员ID查询积分账户信息
        PointsAccount account = pointsService.findByMemberId(1L);
        assertNotNull(account);
        assertEquals(1L, account.getMemberId().longValue());
    }

    @Test
    public void testAddPoints() {
        // 测试增加积分
        Long memberId = 1L;
        Integer points = 100;
        Integer businessType = 1;
        pointsService.addPoints(memberId, points, businessType, null, "测试增加积分");
        PointsAccount account = pointsService.findByMemberId(memberId);
        assertNotNull(account);
        assertTrue(account.getAvailablePoints() >= points);
    }

    @Test
    public void testDeductPoints() {
        // 测试扣除积分
        Long memberId = 1L;
        Integer points = 50;
        Integer businessType = 7;
        pointsService.addPoints(memberId, 100, 1, null, "测试扣除积分前置操作");
        pointsService.deductPoints(memberId, points, businessType, null, "测试扣除积分");
        PointsAccount account = pointsService.findByMemberId(memberId);
        assertNotNull(account);
        assertTrue(account.getAvailablePoints() >= 0);
    }

    @Test
    public void testFreezePoints() {
        // 测试冻结积分
        Long memberId = 1L;
        Integer points = 50;
        Integer businessType = 7;
        pointsService.addPoints(memberId, 100, 1, null, "测试冻结积分前置操作");
        pointsService.freezePoints(memberId, points, businessType, null, "测试冻结积分");
        PointsAccount account = pointsService.findByMemberId(memberId);
        assertNotNull(account);
        assertEquals(points.intValue(), account.getFrozenPoints().intValue());
    }

    @Test
    public void testUnfreezePoints() {
        // 测试解冻积分
        Long memberId = 1L;
        Integer points = 50;
        Integer businessType = 7;
        pointsService.addPoints(memberId, 100, 1, null, "测试解冻积分前置操作");
        pointsService.freezePoints(memberId, points, businessType, null, "测试解冻积分前置操作");
        pointsService.unfreezePoints(memberId, points, businessType, null, "测试解冻积分");
        PointsAccount account = pointsService.findByMemberId(memberId);
        assertNotNull(account);
        assertEquals(0, account.getFrozenPoints().intValue());
    }

    @Test
    public void testProcessPointsExpiration() {
        // 测试处理积分过期
        pointsService.processPointsExpiration();
        // 这里可以添加断言来验证积分过期是否处理成功
    }

    @Test
    public void testGetPointsRecordsByMemberId() {
        // 测试根据会员ID查询积分记录
        List<PointsRecord> records = pointsService.getPointsRecordsByMemberId(1L);
        assertNotNull(records);
    }

    @Test
    public void testGetPointsStatistics() {
        // 测试获取积分统计信息
        PointsService.PointsStatistics statistics = pointsService.getPointsStatistics();
        assertNotNull(statistics);
        assertNotNull(statistics.getTotalPointsIssued());
        assertNotNull(statistics.getTotalPointsUsed());
        assertNotNull(statistics.getTotalPointsExpired());
        assertNotNull(statistics.getPointsBalance());
    }
}