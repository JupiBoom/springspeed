package com.yasinyuan.testspring.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasinyuan.testspring.member.entity.MemberLevelConfig;
import com.yasinyuan.testspring.member.service.MemberLevelConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员等级配置Controller
 */
@RestController
@RequestMapping("/member-level-config")
public class MemberLevelConfigController {

    @Autowired
    private MemberLevelConfigService memberLevelConfigService;

    /**
     * 查询所有会员等级配置
     */
    @GetMapping("/list")
    public List<MemberLevelConfig> list() {
        return memberLevelConfigService.list();
    }

    /**
     * 根据ID查询会员等级配置
     */
    @GetMapping("/{id}")
    public MemberLevelConfig getById(@PathVariable Long id) {
        return memberLevelConfigService.getById(id);
    }

    /**
     * 根据等级代码查询会员等级配置
     */
    @GetMapping("/code/{levelCode}")
    public MemberLevelConfig getByLevelCode(@PathVariable Integer levelCode) {
        return memberLevelConfigService.lambdaQuery()
                .eq(MemberLevelConfig::getLevelCode, levelCode)
                .one();
    }

    /**
     * 新增会员等级配置
     */
    @PostMapping
    public boolean save(@RequestBody MemberLevelConfig memberLevelConfig) {
        return memberLevelConfigService.save(memberLevelConfig);
    }

    /**
     * 修改会员等级配置
     */
    @PutMapping
    public boolean update(@RequestBody MemberLevelConfig memberLevelConfig) {
        return memberLevelConfigService.updateById(memberLevelConfig);
    }

    /**
     * 删除会员等级配置
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return memberLevelConfigService.removeById(id);
    }
}
