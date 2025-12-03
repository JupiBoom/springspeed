package com.yasinyuan.testspring.service.product.impl;

import com.yasinyuan.testspring.dao.product.SpuMapper;
import com.yasinyuan.testspring.model.product.Spu;
import com.yasinyuan.testspring.service.product.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpuServiceImpl implements SpuService {
    
    @Autowired
    private SpuMapper spuMapper;
    
    @Override
    public List<Spu> findByCondition(Spu spu) {
        return spuMapper.selectByCondition(spu);
    }
    
    @Override
    public Spu findById(Long id) {
        return spuMapper.selectById(id);
    }
    
    @Override
    public boolean save(Spu spu) {
        spu.setCreateTime(new Date());
        spu.setUpdateTime(new Date());
        return spuMapper.insertSelective(spu) > 0;
    }
    
    @Override
    public boolean update(Spu spu) {
        spu.setUpdateTime(new Date());
        return spuMapper.updateByPrimaryKeySelective(spu) > 0;
    }
    
    @Override
    public boolean batchUpdateStatus(List<Long> ids, Integer status) {
        return spuMapper.batchUpdateStatus(ids, status) > 0;
    }
    
    @Override
    public boolean deleteById(Long id) {
        return spuMapper.deleteByPrimaryKey(id) > 0;
    }
}
