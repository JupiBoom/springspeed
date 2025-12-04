package com.yasinyuan.testspring.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasinyuan.testspring.member.entity.Member;
import com.yasinyuan.testspring.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员Controller
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 分页查询会员列表
     */
    @GetMapping("/list")
    public IPage<Member> list(@RequestParam(defaultValue = "1") Integer page, 
                             @RequestParam(defaultValue = "10") Integer size) {
        return memberService.page(new Page<>(page, size));
    }

    /**
     * 根据ID查询会员
     */
    @GetMapping("/{id}")
    public Member getById(@PathVariable Long id) {
        return memberService.getById(id);
    }

    /**
     * 根据手机号查询会员
     */
    @GetMapping("/phone/{phone}")
    public Member getByPhone(@PathVariable String phone) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        return memberService.getOne(queryWrapper);
    }

    /**
     * 新增会员
     */
    @PostMapping
    public boolean save(@RequestBody Member member) {
        return memberService.save(member);
    }

    /**
     * 修改会员
     */
    @PutMapping
    public boolean update(@RequestBody Member member) {
        return memberService.updateById(member);
    }

    /**
     * 删除会员
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return memberService.removeById(id);
    }
}
