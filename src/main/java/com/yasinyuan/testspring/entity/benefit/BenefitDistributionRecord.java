package com.yasinyuan.testspring.entity.benefit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yasinyuan.testspring.entity.member.Member;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "benefit_distribution_record")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BenefitDistributionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    // 发放状态
    public enum DistributionStatus {
        PENDING(1), // 待发放
        SUCCESS(2), // 发放成功
        FAILED(3), // 发放失败
        CANCELLED(4); // 已取消

        private Integer value;

        DistributionStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static DistributionStatus fromValue(Integer value) {
            for (DistributionStatus status : DistributionStatus.values()) {
                if (status.getValue().equals(value)) {
                    return status;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benefit_id", nullable = false)
    private MemberBenefit benefit; // 关联的权益

    @Column(name = "distribution_status", nullable = false)
    private Integer distributionStatus; // 发放状态

    @Column(name = "benefit_value")
    private String benefitValue; // 实际发放的权益值

    @Column(name = "related_id")
    private Long relatedId; // 关联业务ID（如订单ID、生日活动ID等）

    @Column(name = "description")
    private String description; // 发放描述

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "distribution_date")
    private Date distributionDate; // 发放时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "expire_date")
    private Date expireDate; // 权益过期时间

    @Column(name = "failure_reason")
    private String failureReason; // 发放失败原因

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_date", nullable = false)
    private Date createDate; // 创建时间

    // 构造函数
    public BenefitDistributionRecord() {}

    // getter和setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public MemberBenefit getBenefit() { return benefit; }
    public void setBenefit(MemberBenefit benefit) { this.benefit = benefit; }
    public Integer getDistributionStatus() { return distributionStatus; }
    public void setDistributionStatus(Integer distributionStatus) { this.distributionStatus = distributionStatus; }
    public String getBenefitValue() { return benefitValue; }
    public void setBenefitValue(String benefitValue) { this.benefitValue = benefitValue; }
    public Long getRelatedId() { return relatedId; }
    public void setRelatedId(Long relatedId) { this.relatedId = relatedId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getDistributionDate() { return distributionDate; }
    public void setDistributionDate(Date distributionDate) { this.distributionDate = distributionDate; }
    public Date getExpireDate() { return expireDate; }
    public void setExpireDate(Date expireDate) { this.expireDate = expireDate; }
    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }
    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }
}
