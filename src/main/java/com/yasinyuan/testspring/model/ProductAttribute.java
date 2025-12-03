package com.yasinyuan.testspring.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 商品属性
 * 用于管理商品的规格参数和销售属性
 */
@Table(name = "product_attribute")
public class ProductAttribute {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** 属性名称 */
    private String name;
    
    /** 属性类型：0-规格参数，1-销售属性 */
    private Integer type;
    
    /** 属性值，用逗号分隔 */
    private String values;
    
    /** 分类ID，关联商品分类 */
    @Column(name = "category_id")
    private Long categoryId;
    
    /** 是否必填：0-非必填，1-必填 */
    @Column(name = "is_required")
    private Integer isRequired;
    
    /** 排序字段 */
    private Integer sort;
    
    /** 状态：0-禁用，1-启用 */
    private Integer status;
    
    /** 创建时间 */
    @Column(name = "create_time")
    private Date createTime;
    
    /** 更新时间 */
    @Column(name = "update_time")
    private Date updateTime;
    
    /** 创建人 */
    private String createBy;
    
    /** 更新人 */
    private String updateBy;
    
    /** 删除标记：0-未删除，1-已删除 */
    private Integer deleted;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public String getValues() {
        return values;
    }
    
    public void setValues(String values) {
        this.values = values;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public Integer getIsRequired() {
        return isRequired;
    }
    
    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }
    
    public Integer getSort() {
        return sort;
    }
    
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getCreateBy() {
        return createBy;
    }
    
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    
    public String getUpdateBy() {
        return updateBy;
    }
    
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    
    public Integer getDeleted() {
        return deleted;
    }
    
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}