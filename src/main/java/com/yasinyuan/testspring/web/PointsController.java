package com.yasinyuan.testspring.web;

import com.yasinyuan.testspring.model.PointsAccount;
import com.yasinyuan.testspring.model.PointsRecord;
import com.yasinyuan.testspring.service.PointsService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 积分控制器
 * @author yinyuan
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/points")
public class PointsController extends AbstractController {
    @Resource
    private PointsService pointsService;

    /**
     * 根据会员ID查询积分账户信息
     */
    @GetMapping("/account/{memberId}")
    public R getPointsAccount(@PathVariable Long memberId) {
        PointsAccount account = pointsService.findByMemberId(memberId);
        if (account == null) {
            return R.error("积分账户不存在");
        }
        return R.ok().put("account", account);
    }

    /**
     * 增加积分
     */
    @PostMapping("/add")
    public R addPoints(@RequestParam Long memberId, @RequestParam Integer points,
                      @RequestParam Integer businessType, @RequestParam(required = false) Long businessId,
                      @RequestParam(required = false) String remark) {
        try {
            pointsService.addPoints(memberId, points, businessType, businessId, remark);
            return R.ok("积分增加成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 扣除积分
     */
    @PostMapping("/deduct")
    public R deductPoints(@RequestParam Long memberId, @RequestParam Integer points,
                         @RequestParam Integer businessType, @RequestParam(required = false) Long businessId,
                         @RequestParam(required = false) String remark) {
        try {
            pointsService.deductPoints(memberId, points, businessType, businessId, remark);
            return R.ok("积分扣除成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 冻结积分
     */
    @PostMapping("/freeze")
    public R freezePoints(@RequestParam Long memberId, @RequestParam Integer points,
                         @RequestParam Integer businessType, @RequestParam(required = false) Long businessId,
                         @RequestParam(required = false) String remark) {
        try {
            pointsService.freezePoints(memberId, points, businessType, businessId, remark);
            return R.ok("积分冻结成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 解冻积分
     */
    @PostMapping("/unfreeze")
    public R unfreezePoints(@RequestParam Long memberId, @RequestParam Integer points,
                           @RequestParam Integer businessType, @RequestParam(required = false) Long businessId,
                           @RequestParam(required = false) String remark) {
        try {
            pointsService.unfreezePoints(memberId, points, businessType, businessId, remark);
            return R.ok("积分解冻成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 根据会员ID查询积分记录
     */
    @GetMapping("/records/{memberId}")
    public R getPointsRecords(@PathVariable Long memberId) {
        List<PointsRecord> records = pointsService.getPointsRecordsByMemberId(memberId);
        return R.ok().put("records", records);
    }

    /**
     * 获取积分统计信息
     */
    @GetMapping("/statistics")
    public R getPointsStatistics() {
        PointsService.PointsStatistics statistics = pointsService.getPointsStatistics();
        return R.ok().put("statistics", statistics);
    }
}