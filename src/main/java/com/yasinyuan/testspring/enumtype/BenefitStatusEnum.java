package com.yasinyuan.testspring.enumtype;

/**
 * 权益状态枚举
 * @author yinyuan
 * @since 2024-01-01
 */
public enum BenefitStatusEnum {
    /**
     * 未使用
     */
    UNUSED(1, "未使用"),
    /**
     * 已使用
     */
    USED(2, "已使用"),
    /**
     * 已过期
     */
    EXPIRED(3, "已过期");

    private final Integer value;
    private final String name;

    BenefitStatusEnum(Integer value, String name) {
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
    public static BenefitStatusEnum getByValue(Integer value) {
        for (BenefitStatusEnum status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}