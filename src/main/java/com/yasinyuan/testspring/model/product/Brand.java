package com.yasinyuan.testspring.model.product;

import java.io.Serializable;
import java.util.Date;

public class Brand implements Serializable {
    private static final long serialVersionUID = 1L;

    // 品牌ID
    private Long id;

    // 品牌名称
    private String name;

    // 品牌Logo
    private String logo;

    // 品牌描述
    private String description;

    // 品牌网址
    private String website;

    // 排序字段
    private Integer sort;

    // 是否启用：0-禁用，1-启用
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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
}
