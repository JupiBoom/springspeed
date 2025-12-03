package com.yasinyuan.testspring.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品ES文档实体
 */
@Document(indexName = "product", type = "docs", shards = 1, replicas = 0)
@Setting(settingPath = "es-settings.json")
public class ProductEs implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;
    
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;
    
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String subTitle;
    
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String detailDesc;
    
    @Field(type = FieldType.Long)
    private Long brandId;
    
    @Field(type = FieldType.Keyword)
    private String brandName;
    
    @Field(type = FieldType.Long)
    private Long productCategoryId;
    
    @Field(type = FieldType.Keyword)
    private String productCategoryName;
    
    @Field(type = FieldType.Double)
    private BigDecimal price;
    
    @Field(type = FieldType.Integer)
    private Integer stock;
    
    @Field(type = FieldType.Integer)
    private Integer sale;
    
    @Field(type = FieldType.Integer)
    private Integer sort;
    
    @Field(type = FieldType.Integer)
    private Integer publishStatus;
    
    @Field(type = FieldType.Keyword)
    private String pic;
    
    @Field(type = FieldType.Nested)
    private List<ProductAttributeEs> attrValues;
    
    @Field(type = FieldType.Date)
    private Date createTime;

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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public List<ProductAttributeEs> getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(List<ProductAttributeEs> attrValues) {
        this.attrValues = attrValues;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}