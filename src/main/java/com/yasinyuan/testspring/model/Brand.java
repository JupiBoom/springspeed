package com.yasinyuan.testspring.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 品牌实体类
 */
@Table(name = "pms_brand")
public class Brand {
    @Id
    private Long id;
    
    // 品牌名称
    private String name;
    
    // 品牌logo
    private String logo;
    
    // 品牌描述
    private String description;
    
    // 品牌首字母
    private String firstLetter;
    
    // 排序
    private Integer sort;
    
    // 是否显示
    private Integer showStatus;
    
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

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
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