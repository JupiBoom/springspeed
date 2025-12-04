package com.yasinyuan.testspring.web;

import com.yasinyuan.testspring.model.PointsGainRule;
import com.yasinyuan.testspring.service.PointsGainRuleService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 积分获取规则控制器
 * @author yinyuan
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/points/gain-rule")
public class PointsGainRuleController extends AbstractController {
    @Resource
    private PointsGainRuleService pointsGainRuleService;

    /**
     * 根据业务类型查询启用的积分获取规则
     */
    @GetMapping("/enabled/{businessType}")
    public R getEnabledRuleByBusinessType(@PathVariable Integer businessType) {
        PointsGainRule rule = pointsGainRuleService.findEnabledRuleByBusinessType(businessType);
        if (rule == null) {
            return R.error("未找到对应的积分获取规则");
        }
        return R.ok().put("rule", rule);
    }

    /**
     * 初始化积分获取规则
     */
    @PostMapping("/init")
    public R initPointsGainRules() {
        try {
            pointsGainRuleService.initPointsGainRules();
            return R.ok("积分获取规则初始化成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 检查每日积分获取限制
     */
    @GetMapping("/check-daily-limit")
    public R checkDailyLimit(@RequestParam Long memberId, @RequestParam Integer businessType) {
        try {
            boolean withinLimit = pointsGainRuleService.checkDailyLimit(memberId, businessType);
            return R.ok().put("withinLimit", withinLimit);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 检查每月积分获取限制
     */
    @GetMapping("/check-monthly-limit")
    public R checkMonthlyLimit(@RequestParam Long memberId, @RequestParam Integer businessType) {
        try {
            boolean withinLimit = pointsGainRuleService.checkMonthlyLimit(memberId, businessType);
            return R.ok().put("withinLimit", withinLimit);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
}