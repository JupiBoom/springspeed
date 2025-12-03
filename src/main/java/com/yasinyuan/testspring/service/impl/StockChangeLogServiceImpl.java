package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.mapper.StockChangeLogMapper;
import com.yasinyuan.testspring.model.StockChangeLog;
import com.yasinyuan.testspring.service.StockChangeLogService;
import com.yasinyuan.testspring.tools.PageBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存变更日志Service实现类
 */
@Service("stockChangeLogService")
public class StockChangeLogServiceImpl extends AbstractService<StockChangeLog> implements StockChangeLogService {
    
    @Resource
    private StockChangeLogMapper stockChangeLogMapper;
    
    @Override
    public List<StockChangeLog> selectByProductSkuId(Long productSkuId, Integer page, Integer size) {
        return stockChangeLogMapper.selectByProductSkuId(productSkuId, (page - 1) * size, size);
    }
    
    @Override
    public List<StockChangeLog> selectByWarehouseId(Long warehouseId, Integer page, Integer size) {
        return stockChangeLogMapper.selectByWarehouseId(warehouseId, (page - 1) * size, size);
    }
    
    @Override
    public List<StockChangeLog> selectByChangeType(Integer changeType, Integer page, Integer size) {
        return stockChangeLogMapper.selectByChangeType(changeType, (page - 1) * size, size);
    }
    
    @Override
    public List<StockChangeLog> selectBySource(String sourceType, Long sourceId) {
        return stockChangeLogMapper.selectBySource(sourceType, sourceId);
    }
    
    @Override
    public List<StockChangeLog> selectByOrderNo(String orderNo) {
        return stockChangeLogMapper.selectByOrderNo(orderNo);
    }
    
    @Override
    public Long countByProductSkuId(Long productSkuId) {
        return stockChangeLogMapper.countByProductSkuId(productSkuId);
    }
    
    @Override
    public Long countByWarehouseId(Long warehouseId) {
        return stockChangeLogMapper.countByWarehouseId(warehouseId);
    }
    
    @Override
    public PageBean<StockChangeLog> selectPage(Integer page, Integer size, Long warehouseId, Long productSkuId, Integer changeType, String sourceType, Date startDate, Date endDate) {
        Map<String, Object> params = new HashMap<>();
        if (warehouseId != null) {
            params.put("warehouseId", warehouseId);
        }
        if (productSkuId != null) {
            params.put("productSkuId", productSkuId);
        }
        if (changeType != null) {
            params.put("changeType", changeType);
        }
        if (sourceType != null && !sourceType.isEmpty()) {
            params.put("sourceType", sourceType);
        }
        if (startDate != null) {
            params.put("startDate", startDate);
        }
        if (endDate != null) {
            params.put("endDate", endDate);
        }
        
        Long total = stockChangeLogMapper.countByParam(params);
        if (total == 0) {
            return new PageBean<>(0L, null);
        }
        
        params.put("offset", (page - 1) * size);
        params.put("limit", size);
        List<StockChangeLog> list = stockChangeLogMapper.selectPageByParam(params);
        
        return new PageBean<>(total, list);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchInsert(List<StockChangeLog> logList) {
        return stockChangeLogMapper.batchInsert(logList) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createLog(StockChangeLog log) {
        log.setCreateTime(new Date());
        return stockChangeLogMapper.insertSelective(log) > 0;
    }
}