package com.yasinyuan.testspring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.yasinyuan.testspring.elasticsearch.ProductEs;

import java.util.List;

public interface ProductSearchService {
    
    // 导入所有商品到ES
    int importAll();
    
    // 根据ID删除商品
    void delete(Long id);
    
    // 根据ID创建商品
    ProductEs create(Long id);
    
    // 批量删除商品
    void delete(List<Long> ids);
    
    // 简单搜索
    Page<ProductEs> search(String keyword, Pageable pageable);
    
    // 高级搜索（多维度筛选）
    Page<ProductEs> search(String keyword, Long brandId, Long productCategoryId, 
                          Double minPrice, Double maxPrice, String attrValue, Pageable pageable);
}