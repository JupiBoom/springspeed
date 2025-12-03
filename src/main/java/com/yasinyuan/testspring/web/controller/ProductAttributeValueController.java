package com.yasinyuan.testspring.web.controller;

import com.yasinyuan.testspring.model.ProductAttributeValue;
import com.yasinyuan.testspring.service.ProductAttributeValueService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品属性值 Controller
 */
@RestController
@RequestMapping("/product/attributeValue")
public class ProductAttributeValueController extends BaseController {
    
    @Autowired
    private ProductAttributeValueService productAttributeValueService;
    
    /**
     * 根据SPU ID查询属性值列表
     */
    @GetMapping("/listBySpuId/{spuId}")
    public R listBySpuId(@PathVariable Long spuId) {
        List<ProductAttributeValue> list = productAttributeValueService.selectBySpuId(spuId);
        return R.ok().put("list", list);
    }
    
    /**
     * 根据SPU ID和属性ID查询属性值
     */
    @GetMapping("/getBySpuIdAndAttributeId")
    public R getBySpuIdAndAttributeId(@RequestParam Long spuId, @RequestParam Long attributeId) {
        ProductAttributeValue attributeValue = productAttributeValueService.selectBySpuIdAndAttributeId(spuId, attributeId);
        return R.ok().put("attributeValue", attributeValue);
    }
    
    /**
     * 批量保存商品属性值
     */
    @PostMapping("/batchSave/{spuId}")
    public R batchSave(@PathVariable Long spuId, @RequestBody List<ProductAttributeValue> attributeValues) {
        boolean result = productAttributeValueService.batchSave(spuId, attributeValues);
        return result ? R.ok() : R.error("属性值保存失败");
    }
    
    /**
     * 删除商品的所有属性值
     */
    @PostMapping("/deleteBySpuId/{spuId}")
    public R deleteBySpuId(@PathVariable Long spuId) {
        boolean result = productAttributeValueService.deleteBySpuId(spuId);
        return result ? R.ok() : R.error("属性值删除失败");
    }
}