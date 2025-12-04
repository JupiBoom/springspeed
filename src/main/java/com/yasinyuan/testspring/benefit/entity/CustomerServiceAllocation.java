package com.yasinyuan.testspring.benefit.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 客服分配
 */
@Data
@TableName("t_customer_service_allocation")
public class CustomerServiceAllocation implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 分配ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 客服ID
     */
    private Long serviceId;
    
    /**
     * 客服姓名
     */
    private String serviceName;
    
    /**
     * 客服等级
     */
    private String serviceLevel;
    
    /**
     * 负责等级代码（多个用逗号分隔）
     */
    private String responsibleLevels;
    
    /**
     * 已分配会员数
     */
    private Integer allocatedMemberCount;
    
    /**
     * 最大承载会员数
     */
    private Integer maxMemberCount;
    
    /**
     * 在线状态：0-离线，1-在线
     */
    private Integer onlineStatus;
    
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