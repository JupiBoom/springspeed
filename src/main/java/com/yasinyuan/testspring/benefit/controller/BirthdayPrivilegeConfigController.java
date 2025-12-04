package com.yasinyuan.testspring.benefit.controller;

import com.yasinyuan.testspring.benefit.entity.BirthdayPrivilegeConfig;
import com.yasinyuan.testspring.benefit.service.BirthdayPrivilegeConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 生日特权配置Controller
 */
@RestController
@RequestMapping("/birthday-privilege-config")
public class BirthdayPrivilegeConfigController {

    @Autowired
    private BirthdayPrivilegeConfigService birthdayPrivilegeConfigService;

    /**
     * 查询所有生日特权配置
     */
    @GetMapping("/list")
    public List<BirthdayPrivilegeConfig> list() {
        return birthdayPrivilegeConfigService.list();
    }

    /**
     * 根据ID查询生日特权配置
     */
    @GetMapping("/{id}")
    public BirthdayPrivilegeConfig getById(@PathVariable Long id) {
        return birthdayPrivilegeConfigService.getById(id);
    }

    /**
     * 根据会员等级查询生日特权配置
     */
    @GetMapping("/level/{memberLevel}")
    public BirthdayPrivilegeConfig getByMemberLevel(@PathVariable Integer memberLevel) {
        return birthdayPrivilegeConfigService.lambdaQuery()
                .eq(BirthdayPrivilegeConfig::getMemberLevel, memberLevel)
                .one();
    }

    /**
     * 新增生日特权配置
     */
    @PostMapping
    public boolean save(@RequestBody BirthdayPrivilegeConfig birthdayPrivilegeConfig) {
        return birthdayPrivilegeConfigService.save(birthdayPrivilegeConfig);
    }

    /**
     * 修改生日特权配置
     */
    @PutMapping
    public boolean update(@RequestBody BirthdayPrivilegeConfig birthdayPrivilegeConfig) {
        return birthdayPrivilegeConfigService.updateById(birthdayPrivilegeConfig);
    }

    /**
     * 删除生日特权配置
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return birthdayPrivilegeConfigService.removeById(id);
    }
}
