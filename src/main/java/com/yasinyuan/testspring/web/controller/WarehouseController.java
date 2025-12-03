package com.yasinyuan.testspring.web.controller;

import com.yasinyuan.testspring.core.Result;
import com.yasinyuan.testspring.model.Warehouse;
import com.yasinyuan.testspring.service.WarehouseService;
import com.yasinyuan.testspring.tools.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 仓库管理Controller
 */
@Controller
@RequestMapping("/warehouse")
public class WarehouseController extends BaseController {
    
    @Autowired
    private WarehouseService warehouseService;
    
    /**
     * 获取仓库列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result<List<Warehouse>> list() {
        List<Warehouse> list = warehouseService.selectAll();
        return Result.success(list);
    }
    
    /**
     * 分页查询仓库
     */
    @GetMapping("/page")
    @ResponseBody
    public Result<PageBean<Warehouse>> page(@RequestParam(defaultValue = "1") Integer page, 
                                            @RequestParam(defaultValue = "10") Integer size) {
        PageBean<Warehouse> pageBean = warehouseService.selectPage(page, size);
        return Result.success(pageBean);
    }
    
    /**
     * 按编码查询仓库
     */
    @GetMapping("/code/{code}")
    @ResponseBody
    public Result<Warehouse> getByCode(@PathVariable String code) {
        Warehouse warehouse = warehouseService.selectByCode(code);
        return Result.success(warehouse);
    }
    
    /**
     * 名称模糊查询
     */
    @GetMapping("/name")
    @ResponseBody
    public Result<List<Warehouse>> getByName(@RequestParam String name) {
        List<Warehouse> list = warehouseService.selectByNameLike(name);
        return Result.success(list);
    }
    
    /**
     * 获取启用的仓库列表
     */
    @GetMapping("/enabled")
    @ResponseBody
    public Result<List<Warehouse>> getEnabled() {
        List<Warehouse> list = warehouseService.selectEnabled();
        return Result.success(list);
    }
    
    /**
     * 获取仓库详情
     */
    @GetMapping("/id/{id}")
    @ResponseBody
    public Result<Warehouse> getById(@PathVariable Long id) {
        Warehouse warehouse = warehouseService.selectById(id);
        return Result.success(warehouse);
    }
    
    /**
     * 检查编码是否存在
     */
    @GetMapping("/exists/code/{code}")
    @ResponseBody
    public Result<Boolean> existsByCode(@PathVariable String code) {
        boolean exists = warehouseService.existsByCode(code);
        return Result.success(exists);
    }
    
    /**
     * 新增仓库
     */
    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody Warehouse warehouse) {
        warehouse.setEnable(true);
        warehouseService.save(warehouse);
        return Result.success("仓库添加成功");
    }
    
    /**
     * 更新仓库
     */
    @PostMapping("/update")
    @ResponseBody
    public Result<String> update(@RequestBody Warehouse warehouse) {
        warehouseService.updateById(warehouse);
        return Result.success("仓库更新成功");
    }
    
    /**
     * 启用/禁用仓库
     */
    @PostMapping("/enable")
    @ResponseBody
    public Result<String> enable(@RequestParam Long id, @RequestParam Boolean enable) {
        warehouseService.enable(id, enable);
        return Result.success(enable ? "仓库启用成功" : "仓库禁用成功");
    }
    
    /**
     * 删除仓库
     */
    @PostMapping("/delete")
    @ResponseBody
    public Result<String> delete(@RequestParam Long id) {
        warehouseService.deleteById(id);
        return Result.success("仓库删除成功");
    }
}