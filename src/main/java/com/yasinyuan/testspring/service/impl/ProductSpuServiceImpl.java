package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.dao.ProductSpuMapper;
import com.yasinyuan.testspring.model.ProductSpu;
import com.yasinyuan.testspring.service.ProductSpuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 商品SPU Service实现类
 */
@Service("productSpuService")
public class ProductSpuServiceImpl extends AbstractService<ProductSpu> implements ProductSpuService {
    
    @Resource
    private ProductSpuMapper productSpuMapper;
    
    @Override
    public List<ProductSpu> selectByBrandId(Long brandId) {
        return productSpuMapper.selectByBrandId(brandId);
    }
    
    @Override
    public List<ProductSpu> selectByCategoryId(Long categoryId) {
        return productSpuMapper.selectByCategoryId(categoryId);
    }
    
    @Override
    public List<ProductSpu> selectByStatus(Integer status) {
        return productSpuMapper.selectByStatus(status);
    }
    
    @Override
    public boolean onSale(Long id) {
        ProductSpu productSpu = productSpuMapper.selectByPrimaryKey(id);
        if (productSpu == null) {
            return false;
        }
        // 只有审核通过的商品才能上架
        if (productSpu.getAuditStatus() != 1) {
            return false;
        }
        productSpu.setStatus(1);
        productSpu.setOnSaleTime(new Date());
        productSpu.setUpdateTime(new Date());
        return productSpuMapper.updateByPrimaryKeySelective(productSpu) > 0;
    }
    
    @Override
    public boolean offSale(Long id) {
        ProductSpu productSpu = productSpuMapper.selectByPrimaryKey(id);
        if (productSpu == null) {
            return false;
        }
        productSpu.setStatus(0);
        productSpu.setOffSaleTime(new Date());
        productSpu.setUpdateTime(new Date());
        return productSpuMapper.updateByPrimaryKeySelective(productSpu) > 0;
    }
    
    @Override
    public boolean audit(Long id, Integer auditStatus, String auditRemark) {
        ProductSpu productSpu = productSpuMapper.selectByPrimaryKey(id);
        if (productSpu == null) {
            return false;
        }
        productSpu.setAuditStatus(auditStatus);
        productSpu.setAuditRemark(auditRemark);
        productSpu.setUpdateTime(new Date());
        return productSpuMapper.updateByPrimaryKeySelective(productSpu) > 0;
    }
}