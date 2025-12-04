package com.yasinyuan.testspring.member.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会员实体
 */
@Data
@TableName("t_member")
public class Member implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 会员ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 会员编号
     */
    private String memberNo;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 等级代码
     */
    private Integer levelCode;
    
    /**
     * 成长值
     */
    private Integer growthValue;
    
    /**
     * 会员有效期开始时间
     */
    private LocalDateTime validStartTime;
    
    /**
     * 会员有效期结束时间
     */
    private LocalDateTime validEndTime;
    
    /**
     * 生日
     */
    private LocalDate birthday;
    
    /**
     * 专属客服ID
     */
    private Long customerServiceId;
    
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
    
    /**
     * 逻辑删除标识
     */
    @TableLogic
    private Integer deleted;
}