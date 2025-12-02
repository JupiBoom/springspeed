package com.springspeed.userbehavior.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 用户行为日志实体类
 * 用于存储用户的浏览、点击、加购、下单等行为
 */
@Data
@Document(indexName = "user_behavior")
public class UserBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    private String id;

    /**
     * 用户ID
     */
    @Field(type = FieldType.Keyword)
    private String userId;

    /**
     * 会话ID
     */
    @Field(type = FieldType.Keyword)
    private String sessionId;

    /**
     * 行为类型
     * 浏览: view
     * 点击: click
     * 加购: add_cart
     * 下单: place_order
     * 支付: pay
     */
    @Field(type = FieldType.Keyword)
    private String behaviorType;

    /**
     * 行为发生的页面
     */
    @Field(type = FieldType.Keyword)
    private String page;

    /**
     * 行为发生的页面URL
     */
    @Field(type = FieldType.Text)
    private String pageUrl;

    /**
     * 商品ID（如果行为与商品相关）
     */
    @Field(type = FieldType.Keyword)
    private String productId;

    /**
     * 商品名称（如果行为与商品相关）
     */
    @Field(type = FieldType.Text)
    private String productName;

    /**
     * 商品分类ID（如果行为与商品相关）
     */
    @Field(type = FieldType.Keyword)
    private String categoryId;

    /**
     * 商品分类名称（如果行为与商品相关）
     */
    @Field(type = FieldType.Text)
    private String categoryName;

    /**
     * 行为发生的时间
     */
    @Field(type = FieldType.Date, format = {}) 
    private Date behaviorTime;

    /**
     * 行为持续时间（秒）
     */
    @Field(type = FieldType.Integer)
    private Integer duration;

    /**
     * 来源渠道
     * 例如：直接访问、搜索引擎、社交媒体、广告等
     */
    @Field(type = FieldType.Keyword)
    private String sourceChannel;

    /**
     * 设备类型
     * 例如：PC、移动端、平板等
     */
    @Field(type = FieldType.Keyword)
    private String deviceType;

    /**
     * 浏览器类型
     */
    @Field(type = FieldType.Keyword)
    private String browserType;

    /**
     * 操作系统类型
     */
    @Field(type = FieldType.Keyword)
    private String osType;

    /**
     * IP地址
     */
    @Field(type = FieldType.Keyword)
    private String ipAddress;

    /**
     * 地理位置（城市）
     */
    @Field(type = FieldType.Keyword)
    private String city;

    /**
     * 地理位置（省份）
     */
    @Field(type = FieldType.Keyword)
    private String province;

    /**
     * 地理位置（国家）
     */
    @Field(type = FieldType.Keyword)
    private String country;

    /**
     * 自定义属性
     * 用于存储一些额外的信息
     */
    @Field(type = FieldType.Object)
    private Map<String, Object> customProperties;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date, format = {}) 
    private Date createTime;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date, format = {}) 
    private Date updateTime;

    /**
     * 构造函数
     */
    public UserBehavior() {
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    /**
     * 构造函数
     * @param userId 用户ID
     * @param sessionId 会话ID
     * @param behaviorType 行为类型
     * @param page 行为发生的页面
     * @param behaviorTime 行为发生的时间
     */
    public UserBehavior(String userId, String sessionId, String behaviorType, String page, Date behaviorTime) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.behaviorType = behaviorType;
        this.page = page;
        this.behaviorTime = behaviorTime;
        this.createTime = new Date();
        this.updateTime = new Date();
    }
}