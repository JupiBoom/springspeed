package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.core.Service;
import com.yasinyuan.testspring.model.Product;

import java.util.List;

public interface ProductService extends Service<Product> {
    
    // 根据品牌ID查询商品
    List<Product> selectByBrandId(Long brandId);
    
    // 根据分类ID查询商品
    List<Product> selectByCategoryId(Long categoryId);
    
    // 根据上架状态查询商品
    List<Product> selectByPublishStatus(Integer publishStatus);
    
    // 根据审核状态查询商品
    List<Product> selectByVerifyStatus(Integer verifyStatus);
    
    // 商品上架
    boolean publishProduct(Long id);
    
    // 商品下架
    boolean unpublishProduct(Long id);
    
    // 审核商品
    boolean verifyProduct(Long id, Integer verifyStatus);
    
    // 获取库存预警商品
    List<Product> getLowStockProducts();
    
    // 更新商品库存
    boolean updateStock(Long id, Integer stock);
}