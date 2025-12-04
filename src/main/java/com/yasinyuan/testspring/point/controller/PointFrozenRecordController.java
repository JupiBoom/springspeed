package com.yasinyuan.testspring.point.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasinyuan.testspring.point.entity.PointFrozenRecord;
import com.yasinyuan.testspring.point.service.PointFrozenRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 积分冻结记录Controller
 */
@RestController
@RequestMapping("/point-frozen-record")
public class PointFrozenRecordController {

    @Autowired
    private PointFrozenRecordService pointFrozenRecordService;

    /**
     * 分页查询会员积分冻结记录
     */
    @GetMapping("/member/{memberId}")
    public IPage<PointFrozenRecord> getByMemberId(@PathVariable Long memberId,
                                                  @RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        return pointFrozenRecordService.lambdaQuery()
                .eq(PointFrozenRecord::getMemberId, memberId)
                .page(new Page<>(page, size));
    }

    /**
     * 新增积分冻结记录
     */
    @PostMapping
    public boolean save(@RequestBody PointFrozenRecord pointFrozenRecord) {
        return pointFrozenRecordService.save(pointFrozenRecord);
    }

    /**
     * 修改积分冻结记录
     */
    @PutMapping
    public boolean update(@RequestBody PointFrozenRecord pointFrozenRecord) {
        return pointFrozenRecordService.updateById(pointFrozenRecord);
    }
}
