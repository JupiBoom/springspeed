package com.yasinyuan.testspring.enumtype;

/**
 * 权益类型枚举
 * @author yinyuan
 * @since 2024-01-01
 */
public enum BenefitTypeEnum {
    /**
     * 运费券
     */
    FREIGHT_COUPON(1, "运费券"),
    /**
     * 生日积分
     */
    BIRTHDAY_POINTS(2, "生日积分"),
    /**
     * 专属客服
     */
    EXCLUSIVE_SERVICE(3, "专属客服"),
    /**
     * 会员折扣
     */
    MEMBER_DISCOUNT(4, "会员折扣"),
    /**
     * 免费配送
     */
    FREE_SHIPPING(5, "免费配送"),
    /**
     * 专属礼品
     */
    EXCLUSIVE_GIFT(6, "专属礼品"),
    /**
     * 其他
     */
    OTHER(99, "其他");

    private final Integer value;
    private final String name;

    BenefitTypeEnum(Integer value, String name) {
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
    public static BenefitTypeEnum getByValue(Integer value) {
        for (BenefitTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}