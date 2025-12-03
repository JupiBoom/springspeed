package com.yasinyuan.testspring.service.inventory.impl;

import com.yasinyuan.testspring.dao.inventory.InventoryWarningMapper;
import com.yasinyuan.testspring.model.inventory.InventoryWarning;
import com.yasinyuan.testspring.service.inventory.InventoryWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InventoryWarningServiceImpl implements InventoryWarningService {
    
    @Autowired
    private InventoryWarningMapper inventoryWarningMapper;
    
    @Override
    public List<InventoryWarning> findBySkuId(Long skuId) {
        return inventoryWarningMapper.selectBySkuId(skuId);
    }
    
    @Override
    public InventoryWarning findBySkuIdAndWarehouseCode(Long skuId, String warehouseCode) {
        return inventoryWarningMapper.selectBySkuIdAndWarehouseCode(skuId, warehouseCode);
    }
    
    @Override
    public List<InventoryWarning> findEnabledWarnings() {
        return inventoryWarningMapper.selectEnabledWarnings();
    }
    
    @Override
    public void save(InventoryWarning inventoryWarning) {
        inventoryWarning.setCreateTime(new Date());
        inventoryWarning.setUpdateTime(new Date());
        inventoryWarningMapper.insert(inventoryWarning);
    }
    
    @Override
    public void batchSave(List<InventoryWarning> inventoryWarnings) {
        for (InventoryWarning inventoryWarning : inventoryWarnings) {
            inventoryWarning.setCreateTime(new Date());
            inventoryWarning.setUpdateTime(new Date());
        }
        inventoryWarningMapper.insertList(inventoryWarnings);
    }
    
    @Override
    public void update(InventoryWarning inventoryWarning) {
        inventoryWarning.setUpdateTime(new Date());
        inventoryWarningMapper.updateByPrimaryKeySelective(inventoryWarning);
    }
    
    @Override
    public void deleteById(Long id) {
        inventoryWarningMapper.deleteByPrimaryKey(id);
    }
    
    @Override
    public void deleteBySkuId(Long skuId) {
        // TODO: 实现根据SKU ID删除库存预警设置的逻辑
    }
    
    @Override
    public boolean checkInventoryWarning(Long skuId, String warehouseCode) {
        InventoryWarning warning = inventoryWarningMapper.selectBySkuIdAndWarehouseCode(skuId, warehouseCode);
        if (warning != null && warning.getStatus() == 1) {
            // TODO: 查询当前库存数量并与预警阈值比较
            // 如果当前库存数量 <= 预警阈值，则触发预警
            return false; // 临时返回false，待实现完整逻辑
        }
        return false;
    }
}
