package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.dao.CategoryMapper;
import com.yasinyuan.testspring.model.Category;
import com.yasinyuan.testspring.service.CategoryService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类 Service实现类
 */
@Service("categoryService")
public class CategoryServiceImpl extends AbstractService<Category> implements CategoryService {
    
    @Resource
    private CategoryMapper categoryMapper;
    
    @Override
    public List<Category> selectTopCategories() {
        return categoryMapper.selectTopCategories();
    }
    
    @Override
    public List<Category> selectByParentId(Long parentId) {
        return categoryMapper.selectByParentId(parentId);
    }
    
    @Override
    public List<Category> selectAllCategoriesWithTree() {
        List<Category> allCategories = categoryMapper.selectAllCategoriesWithTree();
        return buildCategoryTree(allCategories, 0L);
    }
    
    @Override
    public List<Category> selectByCategoryNameLike(String categoryName) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("name", "%" + categoryName + "%");
        criteria.andEqualTo("status", 1);
        return categoryMapper.selectByExample(example);
    }
    
    /**
     * 构建分类树形结构
     * @param allCategories 所有分类
     * @param parentId 父分类ID
     * @return 树形结构的分类列表
     */
    private List<Category> buildCategoryTree(List<Category> allCategories, Long parentId) {
        List<Category> treeCategories = new ArrayList<>();
        for (Category category : allCategories) {
            if (parentId.equals(category.getParentId())) {
                // 递归查找子分类
                List<Category> children = buildCategoryTree(allCategories, category.getId());
                // 如果当前分类是商品类，添加子分类；否则只保留目录结构
                if (category.getLevel() == 3) { // 假设3级分类是商品类
                    category.setChildren(children);
                }
                treeCategories.add(category);
            }
        }
        return treeCategories;
    }
}