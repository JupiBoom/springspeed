package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.core.Service;
import com.yasinyuan.testspring.model.Category;

import java.util.List;

/**
 * 商品分类 Service接口
 */
public interface CategoryService extends Service<Category> {
    
    /**
     * 查询顶级分类列表
     * @return 顶级分类列表
     */
    List<Category> selectTopCategories();
    
    /**
     * 根据父分类ID查询子分类列表
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<Category> selectByParentId(Long parentId);
    
    /**
     * 查询所有分类列表（树形结构）
     * @return 分类树形结构
     */
    List<Category> selectAllCategoriesWithTree();
    
    /**
     * 根据分类名称模糊查询
     * @param categoryName 分类名称
     * @return 分类列表
     */
    List<Category> selectByCategoryNameLike(String categoryName);
}