package com.yasinyuan.testspring.dao.product;

import com.yasinyuan.testspring.model.product.ProductAttr;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProductAttrMapper extends Mapper<ProductAttr> {
    
    /**
     * 根据分类ID查询属性列表
     * @param categoryId 分类ID
     * @return 属性列表
     */
    List<ProductAttr> selectByCategoryId(Long categoryId);
    
    /**
     * 根据分类ID和属性类型查询属性列表
     * @param categoryId 分类ID
     * @param type 属性类型：0-规格参数，1-销售属性
     * @return 属性列表
     */
    List<ProductAttr> selectByCategoryIdAndType(@Param("categoryId") Long categoryId, @Param("type") Integer type);
    
    /**
     * 根据ID查询属性详情
     * @param id 属性ID
     * @return 属性详情
     */
    ProductAttr selectById(Long id);
}
