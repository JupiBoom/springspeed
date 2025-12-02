package com.yasinyuan.testspring.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_daily_report")
public class UserDailyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_date", nullable = false, unique = true)
    private Date reportDate;

    @Column(name = "new_users", nullable = false)
    private Long newUsers;

    @Column(name = "active_users", nullable = false)
    private Long activeUsers;

    @Column(name = "total_visits", nullable = false)
    private Long totalVisits;

    @Column(name = "page_views", nullable = false)
    private Long pageViews;

    @Column(name = "avg_session_duration", nullable = false)
    private Long avgSessionDuration;

    @Column(name = "browse_count", nullable = false)
    private Long browseCount;

    @Column(name = "click_count", nullable = false)
    private Long clickCount;

    @Column(name = "add_cart_count", nullable = false)
    private Long addCartCount;

    @Column(name = "order_count", nullable = false)
    private Long orderCount;

    @Column(name = "order_amount", nullable = false, precision = 10, scale = 2)
    private Double orderAmount;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    public UserDailyReport() {
        this.createTime = new Date();
    }

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

    public Long getNewUsers() {
        return newUsers;
    }

    public void setNewUsers(Long newUsers) {
        this.newUsers = newUsers;
    }

    public Long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(Long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public Long getTotalVisits() {
        return totalVisits;
    }

    public void setTotalVisits(Long totalVisits) {
        this.totalVisits = totalVisits;
    }

    public Long getPageViews() {
        return pageViews;
    }

    public void setPageViews(Long pageViews) {
        this.pageViews = pageViews;
    }

    public Long getAvgSessionDuration() {
        return avgSessionDuration;
    }

    public void setAvgSessionDuration(Long avgSessionDuration) {
        this.avgSessionDuration = avgSessionDuration;
    }

    public Long getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Long browseCount) {
        this.browseCount = browseCount;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public Long getAddCartCount() {
        return addCartCount;
    }

    public void setAddCartCount(Long addCartCount) {
        this.addCartCount = addCartCount;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
