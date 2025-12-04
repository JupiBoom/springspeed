package com.yasinyuan.testspring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * 积分记录表
 * @author yinyuan
 * @since 2024-01-01
 */
@Data
@Table(name = "points_record")
public class PointsRecord implements Serializable {
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
     * 积分账户ID（关联points_account表）
     */
    private Long accountId;

    /**
     * 变动类型（1：增加，2：减少，3：冻结，4：解冻，5：过期）
     */
    private Integer changeType;

    /**
     * 变动值
     */
    private Integer changeValue;

    /**
     * 变动前可用积分
     */
    private Integer beforeAvailable;

    /**
     * 变动后可用积分
     */
    private Integer afterAvailable;

    /**
     * 变动前冻结积分
     */
    private Integer beforeFrozen;

    /**
     * 变动后冻结积分
     */
    private Integer afterFrozen;

    /**
     * 业务类型（1：消费获取，2：签到获取，3：活动获取，4：消费抵扣，5：积分兑换，6：抽奖消耗，7：过期扣除）
     */
    private Integer businessType;

    /**
     * 业务ID
     */
    private Long businessId;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;
}