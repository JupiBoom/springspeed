package com.yasinyuan.testspring.dao.inventory;

import com.yasinyuan.testspring.model.inventory.InventoryWarning;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface InventoryWarningMapper extends Mapper<InventoryWarning> {
    
    /**
     * 根据SKU ID查询库存预警设置
     * @param skuId SKU ID
     * @return 库存预警设置列表
     */
    List<InventoryWarning> selectBySkuId(Long skuId);
    
    /**
     * 根据SKU ID和仓库编码查询库存预警设置
     * @param skuId SKU ID
     * @param warehouseCode 仓库编码
     * @return 库存预警设置信息
     */
    InventoryWarning selectBySkuIdAndWarehouseCode(Long skuId, String warehouseCode);
    
    /**
     * 查询启用状态的库存预警设置
     * @return 库存预警设置列表
     */
    List<InventoryWarning> selectEnabledWarnings();
}
