package com.yasinyuan.testspring.web.controller;

import com.yasinyuan.testspring.model.ProductSku;
import com.yasinyuan.testspring.service.ProductSkuService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 商品SKU Controller
 */
@RestController
@RequestMapping("/product/sku")
public class ProductSkuController extends BaseController {
    
    @Autowired
    private ProductSkuService productSkuService;
    
    /**
     * 分页查询SKU列表
     */
    @GetMapping("/list")
    public R list(ProductSku productSku, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        startPage(page, limit);
        List<ProductSku> list = productSkuService.select(productSku);
        return page(list);
    }
    
    /**
     * 根据ID查询SKU详情
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable Long id) {
        ProductSku productSku = productSkuService.selectById(id);
        return R.ok().put("productSku", productSku);
    }
    
    /**
     * 根据SPU ID查询SKU列表
     */
    @GetMapping("/listBySpuId/{spuId}")
    public R listBySpuId(@PathVariable Long spuId) {
        List<ProductSku> list = productSkuService.selectBySpuId(spuId);
        return R.ok().put("list", list);
    }
    
    /**
     * 根据SKU编码查询
     */
    @GetMapping("/getBySkuCode/{skuCode}")
    public R getBySkuCode(@PathVariable String skuCode) {
        ProductSku productSku = productSkuService.selectBySkuCode(skuCode);
        return R.ok().put("productSku", productSku);
    }
    
    /**
     * 新增SKU
     */
    @PostMapping("/save")
    public R save(@RequestBody ProductSku productSku) {
        productSku.setCreateTime(new Date());
        productSku.setUpdateTime(new Date());
        productSku.setDeleted(0);
        productSkuService.insert(productSku);
        return R.ok();
    }
    
    /**
     * 修改SKU
     */
    @PostMapping("/update")
    public R update(@RequestBody ProductSku productSku) {
        productSku.setUpdateTime(new Date());
        productSkuService.updateByIdSelective(productSku);
        return R.ok();
    }
    
    /**
     * 更新SKU库存
     */
    @PostMapping("/updateStock/{id}")
    public R updateStock(@PathVariable Long id, @RequestParam Integer stock) {
        boolean result = productSkuService.updateStock(id, stock);
        return result ? R.ok() : R.error("库存更新失败");
    }
    
    /**
     * 减少SKU库存
     */
    @PostMapping("/decreaseStock/{id}")
    public R decreaseStock(@PathVariable Long id, @RequestParam Integer quantity) {
        boolean result = productSkuService.decreaseStock(id, quantity);
        return result ? R.ok() : R.error("库存减少失败");
    }
    
    /**
     * 增加SKU库存
     */
    @PostMapping("/increaseStock/{id}")
    public R increaseStock(@PathVariable Long id, @RequestParam Integer quantity) {
        boolean result = productSkuService.increaseStock(id, quantity);
        return result ? R.ok() : R.error("库存增加失败");
    }
    
    /**
     * 更新SKU状态
     */
    @PostMapping("/updateStatus/{id}")
    public R updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        boolean result = productSkuService.updateStatus(id, status);
        return result ? R.ok() : R.error("状态更新失败");
    }
}