package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.core.Mapper;
import com.yasinyuan.testspring.model.ProductSku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 商品SKU Mapper
 */
public interface ProductSkuMapper extends Mapper<ProductSku> {
    
    /**
     * 根据SPU ID查询SKU列表
     * @param spuId SPU ID
     * @return SKU列表
     */
    @Select("SELECT * FROM product_sku WHERE spu_id = #{spuId} AND deleted = 0")
    List<ProductSku> selectBySpuId(Long spuId);
    
    /**
     * 根据SKU编码查询SKU
     * @param code SKU编码
     * @return SKU信息
     */
    @Select("SELECT * FROM product_sku WHERE code = #{code} AND deleted = 0")
    ProductSku selectByCode(String code);
    
    /**
     * 根据状态查询SKU列表
     * @param status SKU状态
     * @return SKU列表
     */
    @Select("SELECT * FROM product_sku WHERE status = #{status} AND deleted = 0")
    List<ProductSku> selectByStatus(Integer status);
    
    /**
     * 更新SKU库存
     * @param id SKU ID
     * @param stock 库存数量
     * @return 更新结果
     */
    @Update("UPDATE product_sku SET stock = #{stock} WHERE id = #{id}")
    int updateStock(@Param("id") Long id, @Param("stock") Integer stock);
    
    /**
     * 更新SKU预警库存
     * @param id SKU ID
     * @param warningStock 预警库存数量
     * @return 更新结果
     */
    @Update("UPDATE product_sku SET warning_stock = #{warningStock} WHERE id = #{id}")
    int updateWarningStock(@Param("id") Long id, @Param("warningStock") Integer warningStock);
    
    /**
     * 更新SKU状态
     * @param id SKU ID
     * @param status SKU状态
     * @return 更新结果
     */
    @Update("UPDATE product_sku SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}