package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.core.Service;
import com.yasinyuan.testspring.model.ProductSpu;

import java.util.List;

/**
 * 商品SPU Service接口
 */
public interface ProductSpuService extends Service<ProductSpu> {
    
    /**
     * 根据品牌ID查询商品列表
     * @param brandId 品牌ID
     * @return 商品列表
     */
    List<ProductSpu> selectByBrandId(Long brandId);
    
    /**
     * 根据分类ID查询商品列表
     * @param categoryId 分类ID
     * @return 商品列表
     */
    List<ProductSpu> selectByCategoryId(Long categoryId);
    
    /**
     * 根据状态查询商品列表
     * @param status 商品状态
     * @return 商品列表
     */
    List<ProductSpu> selectByStatus(Integer status);
    
    /**
     * 上架商品
     * @param id 商品ID
     * @return 上架结果
     */
    boolean onSale(Long id);
    
    /**
     * 下架商品
     * @param id 商品ID
     * @return 下架结果
     */
    boolean offSale(Long id);
    
    /**
     * 审核商品
     * @param id 商品ID
     * @param auditStatus 审核状态
     * @param auditRemark 审核备注
     * @return 审核结果
     */
    boolean audit(Long id, Integer auditStatus, String auditRemark);
}