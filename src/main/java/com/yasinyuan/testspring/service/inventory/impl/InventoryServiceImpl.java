package com.yasinyuan.testspring.service.inventory.impl;

import com.yasinyuan.testspring.dao.inventory.InventoryMapper;
import com.yasinyuan.testspring.model.inventory.Inventory;
import com.yasinyuan.testspring.service.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {
    
    @Autowired
    private InventoryMapper inventoryMapper;
    
    @Override
    public List<Inventory> findBySkuId(Long skuId) {
        return inventoryMapper.selectBySkuId(skuId);
    }
    
    @Override
    public Inventory findBySkuIdAndWarehouseCode(Long skuId, String warehouseCode) {
        return inventoryMapper.selectBySkuIdAndWarehouseCode(skuId, warehouseCode);
    }
    
    @Override
    public void save(Inventory inventory) {
        inventory.setCreateTime(new Date());
        inventory.setUpdateTime(new Date());
        inventoryMapper.insert(inventory);
    }
    
    @Override
    public void batchSave(List<Inventory> inventories) {
        for (Inventory inventory : inventories) {
            inventory.setCreateTime(new Date());
            inventory.setUpdateTime(new Date());
        }
        inventoryMapper.insertList(inventories);
    }
    
    @Override
    public void update(Inventory inventory) {
        inventory.setUpdateTime(new Date());
        inventoryMapper.updateByPrimaryKeySelective(inventory);
    }
    
    @Override
    public void batchUpdateQuantity(List<Inventory> inventories) {
        for (Inventory inventory : inventories) {
            inventory.setUpdateTime(new Date());
        }
        inventoryMapper.batchUpdateQuantity(inventories);
    }
    
    @Override
    public boolean deductInventory(Long skuId, String warehouseCode, Integer quantity) {
        Inventory inventory = inventoryMapper.selectBySkuIdAndWarehouseCode(skuId, warehouseCode);
        if (inventory != null && inventory.getQuantity() >= quantity) {
            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventory.setUpdateTime(new Date());
            inventoryMapper.updateByPrimaryKeySelective(inventory);
            return true;
        }
        return false;
    }
    
    @Override
    public void addInventory(Long skuId, String warehouseCode, Integer quantity) {
        Inventory inventory = inventoryMapper.selectBySkuIdAndWarehouseCode(skuId, warehouseCode);
        if (inventory != null) {
            inventory.setQuantity(inventory.getQuantity() + quantity);
            inventory.setUpdateTime(new Date());
            inventoryMapper.updateByPrimaryKeySelective(inventory);
        } else {
            Inventory newInventory = new Inventory();
            newInventory.setSkuId(skuId);
            newInventory.setWarehouseCode(warehouseCode);
            newInventory.setQuantity(quantity);
            newInventory.setCreateTime(new Date());
            newInventory.setUpdateTime(new Date());
            inventoryMapper.insert(newInventory);
        }
    }
    
    @Override
    public void deleteById(Long id) {
        inventoryMapper.deleteByPrimaryKey(id);
    }
    
    @Override
    public void deleteBySkuId(Long skuId) {
        // TODO: 实现根据SKU ID删除库存信息的逻辑
    }
}
