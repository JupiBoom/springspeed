package com.yasinyuan.testspring.entity.analytics;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "member_activity_heatmap")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberActivityHeatmap implements Serializable {

    private static final long serialVersionUID = 1L;

    // 时间粒度
    public enum TimeGranularity {
        HOURLY(1), // 按小时
        DAILY(2), // 按天
        WEEKLY(3), // 按周
        MONTHLY(4); // 按月

        private Integer value;

        TimeGranularity(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static TimeGranularity fromValue(Integer value) {
            for (TimeGranularity granularity : TimeGranularity.values()) {
                if (granularity.getValue().equals(value)) {
                    return granularity;
                }
            }
            return null;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_granularity", nullable = false)
    private Integer timeGranularity; // 时间粒度

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "start_time", nullable = false)
    private Date startTime; // 时间区间开始

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "end_time", nullable = false)
    private Date endTime; // 时间区间结束

    @Column(name = "login_count")
    private Integer loginCount = 0; // 登录次数

    @Column(name = "purchase_count")
    private Integer purchaseCount = 0; // 购买次数

    @Column(name = "points_earn_count")
    private Integer pointsEarnCount = 0; // 积分获取次数

    @Column(name = "points_use_count")
    private Integer pointsUseCount = 0; // 积分使用次数

    @Column(name = "active_member_count")
    private Integer activeMemberCount = 0; // 活跃会员数量

    @Column(name = "new_member_count")
    private Integer newMemberCount = 0; // 新会员数量

    @Column(name = "churn_member_count")
    private Integer churnMemberCount = 0; // 流失会员数量

    @Column(name = "activity_score")
    private Double activityScore = 0.0; // 活跃度评分

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_date", nullable = false)
    private Date createDate; // 创建时间

    // 构造函数
    public MemberActivityHeatmap() {}

    // getter和setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getTimeGranularity() { return timeGranularity; }
    public void setTimeGranularity(Integer timeGranularity) { this.timeGranularity = timeGranularity; }
    public Date getStartTime() { return startTime; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }
    public Date getEndTime() { return endTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }
    public Integer getLoginCount() { return loginCount; }
    public void setLoginCount(Integer loginCount) { this.loginCount = loginCount; }
    public Integer getPurchaseCount() { return purchaseCount; }
    public void setPurchaseCount(Integer purchaseCount) { this.purchaseCount = purchaseCount; }
    public Integer getPointsEarnCount() { return pointsEarnCount; }
    public void setPointsEarnCount(Integer pointsEarnCount) { this.pointsEarnCount = pointsEarnCount; }
    public Integer getPointsUseCount() { return pointsUseCount; }
    public void setPointsUseCount(Integer pointsUseCount) { this.pointsUseCount = pointsUseCount; }
    public Integer getActiveMemberCount() { return activeMemberCount; }
    public void setActiveMemberCount(Integer activeMemberCount) { this.activeMemberCount = activeMemberCount; }
    public Integer getNewMemberCount() { return newMemberCount; }
    public void setNewMemberCount(Integer newMemberCount) { this.newMemberCount = newMemberCount; }
    public Integer getChurnMemberCount() { return churnMemberCount; }
    public void setChurnMemberCount(Integer churnMemberCount) { this.churnMemberCount = churnMemberCount; }
    public Double getActivityScore() { return activityScore; }
    public void setActivityScore(Double activityScore) { this.activityScore = activityScore; }
    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }
}
