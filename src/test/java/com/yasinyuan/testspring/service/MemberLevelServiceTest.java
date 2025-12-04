package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.BaseTest;
import com.yasinyuan.testspring.model.MemberLevel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 会员等级服务单元测试类
 * @author yinyuan
 * @since 2024-01-01
 */
public class MemberLevelServiceTest extends BaseTest {
    @Autowired
    private MemberLevelService memberLevelService;

    @Test
    public void testFindEnabledLevels() {
        // 测试查询所有启用的会员等级
        List<MemberLevel> levels = memberLevelService.findEnabledLevels();
        assertNotNull(levels);
        assertTrue(levels.size() > 0);
    }

    @Test
    public void testFindByGrowthValue() {
        // 测试根据成长值查询对应的会员等级
        MemberLevel level = memberLevelService.findByGrowthValue(100);
        assertNotNull(level);
        assertTrue(level.getMinGrowthValue() <= 100);
        assertTrue(level.getMaxGrowthValue() >= 100);
    }

    @Test
    public void testInitMemberLevels() {
        // 测试初始化会员等级
        memberLevelService.initMemberLevels();
        List<MemberLevel> levels = memberLevelService.findEnabledLevels();
        assertNotNull(levels);
        assertTrue(levels.size() > 0);
    }
}