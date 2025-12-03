package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.dao.ProductSkuMapper;
import com.yasinyuan.testspring.model.ProductSku;
import com.yasinyuan.testspring.service.ProductSkuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 商品SKU Service实现类
 */
@Service("productSkuService")
public class ProductSkuServiceImpl extends AbstractService<ProductSku> implements ProductSkuService {
    
    @Resource
    private ProductSkuMapper productSkuMapper;
    
    @Override
    public List<ProductSku> selectBySpuId(Long spuId) {
        return productSkuMapper.selectBySpuId(spuId);
    }
    
    @Override
    public ProductSku selectBySkuCode(String skuCode) {
        return productSkuMapper.selectBySkuCode(skuCode);
    }
    
    @Override
    public boolean updateStock(Long id, Integer stock) {
        ProductSku productSku = productSkuMapper.selectByPrimaryKey(id);
        if (productSku == null) {
            return false;
        }
        productSku.setStock(stock);
        productSku.setUpdateTime(new Date());
        return productSkuMapper.updateByPrimaryKeySelective(productSku) > 0;
    }
    
    @Override
    public boolean decreaseStock(Long id, Integer quantity) {
        ProductSku productSku = productSkuMapper.selectByPrimaryKey(id);
        if (productSku == null || productSku.getStock() < quantity) {
            return false;
        }
        productSku.setStock(productSku.getStock() - quantity);
        productSku.setUpdateTime(new Date());
        return productSkuMapper.updateByPrimaryKeySelective(productSku) > 0;
    }
    
    @Override
    public boolean increaseStock(Long id, Integer quantity) {
        ProductSku productSku = productSkuMapper.selectByPrimaryKey(id);
        if (productSku == null) {
            return false;
        }
        productSku.setStock(productSku.getStock() + quantity);
        productSku.setUpdateTime(new Date());
        return productSkuMapper.updateByPrimaryKeySelective(productSku) > 0;
    }
    
    @Override
    public boolean updateStatus(Long id, Integer status) {
        ProductSku productSku = productSkuMapper.selectByPrimaryKey(id);
        if (productSku == null) {
            return false;
        }
        productSku.setStatus(status);
        productSku.setUpdateTime(new Date());
        return productSkuMapper.updateByPrimaryKeySelective(productSku) > 0;
    }
}