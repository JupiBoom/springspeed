package com.yasinyuan.testspring.web.controller;

import com.yasinyuan.testspring.model.ProductSpu;
import com.yasinyuan.testspring.service.ProductSpuService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 商品SPU Controller
 */
@RestController
@RequestMapping("/product/spu")
public class ProductSpuController extends BaseController {
    
    @Autowired
    private ProductSpuService productSpuService;
    
    /**
     * 分页查询商品列表
     */
    @GetMapping("/list")
    public R list(ProductSpu productSpu, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        startPage(page, limit);
        List<ProductSpu> list = productSpuService.select(productSpu);
        return page(list);
    }
    
    /**
     * 根据ID查询商品详情
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable Long id) {
        ProductSpu productSpu = productSpuService.selectById(id);
        return R.ok().put("productSpu", productSpu);
    }
    
    /**
     * 新增商品
     */
    @PostMapping("/save")
    public R save(@RequestBody ProductSpu productSpu) {
        productSpu.setCreateTime(new Date());
        productSpu.setUpdateTime(new Date());
        productSpu.setDeleted(0);
        productSpuService.insert(productSpu);
        return R.ok();
    }
    
    /**
     * 修改商品
     */
    @PostMapping("/update")
    public R update(@RequestBody ProductSpu productSpu) {
        productSpu.setUpdateTime(new Date());
        productSpuService.updateByIdSelective(productSpu);
        return R.ok();
    }
    
    /**
     * 上架商品
     */
    @PostMapping("/onSale/{id}")
    public R onSale(@PathVariable Long id) {
        boolean result = productSpuService.onSale(id);
        return result ? R.ok() : R.error("商品上架失败");
    }
    
    /**
     * 下架商品
     */
    @PostMapping("/offSale/{id}")
    public R offSale(@PathVariable Long id) {
        boolean result = productSpuService.offSale(id);
        return result ? R.ok() : R.error("商品下架失败");
    }
    
    /**
     * 审核商品
     */
    @PostMapping("/audit/{id}")
    public R audit(@PathVariable Long id, @RequestParam Integer auditStatus, @RequestParam(required = false) String auditRemark) {
        boolean result = productSpuService.audit(id, auditStatus, auditRemark);
        return result ? R.ok() : R.error("商品审核失败");
    }
    
    /**
     * 根据品牌ID查询商品列表
     */
    @GetMapping("/listByBrandId/{brandId}")
    public R listByBrandId(@PathVariable Long brandId) {
        List<ProductSpu> list = productSpuService.selectByBrandId(brandId);
        return R.ok().put("list", list);
    }
    
    /**
     * 根据分类ID查询商品列表
     */
    @GetMapping("/listByCategoryId/{categoryId}")
    public R listByCategoryId(@PathVariable Long categoryId) {
        List<ProductSpu> list = productSpuService.selectByCategoryId(categoryId);
        return R.ok().put("list", list);
    }
}