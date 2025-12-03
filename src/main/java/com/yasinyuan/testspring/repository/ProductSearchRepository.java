package com.yasinyuan.testspring.repository;

import com.yasinyuan.testspring.model.ProductSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 商品搜索 Repository
 */
public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearch, Long> {
    
    /**
     * 根据商品名称模糊查询
     * @param name 商品名称
     * @return 商品列表
     */
    List<ProductSearch> findByNameContaining(String name);
    
    /**
     * 根据品牌ID查询
     * @param brandId 品牌ID
     * @return 商品列表
     */
    List<ProductSearch> findByBrandId(Long brandId);
    
    /**
     * 根据分类ID查询
     * @param categoryId 分类ID
     * @return 商品列表
     */
    List<ProductSearch> findByCategoryId(Long categoryId);
    
    /**
     * 根据状态查询
     * @param status 状态
     * @return 商品列表
     */
    List<ProductSearch> findByStatus(Integer status);
    
    /**
     * 根据品牌ID和分类ID查询
     * @param brandId 品牌ID
     * @param categoryId 分类ID
     * @return 商品列表
     */
    List<ProductSearch> findByBrandIdAndCategoryId(Long brandId, Long categoryId);
    
    /**
     * 根据价格区间查询
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @return 商品列表
     */
    List<ProductSearch> findByPriceBetween(Double minPrice, Double maxPrice);
}