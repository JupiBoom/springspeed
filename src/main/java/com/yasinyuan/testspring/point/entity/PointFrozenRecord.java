package com.yasinyuan.testspring.point.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 积分冻结记录
 */
@Data
@TableName("t_point_frozen_record")
public class PointFrozenRecord implements Serializable {
    
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
     * 冻结积分值
     */
    private Integer frozenValue;
    
    /**
     * 剩余冻结值
     */
    private Integer remainingValue;
    
    /**
     * 冻结原因
     */
    private String reason;
    
    /**
     * 冻结开始时间
     */
    private LocalDateTime freezeTime;
    
    /**
     * 冻结结束时间
     */
    private LocalDateTime unfreezeTime;
    
    /**
     * 状态：0-冻结中，1-已解冻，2-已过期
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}