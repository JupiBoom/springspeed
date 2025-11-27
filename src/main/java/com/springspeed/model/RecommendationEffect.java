package com.springspeed.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Recommendation Effect Entity
 * 推荐效果分析
 */
@Entity
@Table(name = "ss_recommendation_effect")
public class RecommendationEffect implements Serializable {

    private static final long serialVersionUID = 1L;

    // 推荐类型常量
    public static final int RECOMMEND_TYPE_HOME = 1; // 首页推荐
    public static final int RECOMMEND_TYPE_CART = 2; // 购物车推荐
    public static final int RECOMMEND_TYPE_PRODUCT = 3; // 商品详情页推荐
    public static final int RECOMMEND_TYPE_CONTENT_BASED = 4; // 基于内容的推荐
    public static final int RECOMMEND_TYPE_POPULAR = 5; // 热门推荐

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "recommend_type", nullable = false)
    private Integer recommendType;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "exposure_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exposureTime; // 曝光时间

    @Column(name = "click_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date clickTime; // 点击时间

    @Column(name = "purchase_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseTime; // 购买时间

    @Column(name = "click_to_purchase_time")
    private Long clickToPurchaseTime; // 点击到购买的时间间隔（秒）

    @Column(name = "is_clicked")
    private Boolean isClicked = false; // 是否点击

    @Column(name = "is_purchased")
    private Boolean isPurchased = false; // 是否购买

    @Column(name = "revenue")
    private Double revenue; // 产生的 revenue

    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "updated_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    // 关联关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Integer getRecommendType() { return recommendType; }
    public void setRecommendType(Integer recommendType) { this.recommendType = recommendType; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Date getExposureTime() { return exposureTime; }
    public void setExposureTime(Date exposureTime) { this.exposureTime = exposureTime; }
    public Date getClickTime() { return clickTime; }
    public void setClickTime(Date clickTime) { this.clickTime = clickTime; }
    public Date getPurchaseTime() { return purchaseTime; }
    public void setPurchaseTime(Date purchaseTime) { this.purchaseTime = purchaseTime; }
    public Long getClickToPurchaseTime() { return clickToPurchaseTime; }
    public void setClickToPurchaseTime(Long clickToPurchaseTime) { this.clickToPurchaseTime = clickToPurchaseTime; }
    public Boolean getIsClicked() { return isClicked; }
    public void setIsClicked(Boolean isClicked) { this.isClicked = isClicked; }
    public Boolean getIsPurchased() { return isPurchased; }
    public void setIsPurchased(Boolean isPurchased) { this.isPurchased = isPurchased; }
    public Double getRevenue() { return revenue; }
    public void setRevenue(Double revenue) { this.revenue = revenue; }
    public Date getCreatedTime() { return createdTime; }
    public void setCreatedTime(Date createdTime) { this.createdTime = createdTime; }
    public Date getUpdatedTime() { return updatedTime; }
    public void setUpdatedTime(Date updatedTime) { this.updatedTime = updatedTime; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    @PrePersist
    protected void onCreate() {
        createdTime = new Date();
        updatedTime = new Date();
        exposureTime = new Date();
        if (isClicked == null) isClicked = false;
        if (isPurchased == null) isPurchased = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = new Date();
        // 计算点击到购买的时间间隔
        if (clickTime != null && purchaseTime != null) {
            clickToPurchaseTime = (purchaseTime.getTime() - clickTime.getTime()) / 1000;
        }
    }

    // 计算点击率
    public double getClickThroughRate() {
        return isClicked ? 1.0 : 0.0;
    }

    // 计算转化率
    public double getConversionRate() {
        return isPurchased ? 1.0 : 0.0;
    }

    // 计算点击转化率（从点击到购买）
    public double getClickConversionRate() {
        return (isClicked && isPurchased) ? 1.0 : 0.0;
    }
}