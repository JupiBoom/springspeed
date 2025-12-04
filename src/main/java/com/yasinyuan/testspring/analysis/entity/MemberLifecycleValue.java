package com.yasinyuan.testspring.analysis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 会员生命周期价值分析
 */
@Data
@TableName("t_member_lifecycle_value")
public class MemberLifecycleValue implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 分析ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 会员ID
     */
    private Long memberId;
    
    /**
     * 首次消费时间
     */
    private LocalDateTime firstPurchaseTime;
    
    /**
     * 末次消费时间
     */
    private LocalDateTime lastPurchaseTime;
    
    /**
     * 累计消费次数
     */
    private Integer totalPurchaseCount;
    
    /**
     * 累计消费金额
     */
    private BigDecimal totalPurchaseAmount;
    
    /**
     * 平均客单价
     */
    private BigDecimal avgOrderAmount;
    
    /**
     * 累计获取积分
     */
    private Integer totalEarnedPoints;
    
    /**
     * 累计使用积分
     */
    private Integer totalUsedPoints;
    
    /**
     * 会员生命周期天数
     */
    private Integer lifecycleDays;
    
    /**
     * R值（最近一次消费距今天数）
     */
    private Integer rValue;
    
    /**
     * F值（消费频率）
     */
    private Integer fValue;
    
    /**
     * M值（消费金额）
     */
    private BigDecimal mValue;
    
    /**
     * 会员价值等级：1-低价值，2-中价值，3-高价值，4-流失
     */
    private Integer valueLevel;
    
    /**
     * 分析周期：1-日，2-周，3-月，4-季度，5-年
     */
    private Integer analysisCycle;
    
    /**
     * 分析日期
     */
    private LocalDateTime analysisDate;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}