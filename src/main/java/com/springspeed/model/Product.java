package com.springspeed.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Product Entity
 * 包含商品基本信息和属性标签
 */
@Entity
@Table(name = "ss_product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    // 商品状态常量
    public static final Integer STATUS_INACTIVE = 0;
    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_DELETED = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "sales_count")
    private Integer salesCount = 0;

    @Column(name = "click_count")
    private Integer clickCount = 0;

    @Column(name = "favorite_count")
    private Integer favoriteCount = 0;

    // 商品属性标签
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name", length = 100)
    private String categoryName;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "tags", length = 1000)
    private String tags; // 商品标签，逗号分隔

    @Column(name = "keywords", length = 1000)
    private String keywords; // 搜索关键词

    @Column(name = "product_attributes", length = 2000)
    private String productAttributes; // 商品属性，JSON格式

    // 评分相关
    @Column(name = "average_rating")
    private Double averageRating = 0.0;

    @Column(name = "rating_count")
    private Integer ratingCount = 0;

    // 状态字段
    @Column(name = "status")
    private Integer status; // 0: inactive, 1: active, 2: deleted

    @Column(name = "is_hot")
    private Boolean isHot = false; // 是否热门商品

    @Column(name = "is_new")
    private Boolean isNew = false; // 是否新品

    // 时间字段
    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "updated_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Column(name = "shelf_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shelfTime;

    // 关联关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserBehavior> userBehaviors;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    public Integer getSalesCount() { return salesCount; }
    public void setSalesCount(Integer salesCount) { this.salesCount = salesCount; }
    public Integer getClickCount() { return clickCount; }
    public void setClickCount(Integer clickCount) { this.clickCount = clickCount; }
    public Integer getFavoriteCount() { return favoriteCount; }
    public void setFavoriteCount(Integer favoriteCount) { this.favoriteCount = favoriteCount; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }
    public String getProductAttributes() { return productAttributes; }
    public void setProductAttributes(String productAttributes) { this.productAttributes = productAttributes; }
    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    public Integer getRatingCount() { return ratingCount; }
    public void setRatingCount(Integer ratingCount) { this.ratingCount = ratingCount; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Boolean getIsHot() { return isHot; }
    public void setIsHot(Boolean isHot) { this.isHot = isHot; }
    public Boolean getIsNew() { return isNew; }
    public void setIsNew(Boolean isNew) { this.isNew = isNew; }
    public Date getCreatedTime() { return createdTime; }
    public void setCreatedTime(Date createdTime) { this.createdTime = createdTime; }
    public Date getUpdatedTime() { return updatedTime; }
    public void setUpdatedTime(Date updatedTime) { this.updatedTime = updatedTime; }
    public Date getShelfTime() { return shelfTime; }
    public void setShelfTime(Date shelfTime) { this.shelfTime = shelfTime; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public List<UserBehavior> getUserBehaviors() { return userBehaviors; }
    public void setUserBehaviors(List<UserBehavior> userBehaviors) { this.userBehaviors = userBehaviors; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
    public List<CartItem> getCartItems() { return cartItems; }
    public void setCartItems(List<CartItem> cartItems) { this.cartItems = cartItems; }

    @PrePersist
    protected void onCreate() {
        createdTime = new Date();
        updatedTime = new Date();
        if (status == null) status = 1;
        if (salesCount == null) salesCount = 0;
        if (clickCount == null) clickCount = 0;
        if (favoriteCount == null) favoriteCount = 0;
        if (averageRating == null) averageRating = 0.0;
        if (ratingCount == null) ratingCount = 0;
        if (isHot == null) isHot = false;
        if (isNew == null) isNew = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = new Date();
    }
}