package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.core.Mapper;
import com.yasinyuan.testspring.model.ProductSku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuMapper extends Mapper<ProductSku> {
    
    // 根据商品ID查询SKU列表
    List<ProductSku> selectByProductId(@Param("productId") Long productId);
    
    // 库存预警SKU查询
    List<ProductSku> selectLowStockSkus();
    
    // 更新SKU库存
    int updateStock(@Param("skuId") Long skuId, @Param("stock") Integer stock);
    
    // 增减SKU库存
    int incrStock(@Param("skuId") Long skuId, @Param("count") Integer count);
}