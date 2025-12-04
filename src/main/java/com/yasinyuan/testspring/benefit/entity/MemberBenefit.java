package com.yasinyuan.testspring.benefit.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会员权益
 */
@Data
@TableName("t_member_benefit")
public class MemberBenefit implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 权益ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 权益名称
     */
    private String benefitName;
    
    /**
     * 权益类型：1-折扣优惠，2-运费券，3-优先发货，4-生日特权，5-专属客服，6-专属活动
     */
    private Integer benefitType;
    
    /**
     * 适用等级代码（多个用逗号分隔）
     */
    private String applicableLevels;
    
    /**
     * 权益值（如折扣比例、运费券金额等）
     */
    private Integer benefitValue;
    
    /**
     * 发放周期：1-一次性，2-每日，3-每月，4-每年
     */
    private Integer issueCycle;
    
    /**
     * 每月发放数量
     */
    private Integer monthlyIssueCount;
    
    /**
     * 每人限领数量
     */
    private Integer limitCountPerPerson;
    
    /**
     * 有效期天数
     */
    private Integer validDays;
    
    /**
     * 防刷配置（如每日限领次数）
     */
    private String antiBrushConfig;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}