package com.yasinyuan.testspring.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 商品分类
 */
@Table(name = "category")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** 分类名称 */
    private String name;
    
    /** 上级分类ID，0表示顶级分类 */
    @Column(name = "parent_id")
    private Long parentId;
    
    /** 分类层级 */
    private Integer level;
    
    /** 分类图标 */
    private String icon;
    
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
    
    public Long getParentId() {
        return parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    public Integer getLevel() {
        return level;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
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