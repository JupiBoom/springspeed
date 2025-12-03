package com.yasinyuan.testspring.dao.product;

import com.yasinyuan.testspring.model.product.Brand;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    
    /**
     * 根据条件查询品牌列表
     * @param brand 查询条件
     * @return 品牌列表
     */
    List<Brand> selectByCondition(Brand brand);
    
    /**
     * 根据ID查询品牌详情
     * @param id 品牌ID
     * @return 品牌详情
     */
    Brand selectById(Long id);
}
