package com.yasinyuan.testspring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品搜索索引模型
 * 用于Elasticsearch商品搜索
 */
@Document(indexName = "product", type = "product", shards = 1, replicas = 0)
public class ProductSearch {
    
    @Id
    private Long id;
    
    /** SPU ID */
    @Field(type = FieldType.Long)
    private Long spuId;
    
    /** 商品名称 */
    @Field(type = FieldType.String, index = FieldIndex.analyzed, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String name;
    
    /** 商品描述 */
    @Field(type = FieldType.String, index = FieldIndex.analyzed, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String description;
    
    /** 品牌ID */
    @Field(type = FieldType.Long)
    private Long brandId;
    
    /** 品牌名称 */
    @Field(type = FieldType.String, index = FieldIndex.analyzed, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String brandName;
    
    /** 分类ID */
    @Field(type = FieldType.Long)
    private Long categoryId;
    
    /** 分类名称 */
    @Field(type = FieldType.String, index = FieldIndex.analyzed, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String categoryName;
    
    /** 商品主图 */
    @Field(type = FieldType.String)
    private String mainImage;
    
    /** 价格 */
    @Field(type = FieldType.Double)
    private BigDecimal price;
    
    /** 库存数量 */
    @Field(type = FieldType.Integer)
    private Integer stock;
    
    /** 销量 */
    @Field(type = FieldType.Integer)
    private Integer sales;
    
    /** 状态：0-下架，1-上架 */
    @Field(type = FieldType.Integer)
    private Integer status;
    
    /** 商品属性列表 */
    @Field(type = FieldType.Nested)
    private List<ProductSearchAttribute> attributes;
    
    /** 标签，用逗号分隔 */
    @Field(type = FieldType.String, index = FieldIndex.analyzed, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String tags;
    
    /** 创建时间 */
    @Field(type = FieldType.Date)
    private Date createTime;
    
    /** 更新时间 */
    @Field(type = FieldType.Date)
    private Date updateTime;
    
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
    
    public String getBrandName() {
        return brandName;
    }
    
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getMainImage() {
        return mainImage;
    }
    
    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
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
    
    public Integer getSales() {
        return sales;
    }
    
    public void setSales(Integer sales) {
        this.sales = sales;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public List<ProductSearchAttribute> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(List<ProductSearchAttribute> attributes) {
        this.attributes = attributes;
    }
    
    public String getTags() {
        return tags;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
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
    
    /**
     * 商品搜索属性嵌套类
     */
    public static class ProductSearchAttribute {
        
        /** 属性ID */
        private Long attributeId;
        
        /** 属性名称 */
        private String attributeName;
        
        /** 属性值 */
        private String attributeValue;
        
        /** 属性值 */
        private String value;
        
        // Getters and Setters
        public Long getAttributeId() {
            return attributeId;
        }
        
        public void setAttributeId(Long attributeId) {
            this.attributeId = attributeId;
        }
        
        public String getAttributeName() {
            return attributeName;
        }
        
        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
        }
        
        public String getAttributeValue() {
            return attributeValue;
        }
        
        public void setAttributeValue(String attributeValue) {
            this.attributeValue = attributeValue;
        }
        
        public String getValue() {
            return value;
        }
        
        public void setValue(String value) {
            this.value = value;
        }
    }
}