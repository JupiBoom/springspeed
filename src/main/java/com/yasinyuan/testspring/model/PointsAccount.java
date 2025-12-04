package com.yasinyuan.testspring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * 积分账户表
 * @author yinyuan
 * @since 2024-01-01
 */
@Data
@Table(name = "points_account")
public class PointsAccount implements Serializable {
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
     * 可用积分
     */
    private Integer availablePoints;

    /**
     * 冻结积分
     */
    private Integer frozenPoints;

    /**
     * 总积分
     */
    private Integer totalPoints;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}