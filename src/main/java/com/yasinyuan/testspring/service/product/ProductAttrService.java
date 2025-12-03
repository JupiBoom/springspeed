package com.yasinyuan.testspring.service.product;

import com.yasinyuan.testspring.model.product.ProductAttr;

import java.util.List;

public interface ProductAttrService {
    
    /**
     * 根据分类ID查询属性列表
     * @param categoryId 分类ID
     * @return 属性列表
     */
    List<ProductAttr> findByCategoryId(Long categoryId);
    
    /**
     * 根据分类ID和属性类型查询属性列表
     * @param categoryId 分类ID
     * @param type 属性类型：0-规格参数，1-销售属性
     * @return 属性列表
     */
    List<ProductAttr> findByCategoryIdAndType(Long categoryId, Integer type);
    
    /**
     * 根据ID查询属性详情
     * @param id 属性ID
     * @return 属性详情
     */
    ProductAttr findById(Long id);
    
    /**
     * 保存属性
     * @param productAttr 属性信息
     * @return 保存结果
     */
    boolean save(ProductAttr productAttr);
    
    /**
     * 更新属性
     * @param productAttr 属性信息
     * @return 更新结果
     */
    boolean update(ProductAttr productAttr);
    
    /**
     * 根据ID删除属性
     * @param id 属性ID
     * @return 删除结果
     */
    boolean deleteById(Long id);
    
    /**
     * 根据分类ID删除属性
     * @param categoryId 分类ID
     * @return 删除结果
     */
    boolean deleteByCategoryId(Long categoryId);
}
