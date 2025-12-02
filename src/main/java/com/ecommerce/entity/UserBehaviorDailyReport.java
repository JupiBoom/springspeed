package com.ecommerce.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_behavior_daily_report")
public class UserBehaviorDailyReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date reportDate;

    @Column(name = "total_users")
    private Integer totalUsers; // 总用户数

    @Column(name = "new_users")
    private Integer newUsers; // 新增用户数

    @Column(name = "active_users")
    private Integer activeUsers; // 活跃用户数

    @Column(name = "total_sessions")
    private Integer totalSessions; // 总会话数

    @Column(name = "avg_session_duration")
    private Double avgSessionDuration; // 平均会话时长（秒）

    @Column(name = "view_count")
    private Integer viewCount; // 浏览次数

    @Column(name = "click_count")
    private Integer clickCount; // 点击次数

    @Column(name = "add_to_cart_count")
    private Integer addToCartCount; // 加购次数

    @Column(name = "order_count")
    private Integer orderCount; // 下单次数

    @Column(name = "conversion_rate")
    private Double conversionRate; // 整体转化率（下单用户数/活跃用户数）

    @Column(name = "created_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Integer getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Integer totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Integer getNewUsers() {
        return newUsers;
    }

    public void setNewUsers(Integer newUsers) {
        this.newUsers = newUsers;
    }

    public Integer getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(Integer activeUsers) {
        this.activeUsers = activeUsers;
    }

    public Integer getTotalSessions() {
        return totalSessions;
    }

    public void setTotalSessions(Integer totalSessions) {
        this.totalSessions = totalSessions;
    }

    public Double getAvgSessionDuration() {
        return avgSessionDuration;
    }

    public void setAvgSessionDuration(Double avgSessionDuration) {
        this.avgSessionDuration = avgSessionDuration;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Integer getAddToCartCount() {
        return addToCartCount;
    }

    public void setAddToCartCount(Integer addToCartCount) {
        this.addToCartCount = addToCartCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
