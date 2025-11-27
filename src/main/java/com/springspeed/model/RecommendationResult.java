package com.springspeed.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Recommendation Result Entity
 * 推荐结果记录
 */
@Entity
@Table(name = "ss_recommendation_result")
public class RecommendationResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId; // 用户ID，0表示热门推荐

    /**
     * 推荐类型
     * 1: 首页个性化推荐
     * 2: 购物车关联推荐
     * 3: 商品详情页猜你喜欢
     * 4: 热门商品推荐
     * 5: 基于内容的推荐
     */
    @Column(name = "recommend_type", nullable = false)
    private Integer recommendType;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "recommend_score")
    private Double recommendScore; // 推荐分数

    @Column(name = "rank_order")
    private Integer rankOrder; // 推荐排名

    @Column(name = "expire_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime; // 推荐结果过期时间

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
    public Double getRecommendScore() { return recommendScore; }
    public void setRecommendScore(Double recommendScore) { this.recommendScore = recommendScore; }
    public Integer getRankOrder() { return rankOrder; }
    public void setRankOrder(Integer rankOrder) { this.rankOrder = rankOrder; }
    public Date getExpireTime() { return expireTime; }
    public void setExpireTime(Date expireTime) { this.expireTime = expireTime; }
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
        // 默认24小时后过期
        if (expireTime == null) {
            expireTime = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = new Date();
    }

    // 推荐类型常量
    public static final int RECOMMEND_TYPE_HOME = 1;
    public static final int RECOMMEND_TYPE_CART = 2;
    public static final int RECOMMEND_TYPE_PRODUCT = 3;
    public static final int RECOMMEND_TYPE_POPULAR = 4;
    public static final int RECOMMEND_TYPE_CONTENT_BASED = 5;

    // 推荐类型描述
    public String getRecommendTypeDesc() {
        switch (recommendType) {
            case RECOMMEND_TYPE_HOME:
                return "首页个性化推荐";
            case RECOMMEND_TYPE_CART:
                return "购物车关联推荐";
            case RECOMMEND_TYPE_PRODUCT:
                return "商品详情页猜你喜欢";
            case RECOMMEND_TYPE_POPULAR:
                return "热门商品推荐";
            case RECOMMEND_TYPE_CONTENT_BASED:
                return "基于内容的推荐";
            default:
                return "未知推荐类型";
        }
    }
}