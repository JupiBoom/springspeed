package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.dao.ProductAttributeValueMapper;
import com.yasinyuan.testspring.model.ProductAttributeValue;
import com.yasinyuan.testspring.service.ProductAttributeValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 商品属性值 Service实现类
 */
@Service("productAttributeValueService")
public class ProductAttributeValueServiceImpl extends AbstractService<ProductAttributeValue> implements ProductAttributeValueService {
    
    @Resource
    private ProductAttributeValueMapper productAttributeValueMapper;
    
    @Override
    public List<ProductAttributeValue> selectBySpuId(Long spuId) {
        return productAttributeValueMapper.selectBySpuId(spuId);
    }
    
    @Override
    public ProductAttributeValue selectBySpuIdAndAttributeId(Long spuId, Long attributeId) {
        return productAttributeValueMapper.selectBySpuIdAndAttributeId(spuId, attributeId);
    }
    
    @Override
    public boolean batchSave(Long spuId, List<ProductAttributeValue> attributeValues) {
        if (attributeValues == null || attributeValues.isEmpty()) {
            return true;
        }
        // 先删除原有属性值
        productAttributeValueMapper.deleteBySpuId(spuId);
        
        // 批量插入新的属性值
        for (ProductAttributeValue value : attributeValues) {
            value.setSpuId(spuId);
            value.setCreateTime(new Date());
            value.setUpdateTime(new Date());
            value.setDeleted(0);
        }
        return productAttributeValueMapper.batchInsert(attributeValues) > 0;
    }
    
    @Override
    public boolean deleteBySpuId(Long spuId) {
        return productAttributeValueMapper.deleteBySpuId(spuId) > 0;
    }
}