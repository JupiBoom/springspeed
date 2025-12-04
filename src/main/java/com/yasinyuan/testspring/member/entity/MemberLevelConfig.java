package com.yasinyuan.testspring.member.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会员等级配置
 */
@Data
@TableName("t_member_level_config")
public class MemberLevelConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 配置ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 等级代码
     */
    private Integer levelCode;
    
    /**
     * 等级名称
     */
    private String levelName;
    
    /**
     * 所需成长值
     */
    private Integer requiredGrowthValue;
    
    /**
     * 有效期天数
     */
    private Integer validDays;
    
    /**
     * 折扣比例（0-100）
     */
    private Integer discountRate;
    
    /**
     * 每月运费券数量
     */
    private Integer monthlyFreightCouponCount;
    
    /**
     * 是否优先发货
     */
    private Boolean priorityShipping;
    
    /**
     * 生日积分倍数
     */
    private Integer birthdayPointMultiple;
    
    /**
     * 客服等级
     */
    private String customerServiceLevel;
    
    /**
     * 描述
     */
    private String description;
    
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