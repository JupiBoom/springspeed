package com.yasinyuan.testspring.analysis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 积分使用效率分析
 */
@Data
@TableName("t_point_usage_efficiency")
public class PointUsageEfficiency implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 分析ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 统计周期：1-日，2-周，3-月，4-季度，5-年
     */
    private Integer statisticsCycle;
    
    /**
     * 统计开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 统计结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 新增积分总额
     */
    private Integer totalEarnedPoints;
    
    /**
     * 使用积分总额
     */
    private Integer totalUsedPoints;
    
    /**
     * 过期积分总额
     */
    private Integer totalExpiredPoints;
    
    /**
     * 积分使用率（使用积分/新增积分）
     */
    private BigDecimal usageRate;
    
    /**
     * 积分过期率（过期积分/新增积分）
     */
    private BigDecimal expireRate;
    
    /**
     * 平均积分留存天数
     */
    private Integer avgRetentionDays;
    
    /**
     * 积分消耗场景分布（JSON格式）
     */
    private String consumptionScenarioDistribution;
    
    /**
     * 积分获取渠道分布（JSON格式）
     */
    private String acquisitionChannelDistribution;
    
    /**
     * 各等级积分使用情况（JSON格式）
     */
    private String levelUsageDistribution;
    
    /**
     * 分析备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}