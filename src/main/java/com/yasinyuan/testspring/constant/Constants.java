package com.yasinyuan.testspring.constant;

/**
 * 系统常量类
 * @author yinyuan
 * @since 2024-01-01
 */
public class Constants {
    /**
     * 成功状态码
     */
    public static final int SUCCESS = 0;
    /**
     * 失败状态码
     */
    public static final int FAIL = 500;

    /**
     * 默认页码
     */
    public static final int DEFAULT_PAGE = 1;
    /**
     * 默认每页条数
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 积分过期时间（天）
     */
    public static final int POINTS_EXPIRATION_DAYS = 365;

    /**
     * 生日权益发放提前天数
     */
    public static final int BIRTHDAY_BENEFIT_ADVANCE_DAYS = 7;

    /**
     * 会员等级缓存前缀
     */
    public static final String MEMBER_LEVEL_CACHE_PREFIX = "member_level:";
    /**
     * 积分获取规则缓存前缀
     */
    public static final String POINTS_GAIN_RULE_CACHE_PREFIX = "points_gain_rule:";
    /**
     * 会员权益缓存前缀
     */
    public static final String MEMBER_BENEFIT_CACHE_PREFIX = "member_benefit:";

    /**
     * 防刷控制缓存前缀
     */
    public static final String RATE_LIMIT_CACHE_PREFIX = "rate_limit:";
    /**
     * 防刷控制时间窗口（秒）
     */
    public static final int RATE_LIMIT_WINDOW = 60;
    /**
     * 防刷控制最大请求数
     */
    public static final int RATE_LIMIT_MAX_REQUESTS = 10;

    /**
     * 日志类型：操作日志
     */
    public static final String LOG_TYPE_OPERATION = "operation";
    /**
     * 日志类型：异常日志
     */
    public static final String LOG_TYPE_EXCEPTION = "exception";

    /**
     * 操作类型：新增
     */
    public static final String OPERATION_TYPE_ADD = "add";
    /**
     * 操作类型：修改
     */
    public static final String OPERATION_TYPE_UPDATE = "update";
    /**
     * 操作类型：删除
     */
    public static final String OPERATION_TYPE_DELETE = "delete";
    /**
     * 操作类型：查询
     */
    public static final String OPERATION_TYPE_QUERY = "query";
}