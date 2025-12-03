package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.core.Service;
import com.yasinyuan.testspring.model.ProductAttribute;

import java.util.List;

/**
 * 商品属性 Service接口
 */
public interface ProductAttributeService extends Service<ProductAttribute> {
    
    /**
     * 根据分类ID查询属性列表
     * @param categoryId 分类ID
     * @return 属性列表
     */
    List<ProductAttribute> selectByCategoryId(Long categoryId);
    
    /**
     * 根据分类ID和属性类型查询属性列表
     * @param categoryId 分类ID
     * @param attrType 属性类型（0-规格参数，1-销售属性）
     * @return 属性列表
     */
    List<ProductAttribute> selectByCategoryIdAndAttrType(Long categoryId, Integer attrType);
    
    /**
     * 查询启用的属性列表
     * @return 属性列表
     */
    List<ProductAttribute> selectEnableAttributes();
}