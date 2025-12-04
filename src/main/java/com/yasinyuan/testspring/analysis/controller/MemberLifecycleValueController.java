package com.yasinyuan.testspring.analysis.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasinyuan.testspring.analysis.entity.MemberLifecycleValue;
import com.yasinyuan.testspring.analysis.service.MemberLifecycleValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 会员生命周期价值分析Controller
 */
@RestController
@RequestMapping("/member-lifecycle-value")
public class MemberLifecycleValueController {

    @Autowired
    private MemberLifecycleValueService memberLifecycleValueService;

    /**
     * 分页查询会员生命周期价值分析
     */
    @GetMapping("/list")
    public IPage<MemberLifecycleValue> list(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size) {
        return memberLifecycleValueService.page(new Page<>(page, size));
    }

    /**
     * 根据会员ID查询生命周期价值分析
     */
    @GetMapping("/member/{memberId}")
    public MemberLifecycleValue getByMemberId(@PathVariable Long memberId) {
        return memberLifecycleValueService.lambdaQuery()
                .eq(MemberLifecycleValue::getMemberId, memberId)
                .one();
    }

    /**
     * 新增会员生命周期价值分析记录
     */
    @PostMapping
    public boolean save(@RequestBody MemberLifecycleValue memberLifecycleValue) {
        return memberLifecycleValueService.save(memberLifecycleValue);
    }

    /**
     * 修改会员生命周期价值分析记录
     */
    @PutMapping
    public boolean update(@RequestBody MemberLifecycleValue memberLifecycleValue) {
        return memberLifecycleValueService.updateById(memberLifecycleValue);
    }
}
