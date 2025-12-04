package com.yasinyuan.testspring.point.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 积分规则配置
 */
@Data
@TableName("t_point_rule_config")
public class PointRuleConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 配置ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 规则类型：1-消费积分，2-签到积分，3-活动积分，4-积分抵扣
     */
    private Integer ruleType;
    
    /**
     * 规则名称
     */
    private String ruleName;
    
    /**
     * 积分比例（如100元积100分则为1）
     */
    private Integer pointRatio;
    
    /**
     * 每日上限
     */
    private Integer dailyLimit;
    
    /**
     * 每月上限
     */
    private Integer monthlyLimit;
    
    /**
     * 积分有效期天数
     */
    private Integer expireDays;
    
    /**
     * 抵扣比例（0-100）
     */
    private Integer deductRatio;
    
    /**
     * 每日签到基础积分
     */
    private Integer signInBasePoints;
    
    /**
     * 连续签到额外积分
     */
    private Integer continuousSignInExtraPoints;
    
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