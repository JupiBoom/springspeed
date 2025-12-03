package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.core.Mapper;
import com.yasinyuan.testspring.model.ProductAttribute;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品属性 Mapper
 */
public interface ProductAttributeMapper extends Mapper<ProductAttribute> {
    
    /**
     * 根据分类ID查询属性列表
     * @param categoryId 分类ID
     * @return 属性列表
     */
    @Select("SELECT * FROM product_attribute WHERE category_id = #{categoryId} AND status = 1 AND deleted = 0 ORDER BY sort ASC")
    List<ProductAttribute> selectByCategoryId(Long categoryId);
    
    /**
     * 根据属性类型查询属性列表
     * @param type 属性类型：0-规格参数，1-销售属性
     * @return 属性列表
     */
    @Select("SELECT * FROM product_attribute WHERE type = #{type} AND status = 1 AND deleted = 0 ORDER BY sort ASC")
    List<ProductAttribute> selectByType(Integer type);
}