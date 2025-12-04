package com.yasinyuan.testspring.web;

import com.yasinyuan.testspring.model.Member;
import com.yasinyuan.testspring.service.MemberService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会员控制器
 * @author yinyuan
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/member")
public class MemberController extends AbstractController {
    @Resource
    private MemberService memberService;

    /**
     * 根据用户ID查询会员信息
     */
    @GetMapping("/info/{userId}")
    public R getMemberInfo(@PathVariable Long userId) {
        Member member = memberService.findByUserId(userId);
        if (member == null) {
            return R.error("会员不存在");
        }
        return R.ok().put("member", member);
    }

    /**
     * 会员注册
     */
    @PostMapping("/register")
    public R registerMember(@RequestParam Long userId) {
        try {
            memberService.registerMember(userId);
            return R.ok("会员注册成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 更新会员成长值
     */
    @PostMapping("/growth-value/update")
    public R updateGrowthValue(@RequestParam Long memberId, @RequestParam Integer growthValue,
                              @RequestParam Integer businessType, @RequestParam(required = false) Long businessId,
                              @RequestParam(required = false) String remark) {
        try {
            memberService.updateGrowthValue(memberId, growthValue, businessType, businessId, remark);
            return R.ok("成长值更新成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 获取会员统计信息
     */
    @GetMapping("/statistics")
    public R getMemberStatistics() {
        MemberService.MemberStatistics statistics = memberService.getMemberStatistics();
        return R.ok().put("statistics", statistics);
    }

    /**
     * 获取会员活跃度热力图数据
     */
    @GetMapping("/activity/heatmap")
    public R getMemberActivityHeatmap() {
        List<MemberService.MemberActivityHeatmapData> data = memberService.getMemberActivityHeatmap();
        return R.ok().put("data", data);
    }
}