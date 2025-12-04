package com.yasinyuan.testspring.enumtype;

/**
 * 业务类型枚举
 * @author yinyuan
 * @since 2024-01-01
 */
public enum BusinessTypeEnum {
    /**
     * 注册
     */
    REGISTER(1, "注册"),
    /**
     * 登录
     */
    LOGIN(2, "登录"),
    /**
     * 购买商品
     */
    PURCHASE(3, "购买商品"),
    /**
     * 评价商品
     */
    COMMENT(4, "评价商品"),
    /**
     * 分享商品
     */
    SHARE(5, "分享商品"),
    /**
     * 生日
     */
    BIRTHDAY(6, "生日"),
    /**
     * 积分兑换
     */
    EXCHANGE(7, "积分兑换"),
    /**
     * 积分过期
     */
    EXPIRE(8, "积分过期"),
    /**
     * 其他
     */
    OTHER(99, "其他");

    private final Integer value;
    private final String name;

    BusinessTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据值获取枚举
     */
    public static BusinessTypeEnum getByValue(Integer value) {
        for (BusinessTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}