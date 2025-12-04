package com.yasinyuan.testspring.point.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 积分账户
 */
@Data
@TableName("t_point_account")
public class PointAccount implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 账户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 会员ID
     */
    private Long memberId;
    
    /**
     * 总积分
     */
    private Integer totalPoints;
    
    /**
     * 可用积分
     */
    private Integer availablePoints;
    
    /**
     * 冻结积分
     */
    private Integer frozenPoints;
    
    /**
     * 已过期积分
     */
    private Integer expiredPoints;
    
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