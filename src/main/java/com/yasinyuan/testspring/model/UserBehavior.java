package com.yasinyuan.testspring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.Map;

/**
 * 用户行为日志实体类
 * @author yasinyuan
 * @date 2025-05-02
 */
@Document(indexName = "user_behavior", type = "behavior", shards = 1, replicas = 0)
public class UserBehavior {

    @Id
    private String id;

    /**
     * 用户ID
     */
    @Field(type = FieldType.Keyword)
    private Long userId;

    /**
     * 会话ID
     */
    @Field(type = FieldType.Keyword)
    private String sessionId;

    /**
     * 行为类型：浏览/点击/加购/下单
     */
    @Field(type = FieldType.Keyword)
    private String behaviorType;

    /**
     * 页面URL
     */
    @Field(type = FieldType.Text)
    private String pageUrl;

    /**
     * 页面标题
     */
    @Field(type = FieldType.Text)
    private String pageTitle;

    /**
     * 商品ID
     */
    @Field(type = FieldType.Keyword)
    private String productId;

    /**
     * 商品名称
     */
    @Field(type = FieldType.Text)
    private String productName;

    /**
     * 行为发生时间
     */
    @Field(type = FieldType.Date)
    private Date createTime;

    /**
     * IP地址
     */
    @Field(type = FieldType.Keyword)
    private String ip;

    /**
     * 设备信息
     */
    @Field(type = FieldType.Keyword)
    private String device;

    /**
     * 扩展字段
     */
    @Field(type = FieldType.Object)
    private Map<String, Object> extInfo;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(String behaviorType) {
        this.behaviorType = behaviorType;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Map<String, Object> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, Object> extInfo) {
        this.extInfo = extInfo;
    }
}
