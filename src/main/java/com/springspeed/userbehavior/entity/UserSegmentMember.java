package com.springspeed.userbehavior.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户分群成员实体类
 * 用于存储用户分群的成员信息
 */
@Data
@Entity
@Table(name = "user_segment_member", uniqueConstraints = {@UniqueConstraint(columnNames = {"segment_id", "user_id"})})
public class UserSegmentMember implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分群ID
     */
    @Column(name = "segment_id", nullable = false, length = 64)
    private String segmentId;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;

    /**
     * 加入时间
     */
    @Column(name = "join_time", nullable = false)
    private Date joinTime;

    /**
     * 退出时间
     */
    @Column(name = "exit_time")
    private Date exitTime;

    /**
     * 状态
     * 0: 已退出
     * 1: 已加入
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
     * 构造函数
     */
    public UserSegmentMember() {
        this.createTime = new Date();
        this.updateTime = new Date();
        this.status = 1; // 默认已加入状态
    }

    /**
     * 构造函数
     * @param segmentId 分群ID
     * @param userId 用户ID
     */
    public UserSegmentMember(String segmentId, String userId) {
        this.segmentId = segmentId;
        this.userId = userId;
        this.joinTime = new Date();
        this.createTime = new Date();
        this.updateTime = new Date();
        this.status = 1; // 默认已加入状态
    }
}