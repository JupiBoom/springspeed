package com.yasinyuan.testspring.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * 通用工具类
 * @author yinyuan
 * @since 2024-01-01
 */
public class CommonUtils {
    /**
     * 生成UUID
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成随机数
     */
    public static int generateRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 格式化日期时间
     */
    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * 格式化日期
     */
    public static String formatDate(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    /**
     * 检查字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 检查字符串是否不为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 检查对象是否为空
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 检查对象是否不为空
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 计算两个日期之间的天数差
     */
    public static long calculateDaysBetween(LocalDate startDate, LocalDate endDate) {
        return endDate.toEpochDay() - startDate.toEpochDay();
    }

    /**
     * 生成验证码
     */
    public static String generateVerificationCode(int length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }
}