package com.yasinyuan.testspring.benefit.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasinyuan.testspring.benefit.entity.BenefitIssueRecord;
import com.yasinyuan.testspring.benefit.service.BenefitIssueRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 权益发放记录Controller
 */
@RestController
@RequestMapping("/benefit-issue-record")
public class BenefitIssueRecordController {

    @Autowired
    private BenefitIssueRecordService benefitIssueRecordService;

    /**
     * 分页查询会员权益发放记录
     */
    @GetMapping("/member/{memberId}")
    public IPage<BenefitIssueRecord> getByMemberId(@PathVariable Long memberId,
                                                  @RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        return benefitIssueRecordService.lambdaQuery()
                .eq(BenefitIssueRecord::getMemberId, memberId)
                .page(new Page<>(page, size));
    }

    /**
     * 新增权益发放记录
     */
    @PostMapping
    public boolean save(@RequestBody BenefitIssueRecord benefitIssueRecord) {
        return benefitIssueRecordService.save(benefitIssueRecord);
    }

    /**
     * 修改权益发放记录
     */
    @PutMapping
    public boolean update(@RequestBody BenefitIssueRecord benefitIssueRecord) {
        return benefitIssueRecordService.updateById(benefitIssueRecord);
    }
}
