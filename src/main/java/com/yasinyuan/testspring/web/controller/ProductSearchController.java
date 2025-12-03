package com.yasinyuan.testspring.web.controller;

import com.yasinyuan.testspring.model.ProductSearch;
import com.yasinyuan.testspring.service.ProductSearchService;
import com.yasinyuan.testspring.tools.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 商品搜索 Controller
 */
@RestController
@RequestMapping("/api/product/search")
public class ProductSearchController extends BaseController {
    
    @Autowired
    private ProductSearchService productSearchService;
    
    /**
     * 综合搜索
     * @param keyword 关键词
     * @param brandId 品牌ID
     * @param categoryId 分类ID
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param page 页码
     * @param size 每页大小
     * @return 搜索结果
     */
    @GetMapping("/query")
    public PageBean<ProductSearch> search(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "brandId", required = false) Long brandId,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return productSearchService.search(keyword, brandId, categoryId, minPrice, maxPrice, page, size);
    }
    
    /**
     * 按名称搜索
     * @param name 商品名称
     * @param page 页码
     * @param size 每页大小
     * @return 搜索结果
     */
    @GetMapping("/name")
    public PageBean<ProductSearch> searchByName(
            @RequestParam("name") String name,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return productSearchService.searchByName(name, page, size);
    }
    
    /**
     * 按品牌搜索
     * @param brandId 品牌ID
     * @param page 页码
     * @param size 每页大小
     * @return 搜索结果
     */
    @GetMapping("/brand")
    public PageBean<ProductSearch> searchByBrand(
            @RequestParam("brandId") Long brandId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return productSearchService.searchByBrand(brandId, page, size);
    }
    
    /**
     * 按分类搜索
     * @param categoryId 分类ID
     * @param page 页码
     * @param size 每页大小
     * @return 搜索结果
     */
    @GetMapping("/category")
    public PageBean<ProductSearch> searchByCategory(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return productSearchService.searchByCategory(categoryId, page, size);
    }
    
    /**
     * 按价格区间搜索
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param page 页码
     * @param size 每页大小
     * @return 搜索结果
     */
    @GetMapping("/price-range")
    public PageBean<ProductSearch> searchByPriceRange(
            @RequestParam("minPrice") BigDecimal minPrice,
            @RequestParam("maxPrice") BigDecimal maxPrice,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return productSearchService.searchByPriceRange(minPrice, maxPrice, page, size);
    }
    
    /**
     * 获取所有上架商品
     * @param page 页码
     * @param size 每页大小
     * @return 商品列表
     */
    @GetMapping("/enabled")
    public PageBean<ProductSearch> findAllEnabled(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return productSearchService.findAllEnabled(page, size);
    }
    
    /**
     * 重新构建搜索索引
     * @return 构建结果
     */
    @PostMapping("/rebuild-index")
    public boolean rebuildIndex() {
        return productSearchService.rebuildIndex();
    }
}