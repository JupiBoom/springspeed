package com.yasinyuan.testspring.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductEsRepository extends ElasticsearchRepository<ProductEs, Long> {
    
    // 根据名称、副标题、详情搜索
    Page<ProductEs> findByNameOrSubTitleOrDetailDesc(String name, String subTitle, String detailDesc, Pageable pageable);
    
    // 根据品牌ID搜索
    Page<ProductEs> findByBrandId(Long brandId, Pageable pageable);
    
    // 根据分类ID搜索
    Page<ProductEs> findByProductCategoryId(Long productCategoryId, Pageable pageable);
    
    // 根据价格区间搜索
    Page<ProductEs> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);
    
    // 根据属性值搜索
    Page<ProductEs> findByAttrValues_AttrValue(String attrValue, Pageable pageable);
}