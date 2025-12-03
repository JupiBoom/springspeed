package com.yasinyuan.testspring.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品SPU（Standard Product Unit）
 * 标准产品单元，是商品信息聚合的最小单位
 */
@Table(name = "product_spu")
public class ProductSpu {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** 商品名称 */
    private String name;
    
    /** 商品描述 */
    @Column(columnDefinition = "TEXT")
    private String description;
    
    /** 品牌ID */
    private Long brandId;
    
    /** 分类ID */
    private Long categoryId;
    
    /** 商品主图 */
    private String mainImage;
    
    /** 商品图片列表，用逗号分隔 */
    private String images;
    
    /** 商品状态：0-下架，1-上架，2-审核中 */
    private Integer status;
    
    /** 审核状态：0-待审核，1-审核通过，2-审核失败 */
    private Integer auditStatus;
    
    /** 审核备注 */
    private String auditRemark;
    
    /** 上架时间 */
    private Date onSaleTime;
    
    /** 下架时间 */
    private Date offSaleTime;
    
    /** 商品价格（参考价） */
    private BigDecimal price;
    
    /** 商品重量 */
    private BigDecimal weight;
    
    /** 商品体积 */
    private BigDecimal volume;
    
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
    
    public String getMainImage() {
        return mainImage;
    }
    
    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }
    
    public String getImages() {
        return images;
    }
    
    public void setImages(String images) {
        this.images = images;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getAuditStatus() {
        return auditStatus;
    }
    
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }
    
    public String getAuditRemark() {
        return auditRemark;
    }
    
    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }
    
    public Date getOnSaleTime() {
        return onSaleTime;
    }
    
    public void setOnSaleTime(Date onSaleTime) {
        this.onSaleTime = onSaleTime;
    }
    
    public Date getOffSaleTime() {
        return offSaleTime;
    }
    
    public void setOffSaleTime(Date offSaleTime) {
        this.offSaleTime = offSaleTime;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public BigDecimal getWeight() {
        return weight;
    }
    
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    
    public BigDecimal getVolume() {
        return volume;
    }
    
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
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