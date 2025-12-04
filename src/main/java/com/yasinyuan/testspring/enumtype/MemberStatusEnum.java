package com.yasinyuan.testspring.enumtype;

/**
 * 会员状态枚举
 * @author yinyuan
 * @since 2024-01-01
 */
public enum MemberStatusEnum {
    /**
     * 正常
     */
    NORMAL(1, "正常"),
    /**
     * 冻结
     */
    FROZEN(2, "冻结"),
    /**
     * 注销
     */
    CANCELED(3, "注销");

    private final Integer value;
    private final String name;

    MemberStatusEnum(Integer value, String name) {
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
    public static MemberStatusEnum getByValue(Integer value) {
        for (MemberStatusEnum status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}