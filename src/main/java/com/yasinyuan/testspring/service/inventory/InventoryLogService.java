package com.yasinyuan.testspring.service.inventory;

import com.yasinyuan.testspring.model.inventory.InventoryLog;

import java.util.List;

public interface InventoryLogService {
    
    /**
     * 根据SKU ID查询库存变更日志
     * @param skuId SKU ID
     * @return 库存变更日志列表
     */
    List<InventoryLog> findBySkuId(Long skuId);
    
    /**
     * 根据仓库编码查询库存变更日志
     * @param warehouseCode 仓库编码
     * @return 库存变更日志列表
     */
    List<InventoryLog> findByWarehouseCode(String warehouseCode);
    
    /**
     * 根据变更类型查询库存变更日志
     * @param changeType 变更类型
     * @return 库存变更日志列表
     */
    List<InventoryLog> findByChangeType(String changeType);
    
    /**
     * 保存库存变更日志
     * @param inventoryLog 库存变更日志
     */
    void save(InventoryLog inventoryLog);
    
    /**
     * 批量保存库存变更日志
     * @param inventoryLogs 库存变更日志列表
     */
    void batchSave(List<InventoryLog> inventoryLogs);
    
    /**
     * 删除库存变更日志
     * @param id 库存变更日志ID
     */
    void deleteById(Long id);
    
    /**
     * 根据SKU ID删除库存变更日志
     * @param skuId SKU ID
     */
    void deleteBySkuId(Long skuId);
}
