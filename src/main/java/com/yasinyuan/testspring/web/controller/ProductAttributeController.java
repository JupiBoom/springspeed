package com.yasinyuan.testspring.web.controller;

import com.yasinyuan.testspring.model.ProductAttribute;
import com.yasinyuan.testspring.service.ProductAttributeService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 商品属性 Controller
 */
@RestController
@RequestMapping("/product/attribute")
public class ProductAttributeController extends BaseController {
    
    @Autowired
    private ProductAttributeService productAttributeService;
    
    /**
     * 分页查询属性列表
     */
    @GetMapping("/list")
    public R list(ProductAttribute productAttribute, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        startPage(page, limit);
        List<ProductAttribute> list = productAttributeService.select(productAttribute);
        return page(list);
    }
    
    /**
     * 根据ID查询属性详情
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable Long id) {
        ProductAttribute productAttribute = productAttributeService.selectById(id);
        return R.ok().put("productAttribute", productAttribute);
    }
    
    /**
     * 根据分类ID查询属性列表
     */
    @GetMapping("/listByCategoryId/{categoryId}")
    public R listByCategoryId(@PathVariable Long categoryId) {
        List<ProductAttribute> list = productAttributeService.selectByCategoryId(categoryId);
        return R.ok().put("list", list);
    }
    
    /**
     * 根据分类ID和属性类型查询属性列表
     */
    @GetMapping("/listByCategoryIdAndAttrType")
    public R listByCategoryIdAndAttrType(@RequestParam Long categoryId, @RequestParam Integer attrType) {
        List<ProductAttribute> list = productAttributeService.selectByCategoryIdAndAttrType(categoryId, attrType);
        return R.ok().put("list", list);
    }
    
    /**
     * 查询启用的属性列表
     */
    @GetMapping("/listEnable")
    public R listEnable() {
        List<ProductAttribute> list = productAttributeService.selectEnableAttributes();
        return R.ok().put("list", list);
    }
    
    /**
     * 新增属性
     */
    @PostMapping("/save")
    public R save(@RequestBody ProductAttribute productAttribute) {
        productAttribute.setCreateTime(new Date());
        productAttribute.setUpdateTime(new Date());
        productAttribute.setDeleted(0);
        productAttributeService.insert(productAttribute);
        return R.ok();
    }
    
    /**
     * 修改属性
     */
    @PostMapping("/update")
    public R update(@RequestBody ProductAttribute productAttribute) {
        productAttribute.setUpdateTime(new Date());
        productAttributeService.updateByIdSelective(productAttribute);
        return R.ok();
    }
    
    /**
     * 删除属性
     */
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        productAttributeService.deleteById(id);
        return R.ok();
    }
}