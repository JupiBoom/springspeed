package com.yasinyuan.testspring.entity.points;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yasinyuan.testspring.entity.member.Member;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "points_account")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member; // 关联的会员

    @Column(name = "available_points")
    private Integer availablePoints = 0; // 可用积分

    @Column(name = "frozen_points")
    private Integer frozenPoints = 0; // 冻结积分

    @Column(name = "expired_points")
    private Integer expiredPoints = 0; // 已过期积分

    @Column(name = "total_points")
    private Integer totalPoints = 0; // 总积分（历史累计）

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "last_update_date")
    private Date lastUpdateDate; // 最后更新时间

    // 构造函数
    public PointsAccount() {}

    // getter和setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public Integer getAvailablePoints() { return availablePoints; }
    public void setAvailablePoints(Integer availablePoints) { this.availablePoints = availablePoints; }
    public Integer getFrozenPoints() { return frozenPoints; }
    public void setFrozenPoints(Integer frozenPoints) { this.frozenPoints = frozenPoints; }
    public Integer getExpiredPoints() { return expiredPoints; }
    public void setExpiredPoints(Integer expiredPoints) { this.expiredPoints = expiredPoints; }
    public Integer getTotalPoints() { return totalPoints; }
    public void setTotalPoints(Integer totalPoints) { this.totalPoints = totalPoints; }
    public Date getLastUpdateDate() { return lastUpdateDate; }
    public void setLastUpdateDate(Date lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
}
