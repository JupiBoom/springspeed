package com.yasinyuan.testspring.point.controller;

import com.yasinyuan.testspring.point.entity.PointRuleConfig;
import com.yasinyuan.testspring.point.service.PointRuleConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 积分规则配置Controller
 */
@RestController
@RequestMapping("/point-rule-config")
public class PointRuleConfigController {

    @Autowired
    private PointRuleConfigService pointRuleConfigService;

    /**
     * 查询所有积分规则配置
     */
    @GetMapping("/list")
    public List<PointRuleConfig> list() {
        return pointRuleConfigService.list();
    }

    /**
     * 根据ID查询积分规则配置
     */
    @GetMapping("/{id}")
    public PointRuleConfig getById(@PathVariable Long id) {
        return pointRuleConfigService.getById(id);
    }

    /**
     * 根据规则类型查询积分规则配置
     */
    @GetMapping("/type/{ruleType}")
    public PointRuleConfig getByRuleType(@PathVariable Integer ruleType) {
        return pointRuleConfigService.lambdaQuery()
                .eq(PointRuleConfig::getRuleType, ruleType)
                .one();
    }

    /**
     * 新增积分规则配置
     */
    @PostMapping
    public boolean save(@RequestBody PointRuleConfig pointRuleConfig) {
        return pointRuleConfigService.save(pointRuleConfig);
    }

    /**
     * 修改积分规则配置
     */
    @PutMapping
    public boolean update(@RequestBody PointRuleConfig pointRuleConfig) {
        return pointRuleConfigService.updateById(pointRuleConfig);
    }

    /**
     * 删除积分规则配置
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return pointRuleConfigService.removeById(id);
    }
}
