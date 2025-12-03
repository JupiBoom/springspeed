package com.yasinyuan.testspring.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 品牌
 */
@Table(name = "brand")
public class Brand {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** 品牌名称 */
    private String name;
    
    /** 品牌描述 */
    private String description;
    
    /** 品牌Logo */
    private String logo;
    
    /** 品牌官网 */
    private String website;
    
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLogo() {
        return logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
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