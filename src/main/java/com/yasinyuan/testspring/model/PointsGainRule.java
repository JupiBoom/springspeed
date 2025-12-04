package com.yasinyuan.testspring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * 积分获取规则配置表
 * @author yinyuan
 * @since 2024-01-01
 */
@Data
@Table(name = "points_gain_rule")
public class PointsGainRule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 业务类型（1：消费，2：签到，3：活动）
     */
    private Integer businessType;

    /**
     * 业务类型名称
     */
    private String businessTypeName;

    /**
     * 积分获取比例（消费时使用，例如1元=1积分）
     */
    private Double pointsRatio;

    /**
     * 固定积分值（签到或活动时使用）
     */
    private Integer fixedPoints;

    /**
     * 每日上限
     */
    private Integer dailyLimit;

    /**
     * 每月上限
     */
    private Integer monthlyLimit;

    /**
     * 积分有效期（天）
     */
    private Integer validityDays;

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