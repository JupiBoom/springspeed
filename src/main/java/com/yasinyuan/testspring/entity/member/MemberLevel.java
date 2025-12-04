package com.yasinyuan.testspring.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "member_level")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name; // 会员等级名称：普通/白银/黄金/铂金

    @Column(name = "level", nullable = false, unique = true)
    private Integer level; // 等级：1-普通，2-白银，3-黄金，4-铂金

    @Column(name = "min_growth_value")
    private Integer minGrowthValue; // 最低成长值

    @Column(name = "max_growth_value")
    private Integer maxGrowthValue; // 最高成长值

    @Column(name = "discount")
    private Double discount; // 折扣

    @Column(name = "freight_coupon_count")
    private Integer freightCouponCount; // 运费券数量

    @Column(name = "priority_shipping")
    private Boolean priorityShipping; // 是否优先发货

    @Column(name = "description")
    private String description; // 等级描述

    @Column(name = "status")
    private Integer status = 1; // 1:启用, 0:禁用

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Member> members; // 该等级下的会员

    // 构造函数
    public MemberLevel() {}

    // getter和setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    public Integer getMinGrowthValue() { return minGrowthValue; }
    public void setMinGrowthValue(Integer minGrowthValue) { this.minGrowthValue = minGrowthValue; }
    public Integer getMaxGrowthValue() { return maxGrowthValue; }
    public void setMaxGrowthValue(Integer maxGrowthValue) { this.maxGrowthValue = maxGrowthValue; }
    public Double getDiscount() { return discount; }
    public void setDiscount(Double discount) { this.discount = discount; }
    public Integer getFreightCouponCount() { return freightCouponCount; }
    public void setFreightCouponCount(Integer freightCouponCount) { this.freightCouponCount = freightCouponCount; }
    public Boolean getPriorityShipping() { return priorityShipping; }
    public void setPriorityShipping(Boolean priorityShipping) { this.priorityShipping = priorityShipping; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public List<Member> getMembers() { return members; }
    public void setMembers(List<Member> members) { this.members = members; }
}
