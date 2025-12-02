package com.yasinyuan.testspring.model;

import javax.persistence.*;
import java.util.Date;

/**
 * RFM用户分群实体类
 * @author yasinyuan
 * @date 2025-05-02
 */
@Entity
@Table(name = "user_rfm")
public class UserRfm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    /**
     * 最近一次购买时间
     */
    @Column(name = "recent_purchase_time")
    private Date recentPurchaseTime;

    /**
     * 购买频率
     */
    @Column(name = "purchase_frequency")
    private Integer purchaseFrequency;

    /**
     * 购买总金额
     */
    @Column(name = "monetary")
    private Double monetary;

    /**
     * R分
     */
    @Column(name = "r_score")
    private Integer rScore;

    /**
     * F分
     */
    @Column(name = "f_score")
    private Integer fScore;

    /**
     * M分
     */
    @Column(name = "m_score")
    private Integer mScore;

    /**
     * 用户群体标签：重要价值用户/重要发展用户/重要保持用户/重要挽留用户/一般价值用户/一般发展用户/一般保持用户/一般挽留用户
     */
    @Column(name = "user_segment")
    private String userSegment;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getRecentPurchaseTime() {
        return recentPurchaseTime;
    }

    public void setRecentPurchaseTime(Date recentPurchaseTime) {
        this.recentPurchaseTime = recentPurchaseTime;
    }

    public Integer getPurchaseFrequency() {
        return purchaseFrequency;
    }

    public void setPurchaseFrequency(Integer purchaseFrequency) {
        this.purchaseFrequency = purchaseFrequency;
    }

    public Double getMonetary() {
        return monetary;
    }

    public void setMonetary(Double monetary) {
        this.monetary = monetary;
    }

    public Integer getRScore() {
        return rScore;
    }

    public void setRScore(Integer rScore) {
        this.rScore = rScore;
    }

    public Integer getFScore() {
        return fScore;
    }

    public void setFScore(Integer fScore) {
        this.fScore = fScore;
    }

    public Integer getMScore() {
        return mScore;
    }

    public void setMScore(Integer mScore) {
        this.mScore = mScore;
    }

    public String getUserSegment() {
        return userSegment;
    }

    public void setUserSegment(String userSegment) {
        this.userSegment = userSegment;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
