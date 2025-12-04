package com.yasinyuan.testspring.entity.benefit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yasinyuan.testspring.entity.member.MemberLevel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "member_benefit")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberBenefit implements Serializable {

    private static final long serialVersionUID = 1L;

    // 权益类型
    public enum BenefitType {
        DISCOUNT(1), // 折扣
        FREIGHT_COUPON(2), // 运费券
        PRIORITY_SHIPPING(3), // 优先发货
        BIRTHDAY_GIFT(4), // 生日特权
        EXCLUSIVE_SERVICE(5); // 专属客服

        private Integer value;

        BenefitType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static BenefitType fromValue(Integer value) {
            for (BenefitType type : BenefitType.values()) {
                if (type.getValue().equals(value)) {
                    return type;
                }
            }
            return null;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id", nullable = false)
    private MemberLevel level; // 关联的会员等级

    @Column(name = "benefit_type", nullable = false)
    private Integer benefitType; // 权益类型

    @Column(name = "benefit_name", nullable = false)
    private String benefitName; // 权益名称

    @Column(name = "benefit_value")
    private String benefitValue; // 权益值（如折扣率、券数量等）

    @Column(name = "description")
    private String description; // 权益描述

    @Column(name = "is_active")
    private Boolean isActive = true; // 是否激活

    @Column(name = "start_date")
    private Date startDate; // 生效开始时间

    @Column(name = "end_date")
    private Date endDate; // 生效结束时间

    @Column(name = "anti_brush_enabled")
    private Boolean antiBrushEnabled = false; // 是否启用防刷

    @Column(name = "anti_brush_rule")
    private String antiBrushRule; // 防刷规则

    // 构造函数
    public MemberBenefit() {}

    // getter和setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public MemberLevel getLevel() { return level; }
    public void setLevel(MemberLevel level) { this.level = level; }
    public Integer getBenefitType() { return benefitType; }
    public void setBenefitType(Integer benefitType) { this.benefitType = benefitType; }
    public String getBenefitName() { return benefitName; }
    public void setBenefitName(String benefitName) { this.benefitName = benefitName; }
    public String getBenefitValue() { return benefitValue; }
    public void setBenefitValue(String benefitValue) { this.benefitValue = benefitValue; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public Boolean getAntiBrushEnabled() { return antiBrushEnabled; }
    public void setAntiBrushEnabled(Boolean antiBrushEnabled) { this.antiBrushEnabled = antiBrushEnabled; }
    public String getAntiBrushRule() { return antiBrushRule; }
    public void setAntiBrushRule(String antiBrushRule) { this.antiBrushRule = antiBrushRule; }
}
