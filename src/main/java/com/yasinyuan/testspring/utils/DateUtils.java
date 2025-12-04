package com.yasinyuan.testspring.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 日期工具类
 * @author yinyuan
 * @since 2024-01-01
 */
public class DateUtils {
    /**
     * 日期时间格式
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 时间格式
     */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 格式化日期时间
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return formatDateTime(dateTime, DATETIME_FORMAT);
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
    public static String formatDate(LocalDate date) {
        return formatDate(date, DATE_FORMAT);
    }

    /**
     * 格式化日期
     */
    public static String formatDate(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    /**
     * 格式化时间
     */
    public static String formatTime(LocalTime time) {
        return formatTime(time, TIME_FORMAT);
    }

    /**
     * 格式化时间
     */
    public static String formatTime(LocalTime time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return time.format(formatter);
    }

    /**
     * 解析日期时间
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return parseDateTime(dateTimeStr, DATETIME_FORMAT);
    }

    /**
     * 解析日期时间
     */
    public static LocalDateTime parseDateTime(String dateTimeStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    /**
     * 解析日期
     */
    public static LocalDate parseDate(String dateStr) {
        return parseDate(dateStr, DATE_FORMAT);
    }

    /**
     * 解析日期
     */
    public static LocalDate parseDate(String dateStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * 解析时间
     */
    public static LocalTime parseTime(String timeStr) {
        return parseTime(timeStr, TIME_FORMAT);
    }

    /**
     * 解析时间
     */
    public static LocalTime parseTime(String timeStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalTime.parse(timeStr, formatter);
    }

    /**
     * 获取当前日期时间
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前日期
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前时间
     */
    public static LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    /**
     * 计算两个日期之间的天数差
     */
    public static long calculateDaysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * 计算两个日期时间之间的小时差
     */
    public static long calculateHoursBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ChronoUnit.HOURS.between(startDateTime, endDateTime);
    }

    /**
     * 计算两个日期时间之间的分钟差
     */
    public static long calculateMinutesBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ChronoUnit.MINUTES.between(startDateTime, endDateTime);
    }

    /**
     * 计算两个日期时间之间的秒数差
     */
    public static long calculateSecondsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ChronoUnit.SECONDS.between(startDateTime, endDateTime);
    }

    /**
     * 在日期时间上增加天数
     */
    public static LocalDateTime addDays(LocalDateTime dateTime, int days) {
        return dateTime.plusDays(days);
    }

    /**
     * 在日期时间上增加小时数
     */
    public static LocalDateTime addHours(LocalDateTime dateTime, int hours) {
        return dateTime.plusHours(hours);
    }

    /**
     * 在日期时间上增加分钟数
     */
    public static LocalDateTime addMinutes(LocalDateTime dateTime, int minutes) {
        return dateTime.plusMinutes(minutes);
    }

    /**
     * 在日期时间上增加秒数
     */
    public static LocalDateTime addSeconds(LocalDateTime dateTime, int seconds) {
        return dateTime.plusSeconds(seconds);
    }

    /**
     * 在日期上增加天数
     */
    public static LocalDate addDays(LocalDate date, int days) {
        return date.plusDays(days);
    }

    /**
     * 在时间上增加小时数
     */
    public static LocalTime addHours(LocalTime time, int hours) {
        return time.plusHours(hours);
    }

    /**
     * 在时间上增加分钟数
     */
    public static LocalTime addMinutes(LocalTime time, int minutes) {
        return time.plusMinutes(minutes);
    }

    /**
     * 在时间上增加秒数
     */
    public static LocalTime addSeconds(LocalTime time, int seconds) {
        return time.plusSeconds(seconds);
    }
}