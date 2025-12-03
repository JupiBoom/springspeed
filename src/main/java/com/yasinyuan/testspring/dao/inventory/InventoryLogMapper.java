package com.yasinyuan.testspring.dao.inventory;

import com.yasinyuan.testspring.model.inventory.InventoryLog;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface InventoryLogMapper extends Mapper<InventoryLog> {
    
    /**
     * 根据SKU ID查询库存变更日志
     * @param skuId SKU ID
     * @return 库存变更日志列表
     */
    List<InventoryLog> selectBySkuId(Long skuId);
    
    /**
     * 根据仓库编码查询库存变更日志
     * @param warehouseCode 仓库编码
     * @return 库存变更日志列表
     */
    List<InventoryLog> selectByWarehouseCode(String warehouseCode);
    
    /**
     * 根据变更类型查询库存变更日志
     * @param changeType 变更类型
     * @return 库存变更日志列表
     */
    List<InventoryLog> selectByChangeType(String changeType);
}
