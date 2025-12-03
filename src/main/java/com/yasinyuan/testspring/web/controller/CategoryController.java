package com.yasinyuan.testspring.web.controller;

import com.yasinyuan.testspring.model.Category;
import com.yasinyuan.testspring.service.CategoryService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 商品分类 Controller
 */
@RestController
@RequestMapping("/product/category")
public class CategoryController extends BaseController {
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 分页查询分类列表
     */
    @GetMapping("/list")
    public R list(Category category, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        startPage(page, limit);
        List<Category> list = categoryService.select(category);
        return page(list);
    }
    
    /**
     * 根据ID查询分类详情
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable Long id) {
        Category category = categoryService.selectById(id);
        return R.ok().put("category", category);
    }
    
    /**
     * 查询顶级分类列表
     */
    @GetMapping("/listTop")
    public R listTop() {
        List<Category> list = categoryService.selectTopCategories();
        return R.ok().put("list", list);
    }
    
    /**
     * 根据父分类ID查询子分类列表
     */
    @GetMapping("/listByParentId/{parentId}")
    public R listByParentId(@PathVariable Long parentId) {
        List<Category> list = categoryService.selectByParentId(parentId);
        return R.ok().put("list", list);
    }
    
    /**
     * 查询所有分类列表（树形结构）
     */
    @GetMapping("/listWithTree")
    public R listWithTree() {
        List<Category> list = categoryService.selectAllCategoriesWithTree();
        return R.ok().put("list", list);
    }
    
    /**
     * 根据分类名称模糊查询
     */
    @GetMapping("/listByNameLike/{categoryName}")
    public R listByNameLike(@PathVariable String categoryName) {
        List<Category> list = categoryService.selectByCategoryNameLike(categoryName);
        return R.ok().put("list", list);
    }
    
    /**
     * 新增分类
     */
    @PostMapping("/save")
    public R save(@RequestBody Category category) {
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        category.setDeleted(0);
        categoryService.insert(category);
        return R.ok();
    }
    
    /**
     * 修改分类
     */
    @PostMapping("/update")
    public R update(@RequestBody Category category) {
        category.setUpdateTime(new Date());
        categoryService.updateByIdSelective(category);
        return R.ok();
    }
    
    /**
     * 删除分类
     */
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return R.ok();
    }
}