package com.yasinyuan.testspring.model.product;

import java.io.Serializable;
import java.util.Date;

public class Spu implements Serializable {
    private static final long serialVersionUID = 1L;

    // SPU ID
    private Long id;

    // 商品名称
    private String name;

    // 商品描述
    private String description;

    // 品牌ID
    private Long brandId;

    // 分类ID
    private Long categoryId;

    // 商品状态：0-下架，1-上架，2-审核中
    private Integer status;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
}
