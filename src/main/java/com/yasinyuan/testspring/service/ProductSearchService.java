package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.model.ProductSearch;
import com.yasinyuan.testspring.tools.PageBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品搜索 Service接口
 */
public interface ProductSearchService {
    
    /**
     * 保存或更新商品索引
     * @param productSearch 商品搜索对象
     * @return 保存结果
     */
    ProductSearch save(ProductSearch productSearch);
    
    /**
     * 根据ID删除商品索引
     * @param id 商品ID
     */
    void deleteById(Long id);
    
    /**
     * 批量保存商品索引
     * @param productSearchList 商品搜索对象列表
     * @return 保存结果列表
     */
    Iterable<ProductSearch> saveAll(Iterable<ProductSearch> productSearchList);
    
    /**
     * 批量删除商品索引
     * @param ids 商品ID列表
     */
    void deleteAllById(Iterable<Long> ids);
    
    /**
     * 根据ID查询商品索引
     * @param id 商品ID
     * @return 商品搜索对象
     */
    ProductSearch findById(Long id);
    
    /**
     * 搜索商品（综合搜索）
     * @param keyword 搜索关键词
     * @param brandId 品牌ID（可选）
     * @param categoryId 分类ID（可选）
     * @param minPrice 最低价格（可选）
     * @param maxPrice 最高价格（可选）
     * @param page 页码
     * @param size 每页数量
     * @return 分页商品列表
     */
    PageBean<ProductSearch> search(String keyword, Long brandId, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, Integer page, Integer size);
    
    /**
     * 按商品名称搜索
     * @param name 商品名称
     * @param page 页码
     * @param size 每页数量
     * @return 分页商品列表
     */
    PageBean<ProductSearch> searchByName(String name, Integer page, Integer size);
    
    /**
     * 按品牌搜索
     * @param brandId 品牌ID
     * @param page 页码
     * @param size 每页数量
     * @return 分页商品列表
     */
    PageBean<ProductSearch> searchByBrand(Long brandId, Integer page, Integer size);
    
    /**
     * 按分类搜索
     * @param categoryId 分类ID
     * @param page 页码
     * @param size 每页数量
     * @return 分页商品列表
     */
    PageBean<ProductSearch> searchByCategory(Long categoryId, Integer page, Integer size);
    
    /**
     * 按价格区间搜索
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param page 页码
     * @param size 每页数量
     * @return 分页商品列表
     */
    PageBean<ProductSearch> searchByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Integer page, Integer size);
    
    /**
     * 获取所有上架商品
     * @param page 页码
     * @param size 每页数量
     * @return 分页商品列表
     */
    PageBean<ProductSearch> findAllEnabled(Integer page, Integer size);
    
    /**
     * 重建商品索引
     * @return 重建结果
     */
    boolean rebuildIndex();
}