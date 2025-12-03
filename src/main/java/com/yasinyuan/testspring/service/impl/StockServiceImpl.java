package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.mapper.StockChangeLogMapper;
import com.yasinyuan.testspring.mapper.StockMapper;
import com.yasinyuan.testspring.model.Stock;
import com.yasinyuan.testspring.model.StockChangeLog;
import com.yasinyuan.testspring.service.StockService;
import com.yasinyuan.testspring.tools.PageBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存Service实现类
 */
@Service("stockService")
public class StockServiceImpl extends AbstractService<Stock> implements StockService {
    
    @Resource
    private StockMapper stockMapper;
    
    @Resource
    private StockChangeLogMapper stockChangeLogMapper;
    
    @Override
    public Stock selectByWarehouseAndSku(Long warehouseId, Long productSkuId) {
        return stockMapper.selectByWarehouseAndSku(warehouseId, productSkuId);
    }
    
    @Override
    public List<Stock> selectByProductSkuId(Long productSkuId) {
        return stockMapper.selectByProductSkuId(productSkuId);
    }
    
    @Override
    public List<Stock> selectByWarehouseId(Long warehouseId) {
        return stockMapper.selectByWarehouseId(warehouseId);
    }
    
    @Override
    public List<Stock> selectWarningStock() {
        return stockMapper.selectWarningStock();
    }
    
    @Override
    public List<Stock> selectOverstock(Integer threshold) {
        return stockMapper.selectOverstock(threshold);
    }
    
    @Override
    public Integer selectTotalStockBySkuId(Long productSkuId) {
        return stockMapper.selectTotalStockBySkuId(productSkuId);
    }
    
    @Override
    public Integer selectAvailableStockBySkuId(Long productSkuId) {
        return stockMapper.selectAvailableStockBySkuId(productSkuId);
    }
    
    @Override
    public PageBean<Stock> selectPage(Integer page, Integer size, Long warehouseId, Long productSkuId, Integer status) {
        Map<String, Object> params = new HashMap<>();
        if (warehouseId != null) {
            params.put("warehouseId", warehouseId);
        }
        if (productSkuId != null) {
            params.put("productSkuId", productSkuId);
        }
        if (status != null) {
            params.put("status", status);
        }
        
        Long total = stockMapper.countByParam(params);
        if (total == 0) {
            return new PageBean<>(0L, null);
        }
        
        params.put("offset", (page - 1) * size);
        params.put("limit", size);
        List<Stock> list = stockMapper.selectPageByParam(params);
        
        return new PageBean<>(total, list);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStock(Long id, Integer stock) {
        return stockMapper.updateStock(id, stock) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean incrementStock(Long id, Integer quantity) {
        return stockMapper.incrementStock(id, quantity) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean decrementStock(Long id, Integer quantity) {
        return stockMapper.decrementStock(id, quantity) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpdateStock(List<Stock> stockList) {
        return stockMapper.batchUpdateStock(stockList) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean inStock(Long warehouseId, Long productSkuId, Integer quantity, java.math.BigDecimal unitPrice, String batchNo, String operator, String remarks) {
        // 1. 检查库存是否存在
        Stock stock = stockMapper.selectByWarehouseAndSku(warehouseId, productSkuId);
        Integer beforeStock = 0;
        
        if (stock == null) {
            // 创建新库存
            stock = new Stock();
            stock.setWarehouseId(warehouseId);
            stock.setProductSkuId(productSkuId);
            stock.setCurrentStock(quantity);
            stock.setWarningStock(10); // 默认预警库存
            stock.setAllocatedStock(0);
            stock.setBatchNo(batchNo);
            stock.setStatus(1);
            stock.setCostPrice(unitPrice);
            stock.setCreateTime(new Date());
            stock.setUpdateTime(new Date());
            stockMapper.insertSelective(stock);
        } else {
            // 更新现有库存
            beforeStock = stock.getCurrentStock();
            stock.setCurrentStock(stock.getCurrentStock() + quantity);
            stock.setLastPurchasePrice(unitPrice);
            stock.setLastInStockTime(System.currentTimeMillis());
            stock.setUpdateTime(new Date());
            stockMapper.updateByPrimaryKeySelective(stock);
        }
        
        // 2. 记录库存变更日志
        StockChangeLog log = new StockChangeLog();
        log.setWarehouseId(warehouseId);
        log.setProductSkuId(productSkuId);
        log.setChangeType(StockChangeLog.TYPE_IN);
        log.setChangeQuantity(quantity);
        log.setBeforeStock(beforeStock);
        log.setAfterStock(beforeStock + quantity);
        log.setUnitPrice(unitPrice);
        log.setTotalAmount(unitPrice.multiply(new java.math.BigDecimal(quantity)));
        log.setSourceType(StockChangeLog.SOURCE_PURCHASE);
        log.setBatchNo(batchNo);
        log.setOperator(operator);
        log.setRemarks(remarks);
        log.setCreateTime(new Date());
        stockChangeLogMapper.insertSelective(log);
        
        return true;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean outStock(Long warehouseId, Long productSkuId, Integer quantity, java.math.BigDecimal unitPrice, String orderNo, String operator, String remarks) {
        // 1. 检查库存是否足够
        Stock stock = stockMapper.selectByWarehouseAndSku(warehouseId, productSkuId);
        if (stock == null || stock.getCurrentStock() < quantity) {
            return false; // 库存不足
        }
        
        // 2. 更新库存
        Integer beforeStock = stock.getCurrentStock();
        stock.setCurrentStock(stock.getCurrentStock() - quantity);
        stock.setLastOutStockTime(System.currentTimeMillis());
        stock.setUpdateTime(new Date());
        stockMapper.updateByPrimaryKeySelective(stock);
        
        // 3. 记录库存变更日志
        StockChangeLog log = new StockChangeLog();
        log.setWarehouseId(warehouseId);
        log.setProductSkuId(productSkuId);
        log.setChangeType(StockChangeLog.TYPE_OUT);
        log.setChangeQuantity(quantity);
        log.setBeforeStock(beforeStock);
        log.setAfterStock(beforeStock - quantity);
        log.setUnitPrice(unitPrice);
        log.setTotalAmount(unitPrice.multiply(new java.math.BigDecimal(quantity)));
        log.setSourceType(StockChangeLog.SOURCE_SALE);
        log.setOrderNo(orderNo);
        log.setOperator(operator);
        log.setRemarks(remarks);
        log.setCreateTime(new Date());
        stockChangeLogMapper.insertSelective(log);
        
        return true;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean adjustStock(Long warehouseId, Long productSkuId, Integer quantity, String reason, String operator, String remarks) {
        // 1. 检查库存是否存在
        Stock stock = stockMapper.selectByWarehouseAndSku(warehouseId, productSkuId);
        if (stock == null) {
            return false; // 库存不存在
        }
        
        // 2. 检查调整后库存是否为负
        if (stock.getCurrentStock() + quantity < 0) {
            return false; // 调整后库存为负
        }
        
        // 3. 更新库存
        Integer beforeStock = stock.getCurrentStock();
        stock.setCurrentStock(stock.getCurrentStock() + quantity);
        stock.setUpdateTime(new Date());
        stockMapper.updateByPrimaryKeySelective(stock);
        
        // 4. 记录库存变更日志
        StockChangeLog log = new StockChangeLog();
        log.setWarehouseId(warehouseId);
        log.setProductSkuId(productSkuId);
        log.setChangeType(StockChangeLog.TYPE_ADJUST);
        log.setChangeQuantity(quantity);
        log.setBeforeStock(beforeStock);
        log.setAfterStock(beforeStock + quantity);
        log.setSourceType(StockChangeLog.SOURCE_MANUAL);
        log.setOperator(operator);
        log.setRemarks(remarks + " - " + reason);
        log.setCreateTime(new Date());
        stockChangeLogMapper.insertSelective(log);
        
        return true;
    }
    
    @Override
    public boolean checkStock(Long warehouseId, Long productSkuId, Integer quantity) {
        Stock stock = stockMapper.selectByWarehouseAndSku(warehouseId, productSkuId);
        return stock != null && stock.getCurrentStock() >= quantity;
    }
}