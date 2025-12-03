package com.yasinyuan.testspring.service.product;

import com.yasinyuan.testspring.model.product.Brand;

import java.util.List;

public interface BrandService {
    
    /**
     * 根据条件查询品牌列表
     * @param brand 查询条件
     * @return 品牌列表
     */
    List<Brand> findByCondition(Brand brand);
    
    /**
     * 根据ID查询品牌详情
     * @param id 品牌ID
     * @return 品牌详情
     */
    Brand findById(Long id);
    
    /**
     * 保存品牌
     * @param brand 品牌信息
     * @return 保存结果
     */
    boolean save(Brand brand);
    
    /**
     * 更新品牌
     * @param brand 品牌信息
     * @return 更新结果
     */
    boolean update(Brand brand);
    
    /**
     * 根据ID删除品牌
     * @param id 品牌ID
     * @return 删除结果
     */
    boolean deleteById(Long id);
}
