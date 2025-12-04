package com.yasinyuan.testspring.controller.analytics;

import com.yasinyuan.testspring.service.analytics.AnalyticsService;
import com.yasinyuan.testspring.entity.analytics.MemberLTV;
import com.yasinyuan.testspring.entity.analytics.PointsEfficiency;
import com.yasinyuan.testspring.entity.analytics.MemberActivityHeatmap;
import com.yasinyuan.testspring.tools.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    /**
     * 计算单个会员的生命周期价值
     */
    @GetMapping("/ltv/single/{memberId}")
    public R calculateMemberLTV(@PathVariable Long memberId) {
        MemberLTV memberLTV = analyticsService.calculateMemberLTV(memberId);
        return R.ok().put("data", memberLTV);
    }

    /**
     * 批量计算会员的生命周期价值
     */
    @PostMapping("/ltv/batch")
    public R calculateBatchMemberLTV(@RequestBody List<Long> memberIds) {
        List<MemberLTV> memberLTVs = analyticsService.calculateBatchMemberLTV(memberIds);
        return R.ok().put("data", memberLTVs);
    }

    /**
     * 定期更新所有会员的生命周期价值
     */
    @PostMapping("/ltv/update-all")
    public R updateAllMemberLTV() {
        analyticsService.periodicUpdateMemberLTV();
        return R.ok();
    }

    /**
     * 生成单日期积分使用效率报告
     */
    @GetMapping("/points-efficiency/single/{date}")
    public R generatePointsEfficiencyReport(@PathVariable String date) {
        LocalDate reportDate = LocalDate.parse(date);
        PointsEfficiency report = analyticsService.generatePointsEfficiencyReport(reportDate);
        return R.ok().put("data", report);
    }

    /**
     * 生成日期范围积分使用效率报告
     */
    @GetMapping("/points-efficiency/range")
    public R generatePointsEfficiencyReport(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<PointsEfficiency> reports = analyticsService.generatePointsEfficiencyReport(start, end);
        return R.ok().put("data", reports);
    }

    /**
     * 定期生成积分使用效率报告
     */
    @PostMapping("/points-efficiency/periodic")
    public R generatePeriodicPointsEfficiencyReport() {
        analyticsService.generatePeriodicPointsEfficiencyReport();
        return R.ok();
    }

    /**
     * 生成会员活跃度热力图数据（按指定粒度和时间范围）
     */
    @GetMapping("/activity-heatmap")
    public R generateMemberActivityHeatmap(
            @RequestParam String granularity,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<MemberActivityHeatmap> heatmapData = analyticsService.generateMemberActivityHeatmap(granularity, start, end);
        return R.ok().put("data", heatmapData);
    }

    /**
     * 查询生命周期价值数据（分页）
     */
    @GetMapping("/ltv")
    public R findLTVData(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size) {
        List<MemberLTV> ltvData = analyticsService.findLTVData(page, size);
        return R.ok().put("data", ltvData);
    }

    /**
     * 查询积分使用效率数据（分页）
     */
    @GetMapping("/points-efficiency")
    public R findPointsEfficiencyData(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        List<PointsEfficiency> efficiencyData = analyticsService.findPointsEfficiencyData(page, size);
        return R.ok().put("data", efficiencyData);
    }

    /**
     * 查询会员活跃度热力图数据（分页）
     */
    @GetMapping("/activity-heatmap/data")
    public R findActivityHeatmapData(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        List<MemberActivityHeatmap> heatmapData = analyticsService.findActivityHeatmapData(page, size);
        return R.ok().put("data", heatmapData);
    }

    /**
     * 获取会员生命周期价值统计数据
     */
    @GetMapping("/ltv/stats")
    public R getLTVStats() {
        Double averageLTV = analyticsService.getAverageLTV();
        return R.ok().put("averageLTV", averageLTV);
    }

    /**
     * 获取积分使用效率统计数据
     */
    @GetMapping("/points-efficiency/stats")
    public R getPointsEfficiencyStats() {
        Double averageEfficiency = analyticsService.getAveragePointsEfficiency();
        return R.ok().put("averageEfficiency", averageEfficiency);
    }

    /**
     * 获取会员活跃度统计数据
     */
    @GetMapping("/activity/stats")
    public R getActivityStats() {
        Double averageActivityScore = analyticsService.getAverageActivityScore();
        return R.ok().put("averageActivityScore", averageActivityScore);
    }
}