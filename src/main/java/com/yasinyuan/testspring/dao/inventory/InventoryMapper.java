package com.yasinyuan.testspring.dao.inventory;

import com.yasinyuan.testspring.model.inventory.Inventory;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface InventoryMapper extends Mapper<Inventory> {
    
    /**
     * 根据SKU ID查询库存
     * @param skuId SKU ID
     * @return 库存列表
     */
    List<Inventory> selectBySkuId(Long skuId);
    
    /**
     * 根据SKU ID和仓库编码查询库存
     * @param skuId SKU ID
     * @param warehouseCode 仓库编码
     * @return 库存信息
     */
    Inventory selectBySkuIdAndWarehouseCode(Long skuId, String warehouseCode);
    
    /**
     * 批量更新库存数量
     * @param inventories 库存列表
     * @return 更新数量
     */
    int batchUpdateQuantity(List<Inventory> inventories);
}
