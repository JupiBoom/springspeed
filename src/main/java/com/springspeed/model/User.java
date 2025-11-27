package com.springspeed.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * User Entity
 * 包含用户基本信息和人口属性标签
 */
@Entity
@Table(name = "ss_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    // 人口属性标签
    @Column(name = "gender")
    private Integer gender; // 0: unknown, 1: male, 2: female

    @Column(name = "age")
    private Integer age;

    @Column(name = "region", length = 100)
    private String region; // 地域信息

    @Column(name = "occupation", length = 100)
    private String occupation;

    @Column(name = "income_level")
    private Integer incomeLevel; // 收入水平

    // 用户画像相关字段
    @Column(name = "interest_tags", length = 1000)
    private String interestTags; // 用户兴趣标签，逗号分隔

    @Column(name = "last_active_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActiveTime;

    // RFM模型相关字段
    @Column(name = "recency")
    private Integer recency; // 最近一次消费时间间隔（天）

    @Column(name = "frequency")
    private Integer frequency; // 消费频率

    @Column(name = "monetary")
    private Double monetary; // 消费金额

    @Column(name = "rfm_score")
    private Integer rfmScore; // RFM总分

    @Column(name = "user_segment")
    private String userSegment; // 用户细分（如：重要价值客户）

    @Column(name = "status")
    private Integer status; // 0: inactive, 1: active

    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "updated_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    // 关联关系
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserBehavior> userBehaviors;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Integer getGender() { return gender; }
    public void setGender(Integer gender) { this.gender = gender; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }
    public Integer getIncomeLevel() { return incomeLevel; }
    public void setIncomeLevel(Integer incomeLevel) { this.incomeLevel = incomeLevel; }
    public String getInterestTags() { return interestTags; }
    public void setInterestTags(String interestTags) { this.interestTags = interestTags; }
    public Date getLastActiveTime() { return lastActiveTime; }
    public void setLastActiveTime(Date lastActiveTime) { this.lastActiveTime = lastActiveTime; }
    public Integer getRecency() { return recency; }
    public void setRecency(Integer recency) { this.recency = recency; }
    public Integer getFrequency() { return frequency; }
    public void setFrequency(Integer frequency) { this.frequency = frequency; }
    public Double getMonetary() { return monetary; }
    public void setMonetary(Double monetary) { this.monetary = monetary; }
    public Integer getRfmScore() { return rfmScore; }
    public void setRfmScore(Integer rfmScore) { this.rfmScore = rfmScore; }
    public String getUserSegment() { return userSegment; }
    public void setUserSegment(String userSegment) { this.userSegment = userSegment; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Date getCreatedTime() { return createdTime; }
    public void setCreatedTime(Date createdTime) { this.createdTime = createdTime; }
    public Date getUpdatedTime() { return updatedTime; }
    public void setUpdatedTime(Date updatedTime) { this.updatedTime = updatedTime; }
    public List<UserBehavior> getUserBehaviors() { return userBehaviors; }
    public void setUserBehaviors(List<UserBehavior> userBehaviors) { this.userBehaviors = userBehaviors; }
    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
    public List<CartItem> getCartItems() { return cartItems; }
    public void setCartItems(List<CartItem> cartItems) { this.cartItems = cartItems; }

    @PrePersist
    protected void onCreate() {
        createdTime = new Date();
        updatedTime = new Date();
        if (status == null) status = 1;
        if (gender == null) gender = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = new Date();
    }
}