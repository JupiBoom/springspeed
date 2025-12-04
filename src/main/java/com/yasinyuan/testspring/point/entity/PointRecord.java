package com.yasinyuan.testspring.point.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 积分变动记录
 */
@Data
@TableName("t_point_record")
public class PointRecord implements Serializable {
    
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
     * 变动类型
     */
    private Integer changeType;
    
    /**
     * 变动值（正数为增加，负数为减少）
     */
    private Integer changeValue;
    
    /**
     * 变动前总积分
     */
    private Integer beforeTotal;
    
    /**
     * 变动后总积分
     */
    private Integer afterTotal;
    
    /**
     * 变动前可用积分
     */
    private Integer beforeAvailable;
    
    /**
     * 变动后可用积分
     */
    private Integer afterAvailable;
    
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    
    /**
     * 关联业务ID
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