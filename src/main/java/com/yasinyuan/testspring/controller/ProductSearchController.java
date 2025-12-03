package com.yasinyuan.testspring.controller;

import com.yasinyuan.testspring.model.search.ProductSearch;
import com.yasinyuan.testspring.service.search.ProductSearchService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class ProductSearchController {

    @Autowired
    private ProductSearchService productSearchService;

    /**
     * 搜索商品
     */
    @GetMapping("/product")
    public R searchProduct(@RequestParam String keyword, 
                           @RequestParam(required = false, defaultValue = "1") Integer page, 
                           @RequestParam(required = false, defaultValue = "10") Integer size) {
        return productSearchService.search(keyword, page, size);
    }

    /**
     * 按分类搜索商品
     */
    @GetMapping("/product/category/{categoryId}")
    public R searchProductByCategory(@PathVariable Long categoryId, 
                                      @RequestParam(required = false, defaultValue = "1") Integer page, 
                                      @RequestParam(required = false, defaultValue = "10") Integer size) {
        return productSearchService.searchByCategory(categoryId, page, size);
    }

    /**
     * 按品牌搜索商品
     */
    @GetMapping("/product/brand/{brandId}")
    public R searchProductByBrand(@PathVariable Long brandId, 
                                   @RequestParam(required = false, defaultValue = "1") Integer page, 
                                   @RequestParam(required = false, defaultValue = "10") Integer size) {
        return productSearchService.searchByBrand(brandId, page, size);
    }

    /**
     * 获取搜索建议
     */
    @GetMapping("/suggestions")
    public R getSuggestions(@RequestParam String keyword) {
        List<String> suggestions = productSearchService.getSuggestions(keyword);
        return R.ok().put("suggestions", suggestions);
    }

    /**
     * 刷新商品索引
     */
    @PostMapping("/refresh")
    public R refreshIndex() {
        productSearchService.refreshIndex();
        return R.ok();
    }

    /**
     * 删除商品索引
     */
    @PostMapping("/delete/{id}")
    public R deleteIndex(@PathVariable Long id) {
        productSearchService.deleteIndex(id);
        return R.ok();
    }
}
