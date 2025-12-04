package com.yasinyuan.testspring.point.enums;

import lombok.Getter;

/**
 * 积分变动类型枚举
 */
@Getter
public enum PointChangeTypeEnum {
    
    CONSUME_EARN("消费获得", 1, true),
    SIGN_IN_EARN("签到获得", 2, true),
    ACTIVITY_EARN("活动奖励", 3, true),
    BIRTHDAY_EARN("生日奖励", 4, true),
    DEDUCT("抵扣使用", 5, false),
    EXCHANGE("兑换商品", 6, false),
    LOTTERY("抽奖消耗", 7, false),
    EXPIRE("过期扣除", 8, false),
    FROZEN("冻结", 9, false),
    UNFREEZE("解冻", 10, true);
    
    /**
     * 类型名称
     */
    private String name;
    
    /**
     * 类型代码
     */
    private int code;
    
    /**
     * 是否为增加类型
     */
    private boolean isIncrease;
    
    PointChangeTypeEnum(String name, int code, boolean isIncrease) {
        this.name = name;
        this.code = code;
        this.isIncrease = isIncrease;
    }
    
    /**
     * 根据代码获取枚举
     */
    public static PointChangeTypeEnum getByCode(int code) {
        for (PointChangeTypeEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}