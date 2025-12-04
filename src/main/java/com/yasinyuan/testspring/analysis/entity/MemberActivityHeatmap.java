package com.yasinyuan.testspring.analysis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会员活跃度热力图
 */
@Data
@TableName("t_member_activity_heatmap")
public class MemberActivityHeatmap implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 统计日期
     */
    private LocalDateTime statisticsDate;
    
    /**
     * 统计维度：1-日期，2-小时，3-星期，4-月份
     */
    private Integer statisticsDimension;
    
    /**
     * 活跃会员数
     */
    private Integer activeMemberCount;
    
    /**
     * 新增会员数
     */
    private Integer newMemberCount;
    
    /**
     * 消费会员数
     */
    private Integer purchaseMemberCount;
    
    /**
     * 签到会员数
     */
    private Integer signInMemberCount;
    
    /**
     * 积分使用会员数
     */
    private Integer pointUsageMemberCount;
    
    /**
     * 活跃度指数（0-100）
     */
    private Integer activityIndex;
    
    /**
     * 活跃时段分布（JSON格式，按小时统计）
     */
    private String hourlyDistribution;
    
    /**
     * 活跃等级分布（JSON格式，按会员等级统计）
     */
    private String levelDistribution;
    
    /**
     * 活跃区域分布（JSON格式，按地区统计）
     */
    private String regionDistribution;
    
    /**
     * 环比增长率（%）
     */
    private Double momGrowthRate;
    
    /**
     * 同比增长率（%）
     */
    private Double yoyGrowthRate;
    
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