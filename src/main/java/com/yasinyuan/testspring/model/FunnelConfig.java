package com.yasinyuan.testspring.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 漏斗配置实体类
 * @author yasinyuan
 * @date 2025-05-02
 */
@Entity
@Table(name = "funnel_config")
public class FunnelConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 漏斗名称
     */
    @Column(name = "funnel_name", nullable = false)
    private String funnelName;

    /**
     * 漏斗步骤JSON配置
     * 格式：[{"stepName": "首页", "behaviorType": "browse", "pageUrl": "/home"}, ...]
     */
    @Column(name = "funnel_steps", columnDefinition = "TEXT", nullable = false)
    private String funnelSteps;

    /**
     * 漏斗描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否启用
     */
    @Column(name = "status")
    private Integer status;

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

    public String getFunnelSteps() {
        return funnelSteps;
    }

    public void setFunnelSteps(String funnelSteps) {
        this.funnelSteps = funnelSteps;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
