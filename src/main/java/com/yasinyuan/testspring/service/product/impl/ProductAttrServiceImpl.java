package com.yasinyuan.testspring.service.product.impl;

import com.yasinyuan.testspring.dao.product.ProductAttrMapper;
import com.yasinyuan.testspring.model.product.ProductAttr;
import com.yasinyuan.testspring.service.product.ProductAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductAttrServiceImpl implements ProductAttrService {
    
    @Autowired
    private ProductAttrMapper productAttrMapper;
    
    @Override
    public List<ProductAttr> findByCategoryId(Long categoryId) {
        return productAttrMapper.selectByCategoryId(categoryId);
    }
    
    @Override
    public List<ProductAttr> findByCategoryIdAndType(Long categoryId, Integer type) {
        return productAttrMapper.selectByCategoryIdAndType(categoryId, type);
    }
    
    @Override
    public ProductAttr findById(Long id) {
        return productAttrMapper.selectById(id);
    }
    
    @Override
    public boolean save(ProductAttr productAttr) {
        productAttr.setCreateTime(new Date());
        productAttr.setUpdateTime(new Date());
        return productAttrMapper.insertSelective(productAttr) > 0;
    }
    
    @Override
    public boolean update(ProductAttr productAttr) {
        productAttr.setUpdateTime(new Date());
        return productAttrMapper.updateByPrimaryKeySelective(productAttr) > 0;
    }
    
    @Override
    public boolean deleteById(Long id) {
        return productAttrMapper.deleteByPrimaryKey(id) > 0;
    }
    
    @Override
    public boolean deleteByCategoryId(Long categoryId) {
        List<ProductAttr> productAttrs = productAttrMapper.selectByCategoryId(categoryId);
        for (ProductAttr productAttr : productAttrs) {
            productAttrMapper.deleteByPrimaryKey(productAttr.getId());
        }
        return true;
    }
}
