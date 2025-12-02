package com.yasinyuan.testspring.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户生命周期实体类
 * @author yasinyuan
 * @date 2025-05-02
 */
@Entity
@Table(name = "user_lifecycle")
public class UserLifecycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    /**
     * 用户生命周期阶段：新客/活跃/沉默/流失
     */
    @Column(name = "life_cycle_stage")
    private String lifeCycleStage;

    /**
     * 首次访问时间
     */
    @Column(name = "first_visit_time")
    private Date firstVisitTime;

    /**
     * 最后活跃时间
     */
    @Column(name = "last_active_time")
    private Date lastActiveTime;

    /**
     * 连续沉默天数
     */
    @Column(name = "silent_days")
    private Integer silentDays;

    /**
     * 活跃天数
     */
    @Column(name = "active_days")
    private Integer activeDays;

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

    public String getLifeCycleStage() {
        return lifeCycleStage;
    }

    public void setLifeCycleStage(String lifeCycleStage) {
        this.lifeCycleStage = lifeCycleStage;
    }

    public Date getFirstVisitTime() {
        return firstVisitTime;
    }

    public void setFirstVisitTime(Date firstVisitTime) {
        this.firstVisitTime = firstVisitTime;
    }

    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public Integer getSilentDays() {
        return silentDays;
    }

    public void setSilentDays(Integer silentDays) {
        this.silentDays = silentDays;
    }

    public Integer getActiveDays() {
        return activeDays;
    }

    public void setActiveDays(Integer activeDays) {
        this.activeDays = activeDays;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
