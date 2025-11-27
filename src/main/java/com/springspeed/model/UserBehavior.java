package com.springspeed.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User Behavior Entity
 * 记录用户的各种行为（浏览、购买、收藏等）
 */
@Entity
@Table(name = "ss_user_behavior")
public class UserBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    /**
     * 行为类型
     * 1: 浏览(view)
     * 2: 收藏(favorite)
     * 3: 加入购物车(add_to_cart)
     * 4: 购买(purchase)
     * 5: 评分(rating)
     * 6: 评论(comment)
     */
    @Column(name = "behavior_type", nullable = false)
    private Integer behaviorType;

    /**
     * 行为权重
     * 根据行为类型设置不同的权重，用于用户兴趣计算
     * 浏览: 1
     * 收藏: 3
     * 加入购物车: 5
     * 购买: 10
     * 评分: 2
     * 评论: 2
     */
    @Column(name = "behavior_weight")
    private Integer behaviorWeight;

    /**
     * 评分值（仅当behavior_type=5时有效）
     * 1-5星评分
     */
    @Column(name = "rating_score")
    private Integer ratingScore;

    /**
     * 行为发生的页面
     * 用于分析用户行为场景
     */
    @Column(name = "page_source", length = 100)
    private String pageSource;

    /**
     * 用户停留时间（秒）
     * 仅当浏览行为时有效
     */
    @Column(name = "stay_duration")
    private Integer stayDuration;

    /**
     * IP地址
     */
    @Column(name = "ip_address", length = 50)
    private String ipAddress;

    /**
     * 用户代理信息
     */
    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

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
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getBehaviorType() { return behaviorType; }
    public void setBehaviorType(Integer behaviorType) { this.behaviorType = behaviorType; }
    public Integer getBehaviorWeight() { return behaviorWeight; }
    public void setBehaviorWeight(Integer behaviorWeight) { this.behaviorWeight = behaviorWeight; }
    public Integer getRatingScore() { return ratingScore; }
    public void setRatingScore(Integer ratingScore) { this.ratingScore = ratingScore; }
    public String getPageSource() { return pageSource; }
    public void setPageSource(String pageSource) { this.pageSource = pageSource; }
    public Integer getStayDuration() { return stayDuration; }
    public void setStayDuration(Integer stayDuration) { this.stayDuration = stayDuration; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public Date getCreatedTime() { return createdTime; }
    public void setCreatedTime(Date createdTime) { this.createdTime = createdTime; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    @PrePersist
    protected void onCreate() {
        createdTime = new Date();
        // 根据行为类型设置默认权重
        if (behaviorWeight == null && behaviorType != null) {
            switch (behaviorType) {
                case 1: // 浏览
                    behaviorWeight = 1;
                    break;
                case 2: // 收藏
                    behaviorWeight = 3;
                    break;
                case 3: // 加入购物车
                    behaviorWeight = 5;
                    break;
                case 4: // 购买
                    behaviorWeight = 10;
                    break;
                case 5: // 评分
                case 6: // 评论
                    behaviorWeight = 2;
                    break;
                default:
                    behaviorWeight = 1;
            }
        }
    }

    // 行为类型常量
    public static final int BEHAVIOR_VIEW = 1;
    public static final int BEHAVIOR_FAVORITE = 2;
    public static final int BEHAVIOR_ADD_TO_CART = 3;
    public static final int BEHAVIOR_PURCHASE = 4;
    public static final int BEHAVIOR_RATING = 5;
    public static final int BEHAVIOR_COMMENT = 6;

    // 行为类型描述
    public String getBehaviorTypeDesc() {
        switch (behaviorType) {
            case BEHAVIOR_VIEW:
                return "浏览";
            case BEHAVIOR_FAVORITE:
                return "收藏";
            case BEHAVIOR_ADD_TO_CART:
                return "加入购物车";
            case BEHAVIOR_PURCHASE:
                return "购买";
            case BEHAVIOR_RATING:
                return "评分";
            case BEHAVIOR_COMMENT:
                return "评论";
            default:
                return "未知";
        }
    }
}