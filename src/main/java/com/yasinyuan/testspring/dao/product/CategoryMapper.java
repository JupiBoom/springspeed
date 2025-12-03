package com.yasinyuan.testspring.dao.product;

import com.yasinyuan.testspring.model.product.Category;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category> {
    
    /**
     * 根据父分类ID查询子分类列表
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<Category> selectByParentId(Long parentId);
    
    /**
     * 根据分类级别查询分类列表
     * @param level 分类级别：1-一级分类，2-二级分类，3-三级分类
     * @return 分类列表
     */
    List<Category> selectByLevel(Integer level);
    
    /**
     * 根据ID查询分类详情
     * @param id 分类ID
     * @return 分类详情
     */
    Category selectById(Long id);
}
