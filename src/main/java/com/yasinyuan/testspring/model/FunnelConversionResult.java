package com.yasinyuan.testspring.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "funnel_conversion_result")
public class FunnelConversionResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "funnel_id", nullable = false)
    private Long funnelId;

    @Column(name = "funnel_name", nullable = false, length = 100)
    private String funnelName;

    @Column(name = "step_order", nullable = false)
    private Integer stepOrder;

    @Column(name = "step_name", nullable = false, length = 100)
    private String stepName;

    @Column(name = "user_count", nullable = false)
    private Long userCount;

    @Column(name = "conversion_rate", precision = 10, scale = 4)
    private Double conversionRate;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    public FunnelConversionResult() {
        this.createTime = new Date();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFunnelId() {
        return funnelId;
    }

    public void setFunnelId(Long funnelId) {
        this.funnelId = funnelId;
    }

    public String getFunnelName() {
        return funnelName;
    }

    public void setFunnelName(String funnelName) {
        this.funnelName = funnelName;
    }

    public Integer getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
