package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.core.Mapper;
import com.yasinyuan.testspring.model.Product;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProductMapper extends Mapper<Product> {
    
    // 根据品牌ID查询商品
    List<Product> selectByBrandId(@Param("brandId") Long brandId);
    
    // 根据分类ID查询商品
    List<Product> selectByCategoryId(@Param("categoryId") Long categoryId);
    
    // 根据上架状态查询商品
    List<Product> selectByPublishStatus(@Param("publishStatus") Integer publishStatus);
    
    // 根据审核状态查询商品
    List<Product> selectByVerifyStatus(@Param("verifyStatus") Integer verifyStatus);
    
    // 库存预警商品查询
    List<Product> selectLowStockProducts();

    // 数据分析相关方法
    List<Map<String, Object>> selectProductSalesRanking(@Param("topN") Integer topN, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<Map<String, Object>> selectInventoryTurnover(@Param("categoryId") Long categoryId, @Param("brandId") Long brandId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    Map<String, Object> selectProductShelfStats(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<Map<String, Object>> selectProductSalesTrend(@Param("productId") Long productId, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("periodType") String periodType);
}