package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.BaseTest;
import com.yasinyuan.testspring.model.GrowthValueRecord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 成长值记录服务单元测试类
 * @author yinyuan
 * @since 2024-01-01
 */
public class GrowthValueRecordServiceTest extends BaseTest {
    @Autowired
    private GrowthValueRecordService growthValueRecordService;

    @Test
    public void testFindByMemberId() {
        // 测试根据会员ID查询成长值记录
        List<GrowthValueRecord> records = growthValueRecordService.findByMemberId(1L);
        assertNotNull(records);
    }

    @Test
    public void testFindByMemberIdAndBusinessType() {
        // 测试根据会员ID和业务类型查询成长值记录
        List<GrowthValueRecord> records = growthValueRecordService.findByMemberIdAndBusinessType(1L, 1);
        assertNotNull(records);
    }

    @Test
    public void testAddGrowthValueRecord() {
        // 测试增加成长值记录
        Long memberId = 1L;
        Integer growthValue = 100;
        Integer businessType = 1;
        growthValueRecordService.addGrowthValueRecord(memberId, growthValue, businessType, null, "测试增加成长值记录");
        List<GrowthValueRecord> records = growthValueRecordService.findByMemberIdAndBusinessType(memberId, businessType);
        assertNotNull(records);
        assertTrue(records.size() > 0);
    }
}