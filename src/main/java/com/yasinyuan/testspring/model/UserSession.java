package com.yasinyuan.testspring.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户会话实体类
 * @author yasinyuan
 * @date 2025-05-02
 */
public class UserSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 会话开始时间
     */
    private Date startTime;

    /**
     * 最后活跃时间
     */
    private Date lastActiveTime;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 设备信息
     */
    private String device;

    /**
     * 用户路径
     */
    private List<String> pagePath;

    /**
     * 会话状态：active/expired
     */
    private String status;

    // Getters and Setters
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public List<String> getPagePath() {
        return pagePath;
    }

    public void setPagePath(List<String> pagePath) {
        this.pagePath = pagePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
