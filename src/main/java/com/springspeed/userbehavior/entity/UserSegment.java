package com.springspeed.userbehavior.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户分群实体类
 * 用于存储用户分群的信息
 */
@Data
@Entity
@Table(name = "user_segment")
public class UserSegment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分群ID（唯一标识）
     */
    @Column(name = "segment_id", nullable = false, length = 64, unique = true)
    private String segmentId;

    /**
     * 分群名称
     */
    @Column(name = "segment_name", nullable = false, length = 128)
    private String segmentName;

    /**
     * 分群类型
     * RFM: RFM模型分群
     * LIFECYCLE: 用户生命周期划分
     * CUSTOM: 自定义标签分群
     */
    @Column(name = "segment_type", nullable = false, length = 32)
    private String segmentType;

    /**
     * 分群条件（JSON格式存储）
     */
    @Column(name = "segment_condition", columnDefinition = "TEXT")
    private String segmentCondition;

    /**
     * 分群用户数量
     */
    @Column(name = "user_count")
    private Integer userCount;

    /**
     * 分群状态
     * 0: 禁用
     * 1: 启用
     */
    @Column(name = "status", nullable = false)
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    /**
     * 创建人
     */
    @Column(name = "create_by", length = 64)
    private String createBy;

    /**
     * 更新人
     */
    @Column(name = "update_by", length = 64)
    private String updateBy;

    /**
     * 构造函数
     */
    public UserSegment() {
        this.createTime = new Date();
        this.updateTime = new Date();
        this.status = 1; // 默认启用状态
    }

    /**
     * 构造函数
     * @param segmentId 分群ID
     * @param segmentName 分群名称
     * @param segmentType 分群类型
     */
    public UserSegment(String segmentId, String segmentName, String segmentType) {
        this.segmentId = segmentId;
        this.segmentName = segmentName;
        this.segmentType = segmentType;
        this.createTime = new Date();
        this.updateTime = new Date();
        this.status = 1; // 默认启用状态
    }
}