package com.springspeed.userbehavior.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户会话实体类
 * 用于存储用户的会话信息
 */
@Data
@Entity
@Table(name = "user_session")
public class UserSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 会话ID
     */
    @Column(name = "session_id", nullable = false, length = 64)
    private String sessionId;

    /**
     * 用户ID
     */
    @Column(name = "user_id", length = 64)
    private String userId;

    /**
     * 会话开始时间
     */
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    /**
     * 会话结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 会话持续时间（秒）
     */
    @Column(name = "duration")
    private Integer duration;

    /**
     * 最后访问时间
     */
    @Column(name = "last_access_time")
    private Date lastAccessTime;

    /**
     * 来源渠道
     * 例如：直接访问、搜索引擎、社交媒体、广告等
     */
    @Column(name = "source_channel", length = 32)
    private String sourceChannel;

    /**
     * 设备类型
     * 例如：PC、移动端、平板等
     */
    @Column(name = "device_type", length = 32)
    private String deviceType;

    /**
     * 浏览器类型
     */
    @Column(name = "browser_type", length = 32)
    private String browserType;

    /**
     * 操作系统类型
     */
    @Column(name = "os_type", length = 32)
    private String osType;

    /**
     * IP地址
     */
    @Column(name = "ip_address", length = 32)
    private String ipAddress;

    /**
     * 地理位置（城市）
     */
    @Column(name = "city", length = 32)
    private String city;

    /**
     * 地理位置（省份）
     */
    @Column(name = "province", length = 32)
    private String province;

    /**
     * 地理位置（国家）
     */
    @Column(name = "country", length = 32)
    private String country;

    /**
     * 会话状态
     * 0: 活跃
     * 1: 已结束
     * 2: 超时
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
    public UserSession() {
        this.createTime = new Date();
        this.updateTime = new Date();
        this.status = 0; // 默认活跃状态
    }

    /**
     * 构造函数
     * @param sessionId 会话ID
     * @param userId 用户ID
     * @param startTime 会话开始时间
     */
    public UserSession(String sessionId, String userId, Date startTime) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.startTime = startTime;
        this.createTime = new Date();
        this.updateTime = new Date();
        this.status = 0; // 默认活跃状态
    }
}