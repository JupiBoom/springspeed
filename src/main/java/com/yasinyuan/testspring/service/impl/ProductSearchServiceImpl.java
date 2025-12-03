package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.elasticsearch.ProductEs;
import com.yasinyuan.testspring.elasticsearch.ProductEsRepository;
import com.yasinyuan.testspring.model.Product;
import com.yasinyuan.testspring.service.ProductSearchService;
import com.yasinyuan.testspring.service.ProductService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    @Autowired
    private ProductEsRepository productEsRepository;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public int importAll() {
        List<Product> productList = productService.findAll();
        List<ProductEs> productEsList = new ArrayList<>();
        for (Product product : productList) {
            ProductEs productEs = new ProductEs();
            productEs.setId(product.getId());
            productEs.setName(product.getName());
            productEs.setSubTitle(product.getSubTitle());
            productEs.setDetailDesc(product.getDetailDesc());
            productEs.setBrandId(product.getBrandId());
            productEs.setProductCategoryId(product.getProductCategoryId());
            productEs.setPrice(product.getOriginalPrice());
            productEs.setStock(product.getStock());
            productEs.setSale(product.getSale());
            productEs.setSort(product.getSort());
            productEs.setPublishStatus(product.getPublishStatus());
            productEs.setPic(product.getMainPic());
            productEs.setCreateTime(product.getCreateTime());
            productEsList.add(productEs);
        }
        productEsRepository.saveAll(productEsList);
        return productEsList.size();
    }

    @Override
    public void delete(Long id) {
        productEsRepository.deleteById(id);
    }

    @Override
    public ProductEs create(Long id) {
        Product product = productService.findById(id != null ? id.intValue() : null);
        if(product == null) {
            return null;
        }
        ProductEs productEs = new ProductEs();
        productEs.setId(product.getId());
        productEs.setName(product.getName());
        productEs.setSubTitle(product.getSubTitle());
        productEs.setDetailDesc(product.getDetailDesc());
        productEs.setBrandId(product.getBrandId());
        productEs.setProductCategoryId(product.getProductCategoryId());
        productEs.setPrice(product.getOriginalPrice());
        productEs.setStock(product.getStock());
        productEs.setSale(product.getSale());
        productEs.setSort(product.getSort());
        productEs.setPublishStatus(product.getPublishStatus());
        productEs.setPic(product.getMainPic());
        productEs.setCreateTime(product.getCreateTime());
        return productEsRepository.save(productEs);
    }

    @Override
    public void delete(List<Long> ids) {
        if (!ids.isEmpty()) {
            List<ProductEs> productEsList = new ArrayList<>();
            for (Long id : ids) {
                ProductEs productEs = new ProductEs();
                productEs.setId(id);
                productEsList.add(productEs);
            }
            productEsRepository.deleteAll(productEsList);
        }
    }

    @Override
    public Page<ProductEs> search(String keyword, Pageable pageable) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (keyword != null && !keyword.isEmpty()) {
            MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("name", keyword)
                    .operator(MatchQueryBuilder.Operator.AND);
            boolQuery.must(matchQuery);
        }
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withPageable(pageable)
                .build();
        return elasticsearchTemplate.queryForPage(searchQuery, ProductEs.class);
    }

    @Override
    public Page<ProductEs> search(String keyword, Long brandId, Long productCategoryId, 
                                  Double minPrice, Double maxPrice, String attrValue, Pageable pageable) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        
        // 关键词搜索
        if (keyword != null && !keyword.isEmpty()) {
            MatchQueryBuilder matchQuery = QueryBuilders.multiMatchQuery(keyword, "name", "subTitle", "detailDesc")
                    .operator(MatchQueryBuilder.Operator.AND);
            boolQuery.must(matchQuery);
        }
        
        // 品牌筛选
        if (brandId != null) {
            boolQuery.filter(QueryBuilders.termQuery("brandId", brandId));
        }
        
        // 分类筛选
        if (productCategoryId != null) {
            boolQuery.filter(QueryBuilders.termQuery("productCategoryId", productCategoryId));
        }
        
        // 价格区间筛选
        if (minPrice != null && maxPrice != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").from(minPrice).to(maxPrice));
        } else if (minPrice != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").gte(minPrice));
        } else if (maxPrice != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").lte(maxPrice));
        }
        
        // 属性筛选
        if (attrValue != null && !attrValue.isEmpty()) {
            boolQuery.filter(QueryBuilders.termQuery("attrValues.attrValue", attrValue));
        }
        
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withPageable(pageable)
                .build();
        return elasticsearchTemplate.queryForPage(searchQuery, ProductEs.class);
    }
}