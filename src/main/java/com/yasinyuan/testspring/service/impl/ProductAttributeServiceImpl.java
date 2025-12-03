package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.dao.ProductAttributeMapper;
import com.yasinyuan.testspring.model.ProductAttribute;
import com.yasinyuan.testspring.service.ProductAttributeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品属性 Service实现类
 */
@Service("productAttributeService")
public class ProductAttributeServiceImpl extends AbstractService<ProductAttribute> implements ProductAttributeService {
    
    @Resource
    private ProductAttributeMapper productAttributeMapper;
    
    @Override
    public List<ProductAttribute> selectByCategoryId(Long categoryId) {
        return productAttributeMapper.selectByCategoryId(categoryId);
    }
    
    @Override
    public List<ProductAttribute> selectByCategoryIdAndAttrType(Long categoryId, Integer attrType) {
        return productAttributeMapper.selectByCategoryIdAndAttrType(categoryId, attrType);
    }
    
    @Override
    public List<ProductAttribute> selectEnableAttributes() {
        ProductAttribute query = new ProductAttribute();
        query.setStatus(1);
        return productAttributeMapper.select(query);
    }
}