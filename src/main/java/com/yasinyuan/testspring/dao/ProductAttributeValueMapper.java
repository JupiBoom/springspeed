package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.core.Mapper;
import com.yasinyuan.testspring.model.ProductAttributeValue;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品属性值 Mapper
 */
public interface ProductAttributeValueMapper extends Mapper<ProductAttributeValue> {
    
    /**
     * 根据SPU ID查询属性值列表
     * @param spuId SPU ID
     * @return 属性值列表
     */
    @Select("SELECT * FROM product_attribute_value WHERE spu_id = #{spuId} AND deleted = 0")
    List<ProductAttributeValue> selectBySpuId(Long spuId);
    
    /**
     * 根据SPU ID和属性ID查询属性值
     * @param spuId SPU ID
     * @param attributeId 属性ID
     * @return 属性值信息
     */
    @Select("SELECT * FROM product_attribute_value WHERE spu_id = #{spuId} AND attribute_id = #{attributeId} AND deleted = 0")
    ProductAttributeValue selectBySpuIdAndAttributeId(@Param("spuId") Long spuId, @Param("attributeId") Long attributeId);
    
    /**
     * 批量插入商品属性值
     * @param list 属性值列表
     * @return 插入结果
     */
    @Insert({"<script>",
            "INSERT INTO product_attribute_value (spu_id, attribute_id, value, create_time, update_time, create_by, update_by) ",
            "VALUES ",
            "<foreach collection='list' item='item' separator=','>",
            "(#{item.spuId}, #{item.attributeId}, #{item.value}, #{item.createTime}, #{item.updateTime}, #{item.createBy}, #{item.updateBy})",
            "</foreach>",
            "</script>"})
    int batchInsert(@Param("list") List<ProductAttributeValue> list);
    
    /**
     * 根据SPU ID删除属性值
     * @param spuId SPU ID
     * @return 删除结果
     */
    @Delete("DELETE FROM product_attribute_value WHERE spu_id = #{spuId}")
    int deleteBySpuId(Long spuId);
}