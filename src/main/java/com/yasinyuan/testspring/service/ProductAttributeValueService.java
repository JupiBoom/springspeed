package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.core.Service;
import com.yasinyuan.testspring.model.ProductAttributeValue;

import java.util.List;

/**
 * 商品属性值 Service接口
 */
public interface ProductAttributeValueService extends Service<ProductAttributeValue> {
    
    /**
     * 根据SPU ID查询属性值列表
     * @param spuId SPU ID
     * @return 属性值列表
     */
    List<ProductAttributeValue> selectBySpuId(Long spuId);
    
    /**
     * 根据SPU ID和属性ID查询属性值
     * @param spuId SPU ID
     * @param attributeId 属性ID
     * @return 属性值信息
     */
    ProductAttributeValue selectBySpuIdAndAttributeId(Long spuId, Long attributeId);
    
    /**
     * 批量保存商品属性值
     * @param spuId SPU ID
     * @param attributeValues 属性值列表
     * @return 保存结果
     */
    boolean batchSave(Long spuId, List<ProductAttributeValue> attributeValues);
    
    /**
     * 删除商品的所有属性值
     * @param spuId SPU ID
     * @return 删除结果
     */
    boolean deleteBySpuId(Long spuId);
}