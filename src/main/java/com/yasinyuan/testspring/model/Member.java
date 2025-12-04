package com.yasinyuan.testspring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * 会员信息表
 * @author yinyuan
 * @since 2024-01-01
 */
@Data
@Table(name = "member")
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID（关联sys_user表）
     */
    private Long userId;

    /**
     * 当前等级ID（关联member_level表）
     */
    private Long currentLevelId;

    /**
     * 当前成长值
     */
    private Integer currentGrowthValue;

    /**
     * 总成长值
     */
    private Integer totalGrowthValue;

    /**
     * 会员有效期开始时间
     */
    private Date validityStartDate;

    /**
     * 会员有效期结束时间
     */
    private Date validityEndDate;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 专属客服ID（关联sys_user表）
     */
    private Long exclusiveServiceId;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 状态（0：禁用，1：启用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}