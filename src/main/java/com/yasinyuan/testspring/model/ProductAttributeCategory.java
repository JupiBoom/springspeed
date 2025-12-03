package com.yasinyuan.testspring.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 商品属性分类实体类
 */
@Table(name = "pms_product_attribute_category")
public class ProductAttributeCategory {
    @Id
    private Long id;
    
    // 分类名称
    private String name;
    
    // 属性数量
    private Integer attributeCount;
    
    // 参数数量
    private Integer paramCount;
    
    // 创建时间
    private Date createTime;
    
    // 更新时间
    private Date updateTime;

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

    public Integer getAttributeCount() {
        return attributeCount;
    }

    public void setAttributeCount(Integer attributeCount) {
        this.attributeCount = attributeCount;
    }

    public Integer getParamCount() {
        return paramCount;
    }

    public void setParamCount(Integer paramCount) {
        this.paramCount = paramCount;
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