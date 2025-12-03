package com.yasinyuan.testspring.service.product.impl;

import com.yasinyuan.testspring.dao.product.BrandMapper;
import com.yasinyuan.testspring.model.product.Brand;
import com.yasinyuan.testspring.service.product.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    
    @Autowired
    private BrandMapper brandMapper;
    
    @Override
    public List<Brand> findByCondition(Brand brand) {
        return brandMapper.selectByCondition(brand);
    }
    
    @Override
    public Brand findById(Long id) {
        return brandMapper.selectById(id);
    }
    
    @Override
    public boolean save(Brand brand) {
        brand.setCreateTime(new Date());
        brand.setUpdateTime(new Date());
        return brandMapper.insertSelective(brand) > 0;
    }
    
    @Override
    public boolean update(Brand brand) {
        brand.setUpdateTime(new Date());
        return brandMapper.updateByPrimaryKeySelective(brand) > 0;
    }
    
    @Override
    public boolean deleteById(Long id) {
        return brandMapper.deleteByPrimaryKey(id) > 0;
    }
}
