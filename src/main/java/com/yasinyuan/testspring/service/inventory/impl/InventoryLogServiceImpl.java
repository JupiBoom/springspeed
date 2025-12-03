package com.yasinyuan.testspring.service.inventory.impl;

import com.yasinyuan.testspring.dao.inventory.InventoryLogMapper;
import com.yasinyuan.testspring.model.inventory.InventoryLog;
import com.yasinyuan.testspring.service.inventory.InventoryLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InventoryLogServiceImpl implements InventoryLogService {
    
    @Autowired
    private InventoryLogMapper inventoryLogMapper;
    
    @Override
    public List<InventoryLog> findBySkuId(Long skuId) {
        return inventoryLogMapper.selectBySkuId(skuId);
    }
    
    @Override
    public List<InventoryLog> findByWarehouseCode(String warehouseCode) {
        return inventoryLogMapper.selectByWarehouseCode(warehouseCode);
    }
    
    @Override
    public List<InventoryLog> findByChangeType(String changeType) {
        return inventoryLogMapper.selectByChangeType(changeType);
    }
    
    @Override
    public void save(InventoryLog inventoryLog) {
        inventoryLog.setCreateTime(new Date());
        inventoryLogMapper.insert(inventoryLog);
    }
    
    @Override
    public void batchSave(List<InventoryLog> inventoryLogs) {
        for (InventoryLog inventoryLog : inventoryLogs) {
            inventoryLog.setCreateTime(new Date());
        }
        inventoryLogMapper.insertList(inventoryLogs);
    }
    
    @Override
    public void deleteById(Long id) {
        inventoryLogMapper.deleteByPrimaryKey(id);
    }
    
    @Override
    public void deleteBySkuId(Long skuId) {
        // TODO: 实现根据SKU ID删除库存变更日志的逻辑
    }
}
