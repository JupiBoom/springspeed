package com.yasinyuan.testspring.benefit.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 生日特权配置
 */
@Data
@TableName("t_birthday_privilege_config")
public class BirthdayPrivilegeConfig implements Serializable {
    
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
     * 生日积分奖励
     */
    private Integer pointReward;
    
    /**
     * 生日折扣（0-100）
     */
    private Integer birthdayDiscount;
    
    /**
     * 生日优惠券金额
     */
    private Integer couponAmount;
    
    /**
     * 提前几天发放
     */
    private Integer issueDaysInAdvance;
    
    /**
     * 特权有效期天数
     */
    private Integer validDays;
    
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