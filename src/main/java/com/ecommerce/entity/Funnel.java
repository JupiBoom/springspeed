package com.ecommerce.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "funnel")
public class Funnel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "funnel_name", nullable = false, length = 64)
    private String funnelName;

    @Column(name = "description", length = 512)
    private String description;

    @Column(name = "funnel_steps", nullable = false, length = 1024)
    private String funnelSteps; // 逗号分隔的步骤列表，如"首页,详情页,加购,下单"

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_by", length = 64)
    private String createdBy;

    @Column(name = "created_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "last_updated_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedTime;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFunnelName() {
        return funnelName;
    }

    public void setFunnelName(String funnelName) {
        this.funnelName = funnelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFunnelSteps() {
        return funnelSteps;
    }

    public void setFunnelSteps(String funnelSteps) {
        this.funnelSteps = funnelSteps;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
