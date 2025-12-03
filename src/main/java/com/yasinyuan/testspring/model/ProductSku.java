package com.yasinyuan.testspring.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 商品SKU实体类
 */
@Table(name = "pms_product_sku")
public class ProductSku {
    @Id
    private Long id;
    
    // 商品ID
    private Long productId;
    
    // SKU编码
    private String skuCode;
    
    // 价格
    private Double price;
    
    // 库存
    private Integer stock;
    
    // 库存预警值
    private Integer lowStock;
    
    // 图片
    private String pic;
    
    // 商品规格属性JSON
    private String spData;
    
    // 重量
    private Double weight;
    
    // 销量
    private Integer sale;
    
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getLowStock() {
        return lowStock;
    }

    public void setLowStock(Integer lowStock) {
        this.lowStock = lowStock;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSpData() {
        return spData;
    }

    public void setSpData(String spData) {
        this.spData = spData;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
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