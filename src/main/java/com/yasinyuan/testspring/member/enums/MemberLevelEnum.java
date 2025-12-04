package com.yasinyuan.testspring.member.enums;

import lombok.Getter;

/**
 * 会员等级枚举
 */
@Getter
public enum MemberLevelEnum {
    
    ORDINARY("普通会员", 1, 0, 999),
    SILVER("白银会员", 2, 1000, 4999),
    GOLD("黄金会员", 3, 5000, 19999),
    PLATINUM("铂金会员", 4, 20000, Integer.MAX_VALUE);
    
    /**
     * 等级名称
     */
    private String name;
    
    /**
     * 等级代码
     */
    private int code;
    
    /**
     * 最低成长值
     */
    private int minGrowthValue;
    
    /**
     * 最高成长值
     */
    private int maxGrowthValue;
    
    MemberLevelEnum(String name, int code, int minGrowthValue, int maxGrowthValue) {
        this.name = name;
        this.code = code;
        this.minGrowthValue = minGrowthValue;
        this.maxGrowthValue = maxGrowthValue;
    }
    
    /**
     * 根据成长值获取会员等级
     */
    public static MemberLevelEnum getByGrowthValue(int growthValue) {
        for (MemberLevelEnum level : values()) {
            if (growthValue >= level.getMinGrowthValue() && growthValue <= level.getMaxGrowthValue()) {
                return level;
            }
        }
        return ORDINARY;
    }
    
    /**
     * 根据等级代码获取会员等级
     */
    public static MemberLevelEnum getByCode(int code) {
        for (MemberLevelEnum level : values()) {
            if (level.getCode() == code) {
                return level;
            }
        }
        return ORDINARY;
    }
}