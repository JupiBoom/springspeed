package com.yasinyuan.testspring.web;

import com.yasinyuan.testspring.model.MemberBenefitRecord;
import com.yasinyuan.testspring.service.MemberBenefitService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会员权益控制器
 * @author yinyuan
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/member/benefit")
public class MemberBenefitController extends AbstractController {
    @Resource
    private MemberBenefitService memberBenefitService;

    /**
     * 根据会员ID查询权益记录
     */
    @GetMapping("/records/{memberId}")
    public R getBenefitRecords(@PathVariable Long memberId) {
        List<MemberBenefitRecord> records = memberBenefitService.findByMemberId(memberId);
        return R.ok().put("records", records);
    }

    /**
     * 根据会员ID和权益类型查询权益记录
     */
    @GetMapping("/records/{memberId}/{benefitType}")
    public R getBenefitRecordsByType(@PathVariable Long memberId, @PathVariable Integer benefitType) {
        List<MemberBenefitRecord> records = memberBenefitService.findByMemberIdAndBenefitType(memberId, benefitType);
        return R.ok().put("records", records);
    }

    /**
     * 查询未使用且未过期的权益记录
     */
    @GetMapping("/available/{memberId}/{benefitType}")
    public R getAvailableBenefits(@PathVariable Long memberId, @PathVariable Integer benefitType) {
        List<MemberBenefitRecord> records = memberBenefitService.findAvailableBenefits(memberId, benefitType);
        return R.ok().put("records", records);
    }

    /**
     * 发放运费券
     */
    @PostMapping("/freight-coupon/issue")
    public R issueFreightCoupons(@RequestParam Long memberId, @RequestParam Integer quantity,
                                 @RequestParam(required = false) String reason) {
        try {
            memberBenefitService.issueFreightCoupons(memberId, quantity, reason);
            return R.ok("运费券发放成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 发放生日积分
     */
    @PostMapping("/birthday-points/issue")
    public R issueBirthdayPoints(@RequestParam Long memberId, @RequestParam(required = false) String reason) {
        try {
            memberBenefitService.issueBirthdayPoints(memberId, reason);
            return R.ok("生日积分发放成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 分配专属客服
     */
    @PostMapping("/exclusive-service/assign")
    public R assignExclusiveService(@RequestParam Long memberId, @RequestParam Long serviceUserId,
                                    @RequestParam(required = false) String reason) {
        try {
            memberBenefitService.assignExclusiveService(memberId, serviceUserId, reason);
            return R.ok("专属客服分配成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 使用权益
     */
    @PostMapping("/use/{recordId}")
    public R useBenefit(@PathVariable Long recordId) {
        try {
            memberBenefitService.useBenefit(recordId);
            return R.ok("权益使用成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
}