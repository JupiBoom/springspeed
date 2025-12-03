package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.dao.ProductMapper;
import com.yasinyuan.testspring.model.Product;
import com.yasinyuan.testspring.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl extends AbstractService<Product> implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public List<Product> selectByBrandId(Long brandId) {
        return productMapper.selectByBrandId(brandId);
    }

    @Override
    public List<Product> selectByCategoryId(Long categoryId) {
        return productMapper.selectByCategoryId(categoryId);
    }

    @Override
    public List<Product> selectByPublishStatus(Integer publishStatus) {
        return productMapper.selectByPublishStatus(publishStatus);
    }

    @Override
    public List<Product> selectByVerifyStatus(Integer verifyStatus) {
        return productMapper.selectByVerifyStatus(verifyStatus);
    }

    @Override
    public boolean publishProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setPublishStatus(1);
        product.setUpdateTime(new Date());
        return productMapper.updateByPrimaryKeySelective(product) > 0;
    }

    @Override
    public boolean unpublishProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setPublishStatus(0);
        product.setUpdateTime(new Date());
        return productMapper.updateByPrimaryKeySelective(product) > 0;
    }

    @Override
    public boolean verifyProduct(Long id, Integer verifyStatus) {
        Product product = new Product();
        product.setId(id);
        product.setVerifyStatus(verifyStatus);
        product.setUpdateTime(new Date());
        return productMapper.updateByPrimaryKeySelective(product) > 0;
    }

    @Override
    public List<Product> getLowStockProducts() {
        return productMapper.selectLowStockProducts();
    }

    @Override
    public boolean updateStock(Long id, Integer stock) {
        Product product = new Product();
        product.setId(id);
        product.setStock(stock);
        product.setUpdateTime(new Date());
        return productMapper.updateByPrimaryKeySelective(product) > 0;
    }
}