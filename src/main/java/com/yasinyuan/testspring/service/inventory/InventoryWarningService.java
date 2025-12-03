package com.yasinyuan.testspring.service.inventory;

import com.yasinyuan.testspring.model.inventory.InventoryWarning;

import java.util.List;

public interface InventoryWarningService {
    
    /**
     * 根据SKU ID查询库存预警设置
     * @param skuId SKU ID
     * @return 库存预警设置列表
     */
    List<InventoryWarning> findBySkuId(Long skuId);
    
    /**
     * 根据SKU ID和仓库编码查询库存预警设置
     * @param skuId SKU ID
     * @param warehouseCode 仓库编码
     * @return 库存预警设置信息
     */
    InventoryWarning findBySkuIdAndWarehouseCode(Long skuId, String warehouseCode);
    
    /**
     * 查询启用状态的库存预警设置
     * @return 库存预警设置列表
     */
    List<InventoryWarning> findEnabledWarnings();
    
    /**
     * 保存库存预警设置
     * @param inventoryWarning 库存预警设置
     */
    void save(InventoryWarning inventoryWarning);
    
    /**
     * 批量保存库存预警设置
     * @param inventoryWarnings 库存预警设置列表
     */
    void batchSave(List<InventoryWarning> inventoryWarnings);
    
    /**
     * 更新库存预警设置
     * @param inventoryWarning 库存预警设置
     */
    void update(InventoryWarning inventoryWarning);
    
    /**
     * 删除库存预警设置
     * @param id 库存预警设置ID
     */
    void deleteById(Long id);
    
    /**
     * 根据SKU ID删除库存预警设置
     * @param skuId SKU ID
     */
    void deleteBySkuId(Long skuId);
    
    /**
     * 检查库存预警
     * @param skuId SKU ID
     * @param warehouseCode 仓库编码
     * @return 是否触发预警
     */
    boolean checkInventoryWarning(Long skuId, String warehouseCode);
}
