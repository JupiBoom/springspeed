package com.yasinyuan.testspring.point.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasinyuan.testspring.point.entity.PointRecord;
import com.yasinyuan.testspring.point.service.PointRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 积分变动记录Controller
 */
@RestController
@RequestMapping("/point-record")
public class PointRecordController {

    @Autowired
    private PointRecordService pointRecordService;

    /**
     * 分页查询会员积分变动记录
     */
    @GetMapping("/member/{memberId}")
    public IPage<PointRecord> getByMemberId(@PathVariable Long memberId,
                                            @RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size) {
        return pointRecordService.lambdaQuery()
                .eq(PointRecord::getMemberId, memberId)
                .page(new Page<>(page, size));
    }

    /**
     * 新增积分变动记录
     */
    @PostMapping
    public boolean save(@RequestBody PointRecord pointRecord) {
        return pointRecordService.save(pointRecord);
    }
}
