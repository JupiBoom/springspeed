package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.core.Service;
import com.yasinyuan.testspring.model.ProductSku;

import java.util.List;

/**
 * 商品SKU Service接口
 */
public interface ProductSkuService extends Service<ProductSku> {
    
    /**
     * 根据SPU ID查询SKU列表
     * @param spuId SPU ID
     * @return SKU列表
     */
    List<ProductSku> selectBySpuId(Long spuId);
    
    /**
     * 根据SKU编码查询
     * @param skuCode SKU编码
     * @return SKU信息
     */
    ProductSku selectBySkuCode(String skuCode);
    
    /**
     * 更新SKU库存
     * @param id SKU ID
     * @param stock 库存数量
     * @return 更新结果
     */
    boolean updateStock(Long id, Integer stock);
    
    /**
     * 减少SKU库存
     * @param id SKU ID
     * @param quantity 减少数量
     * @return 更新结果
     */
    boolean decreaseStock(Long id, Integer quantity);
    
    /**
     * 增加SKU库存
     * @param id SKU ID
     * @param quantity 增加数量
     * @return 更新结果
     */
    boolean increaseStock(Long id, Integer quantity);
    
    /**
     * 更新SKU状态
     * @param id SKU ID
     * @param status 状态
     * @return 更新结果
     */
    boolean updateStatus(Long id, Integer status);
}