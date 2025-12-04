package com.yasinyuan.testspring.controller.benefit;

import com.yasinyuan.testspring.service.benefit.MemberBenefitService;
import com.yasinyuan.testspring.entity.benefit.MemberBenefit;
import com.yasinyuan.testspring.tools.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member-benefits")
public class MemberBenefitController {

    @Autowired
    private MemberBenefitService memberBenefitService;

    /**
     * 创建会员权益
     */
    @PostMapping
    public R create(@RequestBody MemberBenefit memberBenefit) {
        memberBenefitService.create(memberBenefit);
        return R.ok();
    }

    /**
     * 更新会员权益
     */
    @PutMapping("/{id}")
    public R update(@PathVariable Long id, @RequestBody MemberBenefit memberBenefit) {
        memberBenefit.setId(id);
        memberBenefitService.update(memberBenefit);
        return R.ok();
    }

    /**
     * 根据ID查询会员权益
     */
    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        MemberBenefit memberBenefit = memberBenefitService.findById(id);
        return R.ok().put("data", memberBenefit);
    }

    /**
     * 根据会员等级ID查询会员权益
     */
    @GetMapping("/by-level/{levelId}")
    public R findByLevelId(@PathVariable Long levelId) {
        List<MemberBenefit> benefits = memberBenefitService.findByLevelId(levelId);
        return R.ok().put("data", benefits);
    }

    /**
     * 根据权益类型查询会员权益
     */
    @GetMapping("/by-type/{type}")
    public R findByType(@PathVariable String type) {
        List<MemberBenefit> benefits = memberBenefitService.findByType(type);
        return R.ok().put("data", benefits);
    }

    /**
     * 查询所有有效会员权益
     */
    @GetMapping("/valid")
    public R findValid() {
        List<MemberBenefit> benefits = memberBenefitService.findValid();
        return R.ok().put("data", benefits);
    }

    /**
     * 查询所有会员权益（分页）
     */
    @GetMapping
    public R findAll(@RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "10") int size) {
        List<MemberBenefit> benefits = memberBenefitService.findAll(page, size);
        return R.ok().put("data", benefits);
    }

    /**
     * 激活/停用会员权益
     */
    @PutMapping("/toggle-status/{id}")
    public R toggleMemberBenefitStatus(@PathVariable Long id) {
        MemberBenefit memberBenefit = memberBenefitService.toggleMemberBenefitStatus(id);
        if (memberBenefit != null) {
            return R.ok().put("memberBenefit", memberBenefit);
        } else {
            return R.error("操作失败");
        }
    }

    /**
     * 删除会员权益
     */
    @DeleteMapping("/delete/{id}")
    public R deleteMemberBenefit(@PathVariable Long id) {
        memberBenefitService.deleteMemberBenefit(id);
        return R.ok();
    }
}