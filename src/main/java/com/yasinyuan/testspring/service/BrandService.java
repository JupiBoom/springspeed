package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.core.Service;
import com.yasinyuan.testspring.model.Brand;

import java.util.List;

/**
 * 品牌 Service接口
 */
public interface BrandService extends Service<Brand> {
    
    /**
     * 查询启用的品牌列表
     * @return 品牌列表
     */
    List<Brand> selectEnableBrands();
    
    /**
     * 根据品牌名称模糊查询
     * @param brandName 品牌名称
     * @return 品牌列表
     */
    List<Brand> selectByBrandNameLike(String brandName);
}