package com.yasinyuan.testspring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * 成长值记录表
 * @author yinyuan
 * @since 2024-01-01
 */
@Data
@Table(name = "growth_value_record")
public class GrowthValueRecord implements Serializable {
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
     * 变动类型（1：增加，2：减少）
     */
    private Integer changeType;

    /**
     * 变动值
     */
    private Integer changeValue;

    /**
     * 变动前成长值
     */
    private Integer beforeValue;

    /**
     * 变动后成长值
     */
    private Integer afterValue;

    /**
     * 业务类型（1：消费，2：签到，3：活动，4：过期扣除）
     */
    private Integer businessType;

    /**
     * 业务ID
     */
    private Long businessId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;
}