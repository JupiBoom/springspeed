package com.yasinyuan.testspring.web.controller;

import com.yasinyuan.testspring.model.Brand;
import com.yasinyuan.testspring.service.BrandService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 品牌 Controller
 */
@RestController
@RequestMapping("/product/brand")
public class BrandController extends BaseController {
    
    @Autowired
    private BrandService brandService;
    
    /**
     * 分页查询品牌列表
     */
    @GetMapping("/list")
    public R list(Brand brand, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        startPage(page, limit);
        List<Brand> list = brandService.select(brand);
        return page(list);
    }
    
    /**
     * 根据ID查询品牌详情
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable Long id) {
        Brand brand = brandService.selectById(id);
        return R.ok().put("brand", brand);
    }
    
    /**
     * 查询启用的品牌列表
     */
    @GetMapping("/listEnable")
    public R listEnable() {
        List<Brand> list = brandService.selectEnableBrands();
        return R.ok().put("list", list);
    }
    
    /**
     * 根据品牌名称模糊查询
     */
    @GetMapping("/listByNameLike/{brandName}")
    public R listByNameLike(@PathVariable String brandName) {
        List<Brand> list = brandService.selectByBrandNameLike(brandName);
        return R.ok().put("list", list);
    }
    
    /**
     * 新增品牌
     */
    @PostMapping("/save")
    public R save(@RequestBody Brand brand) {
        brand.setCreateTime(new Date());
        brand.setUpdateTime(new Date());
        brand.setDeleted(0);
        brandService.insert(brand);
        return R.ok();
    }
    
    /**
     * 修改品牌
     */
    @PostMapping("/update")
    public R update(@RequestBody Brand brand) {
        brand.setUpdateTime(new Date());
        brandService.updateByIdSelective(brand);
        return R.ok();
    }
    
    /**
     * 删除品牌
     */
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        brandService.deleteById(id);
        return R.ok();
    }
}