package com.yasinyuan.testspring.analysis.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasinyuan.testspring.analysis.entity.PointUsageEfficiency;
import com.yasinyuan.testspring.analysis.service.PointUsageEfficiencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 积分使用效率分析Controller
 */
@RestController
@RequestMapping("/point-usage-efficiency")
public class PointUsageEfficiencyController {

    @Autowired
    private PointUsageEfficiencyService pointUsageEfficiencyService;

    /**
     * 分页查询积分使用效率分析
     */
    @GetMapping("/list")
    public IPage<PointUsageEfficiency> list(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size) {
        return pointUsageEfficiencyService.page(new Page<>(page, size));
    }

    /**
     * 根据会员ID查询积分使用效率分析
     */
    @GetMapping("/member/{memberId}")
    public PointUsageEfficiency getByMemberId(@PathVariable Long memberId) {
        return pointUsageEfficiencyService.lambdaQuery()
                .eq(PointUsageEfficiency::getMemberId, memberId)
                .one();
    }

    /**
     * 新增积分使用效率分析记录
     */
    @PostMapping
    public boolean save(@RequestBody PointUsageEfficiency pointUsageEfficiency) {
        return pointUsageEfficiencyService.save(pointUsageEfficiency);
    }

    /**
     * 修改积分使用效率分析记录
     */
    @PutMapping
    public boolean update(@RequestBody PointUsageEfficiency pointUsageEfficiency) {
        return pointUsageEfficiencyService.updateById(pointUsageEfficiency);
    }
}
