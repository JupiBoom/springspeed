package com.yasinyuan.testspring.service.inventory;

import com.yasinyuan.testspring.model.inventory.Inventory;

import java.util.List;

public interface InventoryService {
    
    /**
     * 根据SKU ID查询库存
     * @param skuId SKU ID
     * @return 库存列表
     */
    List<Inventory> findBySkuId(Long skuId);
    
    /**
     * 根据SKU ID和仓库编码查询库存
     * @param skuId SKU ID
     * @param warehouseCode 仓库编码
     * @return 库存信息
     */
    Inventory findBySkuIdAndWarehouseCode(Long skuId, String warehouseCode);
    
    /**
     * 保存库存信息
     * @param inventory 库存信息
     */
    void save(Inventory inventory);
    
    /**
     * 批量保存库存信息
     * @param inventories 库存列表
     */
    void batchSave(List<Inventory> inventories);
    
    /**
     * 更新库存信息
     * @param inventory 库存信息
     */
    void update(Inventory inventory);
    
    /**
     * 批量更新库存数量
     * @param inventories 库存列表
     */
    void batchUpdateQuantity(List<Inventory> inventories);
    
    /**
     * 扣减库存
     * @param skuId SKU ID
     * @param warehouseCode 仓库编码
     * @param quantity 扣减数量
     * @return 扣减是否成功
     */
    boolean deductInventory(Long skuId, String warehouseCode, Integer quantity);
    
    /**
     * 增加库存
     * @param skuId SKU ID
     * @param warehouseCode 仓库编码
     * @param quantity 增加数量
     */
    void addInventory(Long skuId, String warehouseCode, Integer quantity);
    
    /**
     * 删除库存信息
     * @param id 库存ID
     */
    void deleteById(Long id);
    
    /**
     * 根据SKU ID删除库存信息
     * @param skuId SKU ID
     */
    void deleteBySkuId(Long skuId);
}
