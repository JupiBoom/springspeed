package com.yasinyuan.testspring.controller.benefit;

import com.yasinyuan.testspring.service.benefit.BenefitDistributionService;
import com.yasinyuan.testspring.entity.benefit.BenefitDistributionRecord;
import com.yasinyuan.testspring.tools.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/benefit-distributions")
public class BenefitDistributionController {

    @Autowired
    private BenefitDistributionService benefitDistributionService;

    /**
     * 发放权益给单个会员
     */
    @PostMapping("/single")
    public R distributeToMember(@RequestParam Long memberId, @RequestParam Long benefitId) {
        BenefitDistributionRecord record = benefitDistributionService.distributeToMember(memberId, benefitId);
        return R.ok().put("data", record);
    }

    /**
     * 批量发放权益给多个会员
     */
    @PostMapping("/batch")
    public R distributeToMembers(@RequestParam List<Long> memberIds, @RequestParam Long benefitId) {
        List<BenefitDistributionRecord> records = benefitDistributionService.distributeToMembers(memberIds, benefitId);
        return R.ok().put("data", records);
    }

    /**
     * 发放生日特权给会员
     */
    @PostMapping("/birthday")
    public R distributeBirthdayPrivilege(@RequestParam Long memberId) {
        BenefitDistributionRecord record = benefitDistributionService.distributeBirthdayPrivilege(memberId);
        return R.ok().put("data", record);
    }

    /**
     * 发放升级权益给会员
     */
    @PostMapping("/level-up")
    public R distributeLevelUpBenefits(@RequestParam Long memberId, @RequestParam int newLevel) {
        List<BenefitDistributionRecord> records = benefitDistributionService.distributeLevelUpBenefits(memberId, newLevel);
        return R.ok().put("data", records);
    }

    /**
     * 根据ID查询权益发放记录
     */
    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        BenefitDistributionRecord record = benefitDistributionService.findById(id);
        return R.ok().put("data", record);
    }

    /**
     * 根据会员ID查询权益发放记录
     */
    @GetMapping("/by-member/{memberId}")
    public R findByMemberId(@PathVariable Long memberId) {
        List<BenefitDistributionRecord> records = benefitDistributionService.findByMemberId(memberId);
        return R.ok().put("data", records);
    }

    /**
     * 根据权益ID查询发放记录
     */
    @GetMapping("/by-benefit/{benefitId}")
    public R findByBenefitId(@PathVariable Long benefitId) {
        List<BenefitDistributionRecord> records = benefitDistributionService.findByBenefitId(benefitId);
        return R.ok().put("data", records);
    }

    /**
     * 查询所有权益发放记录（分页）
     */
    @GetMapping
    public R findAll(@RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "10") int size) {
        List<BenefitDistributionRecord> records = benefitDistributionService.findAll(page, size);
        return R.ok().put("data", records);
    }

    /**
     * 处理权益过期
     */
    @PostMapping("/expire")
    public R processExpiredBenefits() {
        benefitDistributionService.processExpiredBenefits();
        return R.ok();
    }

    /**
     * 检查权益发放频率（防刷）
     */
    @GetMapping("/check-frequency")
    public R checkDistributionFrequency(@RequestParam Long memberId, @RequestParam Long benefitId) {
        boolean canDistribute = benefitDistributionService.checkDistributionFrequency(memberId, benefitId);
        return R.ok().put("canDistribute", canDistribute);
    }
}