package com.yasinyuan.testspring.service.impl;

import com.github.pagehelper.PageHelper;
import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.mapper.WarehouseMapper;
import com.yasinyuan.testspring.model.Warehouse;
import com.yasinyuan.testspring.service.WarehouseService;
import com.yasinyuan.testspring.tools.PageBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 仓库Service实现类
 */
@Service("warehouseService")
public class WarehouseServiceImpl extends AbstractService<Warehouse> implements WarehouseService {
    
    @Resource
    private WarehouseMapper warehouseMapper;
    
    @Override
    public Warehouse selectByCode(String code) {
        return warehouseMapper.selectByCode(code);
    }
    
    @Override
    public List<Warehouse> selectByNameLike(String name) {
        return warehouseMapper.selectByNameLike("%" + name + "%");
    }
    
    @Override
    public List<Warehouse> selectEnabled() {
        return warehouseMapper.selectEnabled();
    }
    
    @Override
    public PageBean<Warehouse> selectPage(Integer page, Integer size, String name, Integer status) {
        Map<String, Object> params = new HashMap<>();
        if (name != null && !name.isEmpty()) {
            params.put("name", "%" + name + "%");
        }
        if (status != null) {
            params.put("status", status);
        }
        
        Long total = warehouseMapper.countByParam(params);
        if (total == 0) {
            return new PageBean<>(0L, null);
        }
        
        params.put("offset", (page - 1) * size);
        params.put("limit", size);
        List<Warehouse> list = warehouseMapper.selectPageByParam(params);
        
        return new PageBean<>(total, list);
    }
    
    @Override
    public boolean existsByCode(String code, Long excludeId) {
        Warehouse warehouse = warehouseMapper.selectByCode(code);
        return warehouse != null && !warehouse.getId().equals(excludeId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean enable(Long id) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(id);
        warehouse.setStatus(1);
        warehouse.setUpdateTime(new Date());
        return warehouseMapper.updateByPrimaryKeySelective(warehouse) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disable(Long id) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(id);
        warehouse.setStatus(0);
        warehouse.setUpdateTime(new Date());
        return warehouseMapper.updateByPrimaryKeySelective(warehouse) > 0;
    }
}