package com.yasinyuan.testspring.service.product;

import com.yasinyuan.testspring.model.product.Category;

import java.util.List;

public interface CategoryService {
    
    /**
     * 根据父分类ID查询子分类列表
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<Category> findByParentId(Long parentId);
    
    /**
     * 根据分类级别查询分类列表
     * @param level 分类级别：1-一级分类，2-二级分类，3-三级分类
     * @return 分类列表
     */
    List<Category> findByLevel(Integer level);
    
    /**
     * 根据ID查询分类详情
     * @param id 分类ID
     * @return 分类详情
     */
    Category findById(Long id);
    
    /**
     * 保存分类
     * @param category 分类信息
     * @return 保存结果
     */
    boolean save(Category category);
    
    /**
     * 更新分类
     * @param category 分类信息
     * @return 更新结果
     */
    boolean update(Category category);
    
    /**
     * 根据ID删除分类
     * @param id 分类ID
     * @return 删除结果
     */
    boolean deleteById(Long id);
}
