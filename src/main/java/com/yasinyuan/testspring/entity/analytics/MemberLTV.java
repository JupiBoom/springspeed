package com.yasinyuan.testspring.entity.analytics;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yasinyuan.testspring.entity.member.Member;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "member_ltv")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberLTV implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member; // 关联的会员

    @Column(name = "total_purchase_amount")
    private Double totalPurchaseAmount = 0.0; // 总购买金额

    @Column(name = "total_orders")
    private Integer totalOrders = 0; // 总订单数

    @Column(name = "average_order_value")
    private Double averageOrderValue = 0.0; // 平均订单价值

    @Column(name = "total_points_earned")
    private Integer totalPointsEarned = 0; // 总积分获取

    @Column(name = "total_points_used")
    private Integer totalPointsUsed = 0; // 总积分使用

    @Column(name = "points_conversion_rate")
    private Double pointsConversionRate = 0.0; // 积分转化率

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "first_purchase_date")
    private Date firstPurchaseDate; // 首次购买时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "last_purchase_date")
    private Date lastPurchaseDate; // 最后购买时间

    @Column(name = "member_age_days")
    private Integer memberAgeDays = 0; // 会员天数

    @Column(name = "ltv_score")
    private Double ltvScore = 0.0; // LTV评分

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "last_calculation_date")
    private Date lastCalculationDate; // 最后计算时间

    // 构造函数
    public MemberLTV() {}

    // getter和setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public Double getTotalPurchaseAmount() { return totalPurchaseAmount; }
    public void setTotalPurchaseAmount(Double totalPurchaseAmount) { this.totalPurchaseAmount = totalPurchaseAmount; }
    public Integer getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Integer totalOrders) { this.totalOrders = totalOrders; }
    public Double getAverageOrderValue() { return averageOrderValue; }
    public void setAverageOrderValue(Double averageOrderValue) { this.averageOrderValue = averageOrderValue; }
    public Integer getTotalPointsEarned() { return totalPointsEarned; }
    public void setTotalPointsEarned(Integer totalPointsEarned) { this.totalPointsEarned = totalPointsEarned; }
    public Integer getTotalPointsUsed() { return totalPointsUsed; }
    public void setTotalPointsUsed(Integer totalPointsUsed) { this.totalPointsUsed = totalPointsUsed; }
    public Double getPointsConversionRate() { return pointsConversionRate; }
    public void setPointsConversionRate(Double pointsConversionRate) { this.pointsConversionRate = pointsConversionRate; }
    public Date getFirstPurchaseDate() { return firstPurchaseDate; }
    public void setFirstPurchaseDate(Date firstPurchaseDate) { this.firstPurchaseDate = firstPurchaseDate; }
    public Date getLastPurchaseDate() { return lastPurchaseDate; }
    public void setLastPurchaseDate(Date lastPurchaseDate) { this.lastPurchaseDate = lastPurchaseDate; }
    public Integer getMemberAgeDays() { return memberAgeDays; }
    public void setMemberAgeDays(Integer memberAgeDays) { this.memberAgeDays = memberAgeDays; }
    public Double getLtvScore() { return ltvScore; }
    public void setLtvScore(Double ltvScore) { this.ltvScore = ltvScore; }
    public Date getLastCalculationDate() { return lastCalculationDate; }
    public void setLastCalculationDate(Date lastCalculationDate) { this.lastCalculationDate = lastCalculationDate; }
}
