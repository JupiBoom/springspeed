package com.yasinyuan.testspring.service.product.impl;

import com.yasinyuan.testspring.dao.product.CategoryMapper;
import com.yasinyuan.testspring.model.product.Category;
import com.yasinyuan.testspring.service.product.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryMapper categoryMapper;
    
    @Override
    public List<Category> findByParentId(Long parentId) {
        return categoryMapper.selectByParentId(parentId);
    }
    
    @Override
    public List<Category> findByLevel(Integer level) {
        return categoryMapper.selectByLevel(level);
    }
    
    @Override
    public Category findById(Long id) {
        return categoryMapper.selectById(id);
    }
    
    @Override
    public boolean save(Category category) {
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        return categoryMapper.insertSelective(category) > 0;
    }
    
    @Override
    public boolean update(Category category) {
        category.setUpdateTime(new Date());
        return categoryMapper.updateByPrimaryKeySelective(category) > 0;
    }
    
    @Override
    public boolean deleteById(Long id) {
        // 先删除子分类
        List<Category> children = categoryMapper.selectByParentId(id);
        for (Category child : children) {
            deleteById(child.getId());
        }
        // 再删除当前分类
        return categoryMapper.deleteByPrimaryKey(id) > 0;
    }
}
