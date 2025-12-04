package com.yasinyuan.testspring.benefit.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权益发放记录
 */
@Data
@TableName("t_benefit_issue_record")
public class BenefitIssueRecord implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 会员ID
     */
    private Long memberId;
    
    /**
     * 权益ID
     */
    private Long benefitId;
    
    /**
     * 权益名称
     */
    private String benefitName;
    
    /**
     * 权益类型
     */
    private Integer benefitType;
    
    /**
     * 权益值
     */
    private Integer benefitValue;
    
    /**
     * 发放数量
     */
    private Integer issueCount;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 使用状态：0-未使用，1-已使用，2-已过期
     */
    private Integer useStatus;
    
    /**
     * 使用时间
     */
    private LocalDateTime useTime;
    
    /**
     * 防刷检查结果
     */
    private String antiBrushCheckResult;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}