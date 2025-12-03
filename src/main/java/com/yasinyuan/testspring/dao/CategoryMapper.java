package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.core.Mapper;
import com.yasinyuan.testspring.model.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品分类 Mapper
 */
public interface CategoryMapper extends Mapper<Category> {
    
    /**
     * 查询顶级分类列表
     * @return 顶级分类列表
     */
    @Select("SELECT * FROM category WHERE parent_id = 0 AND status = 1 AND deleted = 0 ORDER BY sort ASC")
    List<Category> selectTopCategories();
    
    /**
     * 根据父分类ID查询子分类列表
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    @Select("SELECT * FROM category WHERE parent_id = #{parentId} AND status = 1 AND deleted = 0 ORDER BY sort ASC")
    List<Category> selectByParentId(Long parentId);
    
    /**
     * 查询所有分类列表（树形结构）
     * @return 分类列表
     */
    @Select("SELECT * FROM category WHERE status = 1 AND deleted = 0 ORDER BY parent_id ASC, sort ASC")
    List<Category> selectAllCategories();
}