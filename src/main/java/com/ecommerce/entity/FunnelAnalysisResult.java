package com.ecommerce.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "funnel_analysis_result")
public class FunnelAnalysisResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "funnel_id", nullable = false)
    private Long funnelId;

    @Column(name = "step_index", nullable = false)
    private Integer stepIndex; // 步骤索引，从0开始

    @Column(name = "step_name", nullable = false, length = 64)
    private String stepName;

    @Column(name = "user_count", nullable = false)
    private Integer userCount; // 该步骤的用户数量

    @Column(name = "conversion_rate")
    private Double conversionRate; // 相对于上一步的转化率

    @Column(name = "overall_conversion_rate")
    private Double overallConversionRate; // 相对于第一步的转化率

    @Column(name = "analysis_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date analysisDate; // 分析日期

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

    public Long getFunnelId() {
        return funnelId;
    }

    public void setFunnelId(Long funnelId) {
        this.funnelId = funnelId;
    }

    public Integer getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(Integer stepIndex) {
        this.stepIndex = stepIndex;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public Double getOverallConversionRate() {
        return overallConversionRate;
    }

    public void setOverallConversionRate(Double overallConversionRate) {
        this.overallConversionRate = overallConversionRate;
    }

    public Date getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate(Date analysisDate) {
        this.analysisDate = analysisDate;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
