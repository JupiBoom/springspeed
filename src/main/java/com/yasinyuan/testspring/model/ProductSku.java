package com.yasinyuan.testspring.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品SKU（Stock Keeping Unit）
 * 库存管理单元，是商品销售的最小单位
 */
@Table(name = "product_sku")
public class ProductSku {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** SPU ID */
    @Column(name = "spu_id")
    private Long spuId;
    
    /** SKU名称 */
    private String name;
    
    /** SKU编码 */
    @Column(unique = true)
    private String code;
    
    /** 销售价格 */
    private BigDecimal price;
    
    /** 成本价格 */
    @Column(name = "cost_price")
    private BigDecimal costPrice;
    
    /** 库存数量 */
    private Integer stock;
    
    /** 预警库存数量 */
    @Column(name = "warning_stock")
    private Integer warningStock;
    
    /** 商品规格，用JSON格式存储 */
    @Column(name = "specification", columnDefinition = "TEXT")
    private String specification;
    
    /** 商品属性，用JSON格式存储 */
    @Column(name = "attributes", columnDefinition = "TEXT")
    private String attributes;
    
    /** 商品状态：0-下架，1-上架 */
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
    
    public Long getSpuId() {
        return spuId;
    }
    
    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public BigDecimal getCostPrice() {
        return costPrice;
    }
    
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    public Integer getWarningStock() {
        return warningStock;
    }
    
    public void setWarningStock(Integer warningStock) {
        this.warningStock = warningStock;
    }
    
    public String getSpecification() {
        return specification;
    }
    
    public void setSpecification(String specification) {
        this.specification = specification;
    }
    
    public String getAttributes() {
        return attributes;
    }
    
    public void setAttributes(String attributes) {
        this.attributes = attributes;
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