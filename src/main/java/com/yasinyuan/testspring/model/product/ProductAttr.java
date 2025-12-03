package com.yasinyuan.testspring.model.product;

import java.io.Serializable;
import java.util.Date;

public class ProductAttr implements Serializable {
    private static final long serialVersionUID = 1L;

    // 属性ID
    private Long id;

    // 属性名称
    private String name;

    // 属性类型：0-规格参数，1-销售属性
    private Integer type;

    // 分类ID（属性所属分类）
    private Long categoryId;

    // 属性值（JSON格式，如["红色","蓝色","绿色"]）
    private String values;

    // 是否必填：0-否，1-是
    private Integer required;

    // 排序字段
    private Integer sort;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;

    //  getter和setter方法
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
}
