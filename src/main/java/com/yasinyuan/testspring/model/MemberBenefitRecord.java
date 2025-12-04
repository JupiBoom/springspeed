package com.yasinyuan.testspring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * 会员权益发放记录表
 * @author yinyuan
 * @since 2024-01-01
 */
@Data
@Table(name = "member_benefit_record")
public class MemberBenefitRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 会员ID（关联member表）
     */
    private Long memberId;

    /**
     * 权益类型（1：运费券，2：生日积分，3：专属客服）
     */
    private Integer benefitType;

    /**
     * 权益名称
     */
    private String benefitName;

    /**
     * 发放数量
     */
    private Integer quantity;

    /**
     * 有效期开始时间
     */
    private Date validityStartDate;

    /**
     * 有效期结束时间
     */
    private Date validityEndDate;

    /**
     * 状态（0：未使用，1：已使用，2：已过期）
     */
    private Integer status;

    /**
     * 发放原因
     */
    private String reason;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}