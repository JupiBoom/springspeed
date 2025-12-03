package com.yasinyuan.testspring.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 商品属性值
 * 存储具体商品的属性值
 */
@Table(name = "product_attribute_value")
public class ProductAttributeValue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** SPU ID */
    @Column(name = "spu_id")
    private Long spuId;
    
    /** 属性ID */
    @Column(name = "attribute_id")
    private Long attributeId;
    
    /** 属性值 */
    private String value;
    
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
    
    public Long getSpuId() {
        return spuId;
    }
    
    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }
    
    public Long getAttributeId() {
        return attributeId;
    }
    
    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
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