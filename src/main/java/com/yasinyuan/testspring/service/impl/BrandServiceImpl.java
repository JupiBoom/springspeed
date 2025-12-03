package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.dao.BrandMapper;
import com.yasinyuan.testspring.model.Brand;
import com.yasinyuan.testspring.service.BrandService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌 Service实现类
 */
@Service("brandService")
public class BrandServiceImpl extends AbstractService<Brand> implements BrandService {
    
    @Resource
    private BrandMapper brandMapper;
    
    @Override
    public List<Brand> selectEnableBrands() {
        return brandMapper.selectEnableBrands();
    }
    
    @Override
    public List<Brand> selectByBrandNameLike(String brandName) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("name", "%" + brandName + "%");
        criteria.andEqualTo("status", 1);
        return brandMapper.selectByExample(example);
    }
}