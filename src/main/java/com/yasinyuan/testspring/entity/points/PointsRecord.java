package com.yasinyuan.testspring.entity.points;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yasinyuan.testspring.entity.member.Member;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "points_record")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    // 积分变动类型
    public enum PointsType {
        EARN(1), // 获得积分
        CONSUME(2), // 消耗积分
        FROZEN(3), // 冻结积分
        UNFROZEN(4), // 解冻积分
        EXPIRED(5); // 积分过期

        private Integer value;

        PointsType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static PointsType fromValue(Integer value) {
            for (PointsType type : PointsType.values()) {
                if (type.getValue().equals(value)) {
                    return type;
                }
            }
            return null;
        }
    }

    // 积分来源/消耗场景
    public enum PointsScene {
        PURCHASE(1), // 消费
        SIGN_IN(2), // 签到
        ACTIVITY(3), // 活动
        DEDUCTION(4), // 抵扣
        EXCHANGE(5), // 兑换
        LOTTERY(6); // 抽奖

        private Integer value;

        PointsScene(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static PointsScene fromValue(Integer value) {
            for (PointsScene scene : PointsScene.values()) {
                if (scene.getValue().equals(value)) {
                    return scene;
                }
            }
            return null;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 关联的会员

    @Column(name = "points_type", nullable = false)
    private Integer pointsType; // 积分变动类型

    @Column(name = "points_scene")
    private Integer pointsScene; // 积分来源/消耗场景

    @Column(name = "points_amount", nullable = false)
    private Integer pointsAmount; // 积分数量

    @Column(name = "available_points_before")
    private Integer availablePointsBefore; // 变动前可用积分

    @Column(name = "available_points_after")
    private Integer availablePointsAfter; // 变动后可用积分

    @Column(name = "frozen_points_before")
    private Integer frozenPointsBefore; // 变动前冻结积分

    @Column(name = "frozen_points_after")
    private Integer frozenPointsAfter; // 变动后冻结积分

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "expire_date")
    private Date expireDate; // 积分过期时间

    @Column(name = "related_id")
    private Long relatedId; // 关联业务ID（如订单ID、活动ID等）

    @Column(name = "description")
    private String description; // 变动描述

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_date", nullable = false)
    private Date createDate; // 创建时间

    // 构造函数
    public PointsRecord() {}

    // getter和setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public Integer getPointsType() { return pointsType; }
    public void setPointsType(Integer pointsType) { this.pointsType = pointsType; }
    public Integer getPointsScene() { return pointsScene; }
    public void setPointsScene(Integer pointsScene) { this.pointsScene = pointsScene; }
    public Integer getPointsAmount() { return pointsAmount; }
    public void setPointsAmount(Integer pointsAmount) { this.pointsAmount = pointsAmount; }
    public Integer getAvailablePointsBefore() { return availablePointsBefore; }
    public void setAvailablePointsBefore(Integer availablePointsBefore) { this.availablePointsBefore = availablePointsBefore; }
    public Integer getAvailablePointsAfter() { return availablePointsAfter; }
    public void setAvailablePointsAfter(Integer availablePointsAfter) { this.availablePointsAfter = availablePointsAfter; }
    public Integer getFrozenPointsBefore() { return frozenPointsBefore; }
    public void setFrozenPointsBefore(Integer frozenPointsBefore) { this.frozenPointsBefore = frozenPointsBefore; }
    public Integer getFrozenPointsAfter() { return frozenPointsAfter; }
    public void setFrozenPointsAfter(Integer frozenPointsAfter) { this.frozenPointsAfter = frozenPointsAfter; }
    public Date getExpireDate() { return expireDate; }
    public void setExpireDate(Date expireDate) { this.expireDate = expireDate; }
    public Long getRelatedId() { return relatedId; }
    public void setRelatedId(Long relatedId) { this.relatedId = relatedId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }
}
