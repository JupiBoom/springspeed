package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.model.*;
import com.yasinyuan.testspring.repository.ProductSearchRepository;
import com.yasinyuan.testspring.service.*;
import com.yasinyuan.testspring.tools.PageBean;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品搜索 Service实现类
 */
@Service("productSearchService")
public class ProductSearchServiceImpl implements ProductSearchService {
    
    @Autowired
    private ProductSearchRepository productSearchRepository;
    
    @Autowired
    private ProductSpuService productSpuService;
    
    @Autowired
    private ProductSkuService productSkuService;
    
    @Autowired
    private ProductAttributeValueService productAttributeValueService;
    
    @Autowired
    private BrandService brandService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Override
    public ProductSearch save(ProductSearch productSearch) {
        return productSearchRepository.save(productSearch);
    }
    
    @Override
    public void deleteById(Long id) {
        productSearchRepository.delete(id);
    }
    
    @Override
    public Iterable<ProductSearch> saveAll(Iterable<ProductSearch> productSearchList) {
        return productSearchRepository.save(productSearchList);
    }
    
    @Override
    public void deleteAllById(Iterable<Long> ids) {
        productSearchRepository.deleteAll(ids);
    }
    
    @Override
    public ProductSearch findById(Long id) {
        return productSearchRepository.findOne(id);
    }
    
    @Override
    public PageBean<ProductSearch> search(String keyword, Long brandId, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, Integer page, Integer size) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        
        // 关键词搜索
        if (keyword != null && !keyword.isEmpty()) {
            boolQuery.must(QueryBuilders.multiMatchQuery(keyword, "name", "description", "brandName", "categoryName"));
        }
        
        // 品牌过滤
        if (brandId != null) {
            boolQuery.filter(QueryBuilders.termQuery("brandId", brandId));
        }
        
        // 分类过滤
        if (categoryId != null) {
            boolQuery.filter(QueryBuilders.termQuery("categoryId", categoryId));
        }
        
        // 价格区间过滤
        if (minPrice != null && maxPrice != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").gte(minPrice.doubleValue()).lte(maxPrice.doubleValue()));
        } else if (minPrice != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").gte(minPrice.doubleValue()));
        } else if (maxPrice != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").lte(maxPrice.doubleValue()));
        }
        
        // 只搜索上架商品
        boolQuery.filter(QueryBuilders.termQuery("status", 1));
        
        queryBuilder.withQuery(boolQuery)
                .withSort(SortBuilders.fieldSort("sales").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC))
                .withPageable(PageRequest.of(page - 1, size));
        
        Page<ProductSearch> productPage = productSearchRepository.search(queryBuilder.build());
        
        return new PageBean<>(productPage.getTotalElements(), productPage.getContent());
    }
    
    @Override
    public PageBean<ProductSearch> searchByName(String name, Integer page, Integer size) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        
        boolQuery.must(QueryBuilders.matchQuery("name", name));
        boolQuery.filter(QueryBuilders.termQuery("status", 1));
        
        queryBuilder.withQuery(boolQuery)
                .withSort(SortBuilders.fieldSort("sales").order(SortOrder.DESC))
                .withPageable(PageRequest.of(page - 1, size));
        
        Page<ProductSearch> productPage = productSearchRepository.search(queryBuilder.build());
        
        return new PageBean<>(productPage.getTotalElements(), productPage.getContent());
    }
    
    @Override
    public PageBean<ProductSearch> searchByBrand(Long brandId, Integer page, Integer size) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        
        boolQuery.filter(QueryBuilders.termQuery("brandId", brandId));
        boolQuery.filter(QueryBuilders.termQuery("status", 1));
        
        queryBuilder.withQuery(boolQuery)
                .withSort(SortBuilders.fieldSort("sales").order(SortOrder.DESC))
                .withPageable(PageRequest.of(page - 1, size));
        
        Page<ProductSearch> productPage = productSearchRepository.search(queryBuilder.build());
        
        return new PageBean<>(productPage.getTotalElements(), productPage.getContent());
    }
    
    @Override
    public PageBean<ProductSearch> searchByCategory(Long categoryId, Integer page, Integer size) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        
        boolQuery.filter(QueryBuilders.termQuery("categoryId", categoryId));
        boolQuery.filter(QueryBuilders.termQuery("status", 1));
        
        queryBuilder.withQuery(boolQuery)
                .withSort(SortBuilders.fieldSort("sales").order(SortOrder.DESC))
                .withPageable(PageRequest.of(page - 1, size));
        
        Page<ProductSearch> productPage = productSearchRepository.search(queryBuilder.build());
        
        return new PageBean<>(productPage.getTotalElements(), productPage.getContent());
    }
    
    @Override
    public PageBean<ProductSearch> searchByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Integer page, Integer size) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        
        boolQuery.filter(QueryBuilders.rangeQuery("price").gte(minPrice.doubleValue()).lte(maxPrice.doubleValue()));
        boolQuery.filter(QueryBuilders.termQuery("status", 1));
        
        queryBuilder.withQuery(boolQuery)
                .withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC))
                .withPageable(PageRequest.of(page - 1, size));
        
        Page<ProductSearch> productPage = productSearchRepository.search(queryBuilder.build());
        
        return new PageBean<>(productPage.getTotalElements(), productPage.getContent());
    }
    
    @Override
    public PageBean<ProductSearch> findAllEnabled(Integer page, Integer size) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        
        boolQuery.filter(QueryBuilders.termQuery("status", 1));
        
        queryBuilder.withQuery(boolQuery)
                .withSort(SortBuilders.fieldSort("sales").order(SortOrder.DESC))
                .withPageable(PageRequest.of(page - 1, size));
        
        Page<ProductSearch> productPage = productSearchRepository.search(queryBuilder.build());
        
        return new PageBean<>(productPage.getTotalElements(), productPage.getContent());
    }
    
    @Override
    public boolean rebuildIndex() {
        try {
            // 清空现有索引
            productSearchRepository.deleteAll();
            
            // 获取所有上架商品
            ProductSpu spuQuery = new ProductSpu();
            spuQuery.setStatus(1);
            List<ProductSpu> spuList = productSpuService.select(spuQuery);
            
            // 转换为搜索模型
            List<ProductSearch> searchList = spuList.stream()
                    .map(this::convertToSearchModel)
                    .collect(Collectors.toList());
            
            // 批量保存
            productSearchRepository.save(searchList);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 将ProductSpu转换为ProductSearch
     * @param spu 商品SPU
     * @return 商品搜索对象
     */
    private ProductSearch convertToSearchModel(ProductSpu spu) {
        ProductSearch search = new ProductSearch();
        search.setId(spu.getId());
        search.setSpuId(spu.getId());
        search.setName(spu.getName());
        search.setDescription(spu.getDescription());
        search.setBrandId(spu.getBrandId());
        search.setCategoryId(spu.getCategoryId());
        search.setMainImage(spu.getMainImage());
        search.setStatus(spu.getStatus());
        search.setCreateTime(spu.getCreateTime());
        search.setUpdateTime(spu.getUpdateTime());
        search.setTags(spu.getTags());
        
        // 获取品牌名称
        if (spu.getBrandId() != null) {
            Brand brand = brandService.selectById(spu.getBrandId());
            if (brand != null) {
                search.setBrandName(brand.getName());
            }
        }
        
        // 获取分类名称
        if (spu.getCategoryId() != null) {
            Category category = categoryService.selectById(spu.getCategoryId());
            if (category != null) {
                search.setCategoryName(category.getName());
            }
        }
        
        // 获取SKU价格和库存（取第一个SKU或最低价格）
        List<ProductSku> skuList = productSkuService.selectBySpuId(spu.getId());
        if (skuList != null && !skuList.isEmpty()) {
            // 取第一个SKU的价格
            search.setPrice(skuList.get(0).getPrice());
            // 计算总库存
            Integer totalStock = skuList.stream()
                    .mapToInt(ProductSku::getStock)
                    .sum();
            search.setStock(totalStock);
        }
        
        // 获取销量
        search.setSales(spu.getSales() != null ? spu.getSales() : 0);
        
        // 获取商品属性
        List<ProductAttributeValue> attributeValues = productAttributeValueService.selectBySpuId(spu.getId());
        if (attributeValues != null && !attributeValues.isEmpty()) {
            List<ProductSearch.ProductSearchAttribute> attributes = new ArrayList<>();
            for (ProductAttributeValue value : attributeValues) {
                ProductSearch.ProductSearchAttribute attr = new ProductSearch.ProductSearchAttribute();
                attr.setAttributeId(value.getAttributeId());
                attr.setAttributeName(value.getAttributeName());
                attr.setAttributeValue(value.getAttributeValue());
                attr.setValue(value.getAttributeValue());
                attributes.add(attr);
            }
            search.setAttributes(attributes);
        }
        
        return search;
    }
}