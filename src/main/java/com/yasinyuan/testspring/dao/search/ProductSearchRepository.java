package com.yasinyuan.testspring.dao.search;

import com.yasinyuan.testspring.model.search.ProductSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearch, Long> {
    
    /**
     * 根据关键词搜索商品
     * @param keyword 搜索关键词
     * @param pageable 分页参数
     * @return 商品分页列表
     */
    Page<ProductSearch> findByNameOrDescription(String keyword, String keyword2, Pageable pageable);
    
    /**
     * 根据品牌ID搜索商品
     * @param brandId 品牌ID
     * @param pageable 分页参数
     * @return 商品分页列表
     */
    Page<ProductSearch> findByBrandId(Long brandId, Pageable pageable);
    
    /**
     * 根据分类ID搜索商品
     * @param categoryId 分类ID
     * @param pageable 分页参数
     * @return 商品分页列表
     */
    Page<ProductSearch> findByCategoryId(Long categoryId, Pageable pageable);
    
    /**
     * 根据价格区间搜索商品
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param pageable 分页参数
     * @return 商品分页列表
     */
    Page<ProductSearch> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);
}
