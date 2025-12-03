package com.yasinyuan.testspring.service.product;

import com.yasinyuan.testspring.model.product.Sku;

import java.util.List;

public interface SkuService {
    
    /**
     * 根据SPU ID查询SKU列表
     * @param spuId SPU ID
     * @return SKU列表
     */
    List<Sku> findBySpuId(Long spuId);
    
    /**
     * 根据ID查询SKU详情
     * @param id SKU ID
     * @return SKU详情
     */
    Sku findById(Long id);
    
    /**
     * 保存SKU
     * @param sku SKU信息
     * @return 保存结果
     */
    boolean save(Sku sku);
    
    /**
     * 批量保存SKU
     * @param skus SKU列表
     * @return 保存结果
     */
    boolean batchSave(List<Sku> skus);
    
    /**
     * 更新SKU
     * @param sku SKU信息
     * @return 更新结果
     */
    boolean update(Sku sku);
    
    /**
     * 批量更新SKU库存
     * @param skus SKU列表
     * @return 更新结果
     */
    boolean batchUpdateStock(List<Sku> skus);
    
    /**
     * 批量更新SKU状态
     * @param ids SKU ID列表
     * @param status 状态
     * @return 更新结果
     */
    boolean batchUpdateStatus(List<Long> ids, Integer status);
    
    /**
     * 根据ID删除SKU
     * @param id SKU ID
     * @return 删除结果
     */
    boolean deleteById(Long id);
    
    /**
     * 根据SPU ID删除SKU
     * @param spuId SPU ID
     * @return 删除结果
     */
    boolean deleteBySpuId(Long spuId);
}
