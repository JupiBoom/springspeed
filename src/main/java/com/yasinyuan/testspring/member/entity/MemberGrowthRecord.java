package com.yasinyuan.testspring.member.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会员成长值记录
 */
@Data
@TableName("t_member_growth_record")
public class MemberGrowthRecord implements Serializable {
    
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
     * 变动类型：1-消费获得，2-签到获得，3-活动奖励，4-等级扣除，5-过期扣除
     */
    private Integer changeType;
    
    /**
     * 变动值（正数为增加，负数为减少）
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
     * 关联业务ID（如订单ID、活动ID等）
     */
    private String businessId;
    
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