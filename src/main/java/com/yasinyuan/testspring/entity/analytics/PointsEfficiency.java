package com.yasinyuan.testspring.entity.analytics;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "points_efficiency")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsEfficiency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "statistics_date", nullable = false)
    private Date statisticsDate; // 统计日期

    @Column(name = "total_points_earned")
    private Integer totalPointsEarned = 0; // 当日总积分获取

    @Column(name = "total_points_used")
    private Integer totalPointsUsed = 0; // 当日总积分使用

    @Column(name = "points_balance")
    private Integer pointsBalance = 0; // 当日积分余额

    @Column(name = "points_utilization_rate")
    private Double pointsUtilizationRate = 0.0; // 积分使用率

    @Column(name = "average_points_per_earn")
    private Double averagePointsPerEarn = 0.0; // 每次获取的平均积分

    @Column(name = "average_points_per_use")
    private Double averagePointsPerUse = 0.0; // 每次使用的平均积分

    @Column(name = "earn_scene_distribution")
    private String earnSceneDistribution; // 积分获取场景分布（JSON格式）

    @Column(name = "use_scene_distribution")
    private String useSceneDistribution; // 积分使用场景分布（JSON格式）

    @Column(name = "member_count_with_points")
    private Integer memberCountWithPoints = 0; // 有积分的会员数量

    @Column(name = "active_member_count")
    private Integer activeMemberCount = 0; // 活跃会员数量（有积分变动的会员）

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_date", nullable = false)
    private Date createDate; // 创建时间

    // 构造函数
    public PointsEfficiency() {}

    // getter和setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getStatisticsDate() { return statisticsDate; }
    public void setStatisticsDate(Date statisticsDate) { this.statisticsDate = statisticsDate; }
    public Integer getTotalPointsEarned() { return totalPointsEarned; }
    public void setTotalPointsEarned(Integer totalPointsEarned) { this.totalPointsEarned = totalPointsEarned; }
    public Integer getTotalPointsUsed() { return totalPointsUsed; }
    public void setTotalPointsUsed(Integer totalPointsUsed) { this.totalPointsUsed = totalPointsUsed; }
    public Integer getPointsBalance() { return pointsBalance; }
    public void setPointsBalance(Integer pointsBalance) { this.pointsBalance = pointsBalance; }
    public Double getPointsUtilizationRate() { return pointsUtilizationRate; }
    public void setPointsUtilizationRate(Double pointsUtilizationRate) { this.pointsUtilizationRate = pointsUtilizationRate; }
    public Double getAveragePointsPerEarn() { return averagePointsPerEarn; }
    public void setAveragePointsPerEarn(Double averagePointsPerEarn) { this.averagePointsPerEarn = averagePointsPerEarn; }
    public Double getAveragePointsPerUse() { return averagePointsPerUse; }
    public void setAveragePointsPerUse(Double averagePointsPerUse) { this.averagePointsPerUse = averagePointsPerUse; }
    public String getEarnSceneDistribution() { return earnSceneDistribution; }
    public void setEarnSceneDistribution(String earnSceneDistribution) { this.earnSceneDistribution = earnSceneDistribution; }
    public String getUseSceneDistribution() { return useSceneDistribution; }
    public void setUseSceneDistribution(String useSceneDistribution) { this.useSceneDistribution = useSceneDistribution; }
    public Integer getMemberCountWithPoints() { return memberCountWithPoints; }
    public void setMemberCountWithPoints(Integer memberCountWithPoints) { this.memberCountWithPoints = memberCountWithPoints; }
    public Integer getActiveMemberCount() { return activeMemberCount; }
    public void setActiveMemberCount(Integer activeMemberCount) { this.activeMemberCount = activeMemberCount; }
    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }
}
