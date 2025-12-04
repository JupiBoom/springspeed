package com.yasinyuan.testspring.enumtype;

/**
 * 积分记录类型枚举
 * @author yinyuan
 * @since 2024-01-01
 */
public enum PointsRecordTypeEnum {
    /**
     * 增加
     */
    ADD(1, "增加"),
    /**
     * 扣除
     */
    DEDUCT(2, "扣除"),
    /**
     * 冻结
     */
    FREEZE(3, "冻结"),
    /**
     * 解冻
     */
    UNFREEZE(4, "解冻"),
    /**
     * 过期
     */
    EXPIRE(5, "过期");

    private final Integer value;
    private final String name;

    PointsRecordTypeEnum(Integer value, String name) {
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
    public static PointsRecordTypeEnum getByValue(Integer value) {
        for (PointsRecordTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}