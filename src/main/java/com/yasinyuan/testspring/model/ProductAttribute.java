package com.yasinyuan.testspring.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 商品属性实体类
 */
@Table(name = "pms_product_attribute")
public class ProductAttribute {
    @Id
    private Long id;
    
    // 属性分类ID
    private Long productAttributeCategoryId;
    
    // 属性名称
    private String name;
    
    // 属性选择类型：0-单选，1-多选
    private Integer selectType;
    
    // 属性录入方式：0-手工录入，1-从列表中选取
    private Integer inputType;
    
    // 可选值列表，以逗号分隔
    private String inputList;
    
    // 排序字段
    private Integer sort;
    
    // 分类筛选样式：0-普通，1-颜色
    private Integer filterType;
    
    // 检索类型；0-不需要进行检索，1-关键字检索，2-范围检索
    private Integer searchType;
    
    // 相同属性商品是否关联；0-不关联，1-关联
    private Integer relatedStatus;
    
    // 是否支持手动新增；0-不支持，1-支持
    private Integer handAddStatus;
    
    // 属性类型；0-规格，1-参数
    private Integer type;
    
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

    public Long getProductAttributeCategoryId() {
        return productAttributeCategoryId;
    }

    public void setProductAttributeCategoryId(Long productAttributeCategoryId) {
        this.productAttributeCategoryId = productAttributeCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSelectType() {
        return selectType;
    }

    public void setSelectType(Integer selectType) {
        this.selectType = selectType;
    }

    public Integer getInputType() {
        return inputType;
    }

    public void setInputType(Integer inputType) {
        this.inputType = inputType;
    }

    public String getInputList() {
        return inputList;
    }

    public void setInputList(String inputList) {
        this.inputList = inputList;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getFilterType() {
        return filterType;
    }

    public void setFilterType(Integer filterType) {
        this.filterType = filterType;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public Integer getRelatedStatus() {
        return relatedStatus;
    }

    public void setRelatedStatus(Integer relatedStatus) {
        this.relatedStatus = relatedStatus;
    }

    public Integer getHandAddStatus() {
        return handAddStatus;
    }

    public void setHandAddStatus(Integer handAddStatus) {
        this.handAddStatus = handAddStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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