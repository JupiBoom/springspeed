package com.yasinyuan.testspring.service.search.impl;

import com.yasinyuan.testspring.dao.search.ProductSearchRepository;
import com.yasinyuan.testspring.model.search.ProductSearch;
import com.yasinyuan.testspring.service.search.ProductSearchService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {
    
    @Autowired
    private ProductSearchRepository productSearchRepository;
    
    @Override
    public void initProductIndex() {
        // TODO: 从数据库中查询所有商品数据，然后批量更新到Elasticsearch索引中
    }
    
    @Override
    public void updateProductIndex(ProductSearch productSearch) {
        productSearchRepository.save(productSearch);
    }
    
    @Override
    public void batchUpdateProductIndex(List<ProductSearch> productSearches) {
        productSearchRepository.save(productSearches);
    }
    
    @Override
    public void deleteProductIndex(Long id) {
        productSearchRepository.delete(id);
    }
    
    @Override
    public Page<ProductSearch> searchByKeyword(String keyword, Pageable pageable) {
        return productSearchRepository.findByNameOrDescription(keyword, keyword, pageable);
    }
    
    @Override
    public Page<ProductSearch> searchByBrandId(Long brandId, Pageable pageable) {
        return productSearchRepository.findByBrandId(brandId, pageable);
    }
    
    @Override
    public Page<ProductSearch> searchByCategoryId(Long categoryId, Pageable pageable) {
        return productSearchRepository.findByCategoryId(categoryId, pageable);
    }
    
    @Override
    public Page<ProductSearch> searchByPriceRange(Double minPrice, Double maxPrice, Pageable pageable) {
        return productSearchRepository.findByPriceBetween(minPrice, maxPrice, pageable);
    }
    
    @Override
    public Page<ProductSearch> searchByMultiCondition(String keyword, Long brandId, Long categoryId, Double minPrice, Double maxPrice, Pageable pageable) {
        // 构建布尔查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        
        // 关键词查询
        if (keyword != null && !keyword.isEmpty()) {
            boolQuery.must(QueryBuilders.multiMatchQuery(keyword, "name", "description"));
        }
        
        // 品牌ID查询
        if (brandId != null) {
            boolQuery.filter(QueryBuilders.termQuery("brandId", brandId));
        }
        
        // 分类ID查询
        if (categoryId != null) {
            boolQuery.filter(QueryBuilders.termQuery("categoryId", categoryId));
        }
        
        // 价格区间查询
        if (minPrice != null && maxPrice != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").gte(minPrice).lte(maxPrice));
        } else if (minPrice != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").gte(minPrice));
        } else if (maxPrice != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").lte(maxPrice));
        }
        
        // 构建本地搜索查询
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(boolQuery);
        queryBuilder.withPageable(pageable);
        
        // 执行查询
        return productSearchRepository.search(queryBuilder.build());
    }
}
