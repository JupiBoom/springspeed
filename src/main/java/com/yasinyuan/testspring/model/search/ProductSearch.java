package com.yasinyuan.testspring.model.search;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Document(indexName = "product", type = "product")
public class ProductSearch implements Serializable {
    private static final long serialVersionUID = 1L;

    // 商品ID（SPU ID）
    private Long id;

    // 商品名称
    @Field(type = FieldType.String, index = FieldIndex.analyzed, searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
    private String name;

    // 商品描述
    @Field(type = FieldType.String, index = FieldIndex.analyzed, searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
    private String description;

    // 品牌ID
    private Long brandId;

    // 品牌名称
    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    private String brandName;

    // 分类ID
    private Long categoryId;

    // 分类名称
    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    private String categoryName;

    // 商品价格（最低价格）
    private BigDecimal price;

    // 库存数量（总库存）
    private Integer stock;

    // 商品状态：0-下架，1-上架
    private Integer status;

    // 规格参数（Map格式）
    @Field(type = FieldType.Object)
    private Map<String, Object> specs;

    // 销售属性（Map格式）
    @Field(type = FieldType.Object)
    private Map<String, Object> saleAttrs;

    // 商品图片
    private String image;

    // 销量
    private Integer sales;

    // 好评率
    private Double praiseRate;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<String, Object> getSpecs() {
        return specs;
    }

    public void setSpecs(Map<String, Object> specs) {
        this.specs = specs;
    }

    public Map<String, Object> getSaleAttrs() {
        return saleAttrs;
    }

    public void setSaleAttrs(Map<String, Object> saleAttrs) {
        this.saleAttrs = saleAttrs;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Double getPraiseRate() {
        return praiseRate;
    }

    public void setPraiseRate(Double praiseRate) {
        this.praiseRate = praiseRate;
    }
}
