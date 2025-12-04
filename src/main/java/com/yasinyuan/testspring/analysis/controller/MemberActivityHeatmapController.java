package com.yasinyuan.testspring.analysis.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasinyuan.testspring.analysis.entity.MemberActivityHeatmap;
import com.yasinyuan.testspring.analysis.service.MemberActivityHeatmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员活跃度热力图Controller
 */
@RestController
@RequestMapping("/member-activity-heatmap")
public class MemberActivityHeatmapController {

    @Autowired
    private MemberActivityHeatmapService memberActivityHeatmapService;

    /**
     * 查询所有会员活跃度热力图数据
     */
    @GetMapping("/list")
    public List<MemberActivityHeatmap> list() {
        return memberActivityHeatmapService.list();
    }

    /**
     * 根据月份查询会员活跃度热力图数据
     */
    @GetMapping("/month/{month}")
    public List<MemberActivityHeatmap> getByMonth(@PathVariable String month) {
        return memberActivityHeatmapService.lambdaQuery()
                .eq(MemberActivityHeatmap::getMonth, month)
                .list();
    }

    /**
     * 新增会员活跃度热力图数据
     */
    @PostMapping
    public boolean save(@RequestBody MemberActivityHeatmap memberActivityHeatmap) {
        return memberActivityHeatmapService.save(memberActivityHeatmap);
    }

    /**
     * 修改会员活跃度热力图数据
     */
    @PutMapping
    public boolean update(@RequestBody MemberActivityHeatmap memberActivityHeatmap) {
        return memberActivityHeatmapService.updateById(memberActivityHeatmap);
    }
}
