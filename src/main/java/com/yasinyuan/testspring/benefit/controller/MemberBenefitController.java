package com.yasinyuan.testspring.benefit.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasinyuan.testspring.benefit.entity.MemberBenefit;
import com.yasinyuan.testspring.benefit.service.MemberBenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员权益Controller
 */
@RestController
@RequestMapping("/member-benefit")
public class MemberBenefitController {

    @Autowired
    private MemberBenefitService memberBenefitService;

    /**
     * 查询所有会员权益
     */
    @GetMapping("/list")
    public List<MemberBenefit> list() {
        return memberBenefitService.list();
    }

    /**
     * 根据ID查询会员权益
     */
    @GetMapping("/{id}")
    public MemberBenefit getById(@PathVariable Long id) {
        return memberBenefitService.getById(id);
    }

    /**
     * 根据会员等级查询权益
     */
    @GetMapping("/level/{memberLevel}")
    public List<MemberBenefit> getByMemberLevel(@PathVariable Integer memberLevel) {
        return memberBenefitService.lambdaQuery()
                .eq(MemberBenefit::getMemberLevel, memberLevel)
                .list();
    }

    /**
     * 新增会员权益
     */
    @PostMapping
    public boolean save(@RequestBody MemberBenefit memberBenefit) {
        return memberBenefitService.save(memberBenefit);
    }

    /**
     * 修改会员权益
     */
    @PutMapping
    public boolean update(@RequestBody MemberBenefit memberBenefit) {
        return memberBenefitService.updateById(memberBenefit);
    }

    /**
     * 删除会员权益
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return memberBenefitService.removeById(id);
    }
}
