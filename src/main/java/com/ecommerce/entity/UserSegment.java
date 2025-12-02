package com.ecommerce.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_segment")
public class UserSegment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "segment_type", nullable = false, length = 32)
    private String segmentType; // RFM, LIFECYCLE, CUSTOM

    @Column(name = "segment_name", nullable = false, length = 64)
    private String segmentName;

    @Column(name = "segment_value", length = 128)
    private String segmentValue;

    @Column(name = "recency")
    private Integer recency; // RFM模型中的最近购买天数

    @Column(name = "frequency")
    private Integer frequency; // RFM模型中的购买频率

    @Column(name = "monetary")
    private Double monetary; // RFM模型中的购买金额

    @Column(name = "lifecycle_stage", length = 32)
    private String lifecycleStage; // NEW, ACTIVE, SILENT, CHURNED

    @Column(name = "last_updated_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedTime;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSegmentType() {
        return segmentType;
    }

    public void setSegmentType(String segmentType) {
        this.segmentType = segmentType;
    }

    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    public String getSegmentValue() {
        return segmentValue;
    }

    public void setSegmentValue(String segmentValue) {
        this.segmentValue = segmentValue;
    }

    public Integer getRecency() {
        return recency;
    }

    public void setRecency(Integer recency) {
        this.recency = recency;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Double getMonetary() {
        return monetary;
    }

    public void setMonetary(Double monetary) {
        this.monetary = monetary;
    }

    public String getLifecycleStage() {
        return lifecycleStage;
    }

    public void setLifecycleStage(String lifecycleStage) {
        this.lifecycleStage = lifecycleStage;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
