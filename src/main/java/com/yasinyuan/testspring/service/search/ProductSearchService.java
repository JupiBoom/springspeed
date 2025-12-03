package com.yasinyuan.testspring.service.search;

import com.yasinyuan.testspring.model.search.ProductSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductSearchService {
    
    /**
     * 初始化商品索引
     */
    void initProductIndex();
    
    /**
     * 更新商品索引
     * @param productSearch 商品搜索信息
     */
    void updateProductIndex(ProductSearch productSearch);
    
    /**
     * 批量更新商品索引
     * @param productSearches 商品搜索列表
     */
    void batchUpdateProductIndex(List<ProductSearch> productSearches);
    
    /**
     * 删除商品索引
     * @param id 商品ID
     */
    void deleteProductIndex(Long id);
    
    /**
     * 根据关键词搜索商品
     * @param keyword 搜索关键词
     * @param pageable 分页参数
     * @return 商品分页列表
     */
    Page<ProductSearch> searchByKeyword(String keyword, Pageable pageable);
    
    /**
     * 根据品牌ID搜索商品
     * @param brandId 品牌ID
     * @param pageable 分页参数
     * @return 商品分页列表
     */
    Page<ProductSearch> searchByBrandId(Long brandId, Pageable pageable);
    
    /**
     * 根据分类ID搜索商品
     * @param categoryId 分类ID
     * @param pageable 分页参数
     * @return 商品分页列表
     */
    Page<ProductSearch> searchByCategoryId(Long categoryId, Pageable pageable);
    
    /**
     * 根据价格区间搜索商品
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param pageable 分页参数
     * @return 商品分页列表
     */
    Page<ProductSearch> searchByPriceRange(Double minPrice, Double maxPrice, Pageable pageable);
    
    /**
     * 多维度筛选商品
     * @param keyword 搜索关键词
     * @param brandId 品牌ID
     * @param categoryId 分类ID
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param pageable 分页参数
     * @return 商品分页列表
     */
    Page<ProductSearch> searchByMultiCondition(String keyword, Long brandId, Long categoryId, Double minPrice, Double maxPrice, Pageable pageable);
}
