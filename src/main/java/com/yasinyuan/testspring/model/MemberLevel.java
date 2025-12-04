package com.yasinyuan.testspring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * 会员等级表
 * @author yinyuan
 * @since 2024-01-01
 */
@Data
@Table(name = "member_level")
public class MemberLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 等级名称（普通/白银/黄金/铂金）
     */
    private String levelName;

    /**
     * 等级编码（NORMAL/SILVER/GOLD/PLATINUM）
     */
    private String levelCode;

    /**
     * 所需成长值下限
     */
    private Integer growthValueMin;

    /**
     * 所需成长值上限
     */
    private Integer growthValueMax;

    /**
     * 等级折扣（0.0-1.0）
     */
    private Double discount;

    /**
     * 每月运费券数量
     */
    private Integer freightCouponCount;

    /**
     * 是否享受优先发货
     */
    private Boolean priorityShipping;

    /**
     * 是否享受专属客服
     */
    private Boolean exclusiveService;

    /**
     * 生日特权（额外积分）
     */
    private Integer birthdayBonusPoints;

    /**
     * 状态（0：禁用，1：启用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}