package com.yasinyuan.testspring.service.product.impl;

import com.yasinyuan.testspring.dao.product.SkuMapper;
import com.yasinyuan.testspring.model.product.Sku;
import com.yasinyuan.testspring.service.product.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SkuServiceImpl implements SkuService {
    
    @Autowired
    private SkuMapper skuMapper;
    
    @Override
    public List<Sku> findBySpuId(Long spuId) {
        return skuMapper.selectBySpuId(spuId);
    }
    
    @Override
    public Sku findById(Long id) {
        return skuMapper.selectById(id);
    }
    
    @Override
    public boolean save(Sku sku) {
        sku.setCreateTime(new Date());
        sku.setUpdateTime(new Date());
        return skuMapper.insertSelective(sku) > 0;
    }
    
    @Override
    public boolean batchSave(List<Sku> skus) {
        for (Sku sku : skus) {
            sku.setCreateTime(new Date());
            sku.setUpdateTime(new Date());
            skuMapper.insertSelective(sku);
        }
        return true;
    }
    
    @Override
    public boolean update(Sku sku) {
        sku.setUpdateTime(new Date());
        return skuMapper.updateByPrimaryKeySelective(sku) > 0;
    }
    
    @Override
    public boolean batchUpdateStock(List<Sku> skus) {
        return skuMapper.batchUpdateStock(skus) > 0;
    }
    
    @Override
    public boolean batchUpdateStatus(List<Long> ids, Integer status) {
        return skuMapper.batchUpdateStatus(ids, status) > 0;
    }
    
    @Override
    public boolean deleteById(Long id) {
        return skuMapper.deleteByPrimaryKey(id) > 0;
    }
    
    @Override
    public boolean deleteBySpuId(Long spuId) {
        List<Sku> skus = skuMapper.selectBySpuId(spuId);
        for (Sku sku : skus) {
            skuMapper.deleteByPrimaryKey(sku.getId());
        }
        return true;
    }
}
